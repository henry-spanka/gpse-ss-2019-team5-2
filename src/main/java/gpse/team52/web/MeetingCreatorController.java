package gpse.team52.web;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import gpse.team52.contract.*;
import gpse.team52.domain.*;
import gpse.team52.exception.NoRoomAvailableException;
import gpse.team52.exception.RebookingImpossibleException;
import gpse.team52.exception.RebookingNotNecessaryException;
import gpse.team52.form.MeetingCreationForm;
import gpse.team52.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import gpse.team52.repository.RoomRepository;

/**
 * Meeting Creator Controller.
 */
@Controller
@SessionAttributes("meeting") //NOPMD
public class MeetingCreatorController {

    @Autowired
    private UserService userService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomFinderService roomFinderService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    /**
     * Creates a new meeting from the user selected input.
     *
     * @param meeting        The meeting form which contains all the meeting information.
     * @param bindingResult  Result of the validation.
     * @param authentication User Authentication context.
     * @param sessionStatus  Session status.
     * @return Redirects to the meeting on success otherwise shows room selection view.
     */
    @PostMapping("/createMeeting/confirm")
    public ModelAndView bookMeeting(
    final @ModelAttribute("meeting")
    @Validated({MeetingCreationForm.ValidateMeetingDetails.class, MeetingCreationForm.ValidateRoomSelection.class})
    MeetingCreationForm meeting,
    final BindingResult bindingResult, final Authentication authentication, final SessionStatus sessionStatus) {
        if (!bindingResult.hasErrors()) {
            try {
                final User user = (User) authentication.getPrincipal();
                Meeting createdMeeting = createMeeting(meeting, user);
                sessionStatus.setComplete();

                return new ModelAndView("redirect:/meeting/" + createdMeeting.getMeetingId().toString());
            } catch (NoRoomAvailableException e) {
                bindingResult.rejectValue("rooms", "meeting.create.noRoomsAvailable", e.getMessage());
            }
        }
        return generateRoomSelectionView(meeting);
    }

    /**
     * Shows all available rooms to the user.
     *
     * @param meeting       The meeting form which contains the basic meeting information.
     * @param bindingResult Result of the validation.
     * @return Shows room selection view.
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "/createMeeting/select")
    public ModelAndView showRoomSelectionForm(
    final @ModelAttribute("meeting")
    @Validated(MeetingCreationForm.ValidateMeetingDetails.class) MeetingCreationForm meeting,
    final BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            meeting.setLocationDetails(roomService.findByLocationIdFromString(meeting.getLocations()));

            return generateRoomSelectionView(meeting);
        }

        return generateMeetingCreationView(meeting);
    }

    /**
     * GetMapping: Creates a ModeAndView for a Creation Form for Meetings.
     *
     * @param meeting has the given information for the meeting given by the user.
     * @param offset  .
     * @return a ModelAndView for the CreationForm for the user.
     */
    @GetMapping("/createMeeting")
    public ModelAndView showCreationForm(final @ModelAttribute("meeting") MeetingCreationForm meeting,
                                         final @RequestParam(required = false, name = "offset") Integer offset) {

        if (offset != null && meeting.getStartDate() != null) {
            meeting.addOffsetMinutes(offset);

            return new ModelAndView("redirect:/createMeeting/select");
        }

        return generateMeetingCreationView(meeting);
    }

    @ModelAttribute("meeting")
    public MeetingCreationForm createNewMeetingForm() {
        return new MeetingCreationForm();
    }

    private ModelAndView generateMeetingCreationView(final MeetingCreationForm meeting) {
        final ModelAndView modelAndView = new ModelAndView("createMeeting");

        modelAndView.addObject("meeting", meeting);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("locations", locationService.getAllLocations());
        modelAndView.addObject("equipments", roomService.getAllEquipment());

        return modelAndView;
    }

    /**
     * Room Selection View.
     * Free Rooms and rebookable rooms get shown #smartrebooking.
     *
     * @param meeting the wanted meeting from the user.
     * @return A ModelAndView for the RoomSelection View.
     */
    private ModelAndView generateRoomSelectionView(final MeetingCreationForm meeting) {
        final ModelAndView modelAndView = new ModelAndView("selectMeetingRooms");
        LocalDateTime start = meeting.getStartDateTime();
        LocalDateTime end = meeting.getEndDateTime();
        Map<String, List<Room>> roomsForNew = roomFinderService.find(meeting);

        ArrayList<Meeting> checkMeetings = new ArrayList<>();
        meetingService.getMeetinginTimeFrameAndFlexibleIsTrue(start, end, true)
        .forEach(checkMeetings::add);

        for (Meeting m : checkMeetings) {
            // remove rooms if meeting not rebookable
            try {
                Map<String, List<Room>> alternatives = roomFinderService.findOther(m, roomsForNew);
                for (Map.Entry<String, List<Room>> entry : alternatives.entrySet()) {
                    if (entry.getValue().isEmpty()) {
                        // removing a specific room without alternatives
                        Room room = roomRepository.findById(UUID.fromString(entry.getKey())).orElseThrow();
                        String locationId = room.getLocation().getLocationId().toString();
                        List<Room> removeFrom = roomsForNew.get(locationId);
                        removeFrom.removeIf((Room r) -> r.getRoomID().equals(UUID.fromString(entry.getKey())));
                        roomsForNew.put(locationId, removeFrom);
                    }
                }
            } catch (RebookingImpossibleException e) {
                // removing all rooms according to a meeting
                Iterator<MeetingRoom> it = m.getRooms().iterator();
                while (it.hasNext()) {
                    Room removeRoom = it.next().getRoom();
                    String locationId = removeRoom.getLocation().getLocationId().toString();
                    List<Room> removeFrom = roomsForNew.get(locationId);
                    removeFrom.removeIf((Room room) -> room.getRoomID().equals(removeRoom.getRoomID()));
                    roomsForNew.put(locationId, removeFrom);
                }
            } catch (RebookingNotNecessaryException e) {
                // don't remove anything
            }
        }

        modelAndView.addObject("meeting", meeting);
        modelAndView.addObject("rooms", roomsForNew);

        return modelAndView;
    }

    private Meeting createMeeting(final MeetingCreationForm meeting, final User user) throws NoRoomAvailableException {
        List<Room> rooms;

        if (meeting.getRooms() == null || meeting.noRoomsSelected()) {
            rooms = roomFinderService.findBest(meeting);
        } else {
            rooms = meeting.getRooms().stream()
            .map(r -> roomService.getRoom(UUID.fromString(r)).orElseThrow()).collect(Collectors.toList());
        }

        return meetingService.createMeeting(meeting, rooms, meeting.getParticipants(), user);
    }

    /**
     * Determines alternative rooms for rebookable meetings.
     *
     * @param meeting     the meeting, that needs to be rebooked.
     * @param roomsForNew possible alternative rooms for given meeting.
     * @return true, if a alternative room is bookable for the meeting, otherwise false
     */
    private boolean smartrebooking(Meeting meeting, Map<String, List<Room>> roomsForNew) {
        List<Room> rooms = new ArrayList<>();

        if (rooms.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Rebooks a meeting. It deletes the old room(s) and adds new alternatives.
     * List should have only even amount of rooms and at least two!
     *
     * @param rooms A list of rooms and the meeting. Every odd room in the List is the old room,
     *              which has to be removed, every even number is the new room.
     */
    private void rebook(Meeting meeting, List<Room> rooms) {
        int counter = 1;
        int participants = 0;
        Set<MeetingRoom> roomset = meeting.getRooms();
        Iterator<MeetingRoom> iterator = roomset.iterator();
        for (Room r : rooms) {
            if (counter % 2 != 0) { //odd room = old room
                while (iterator.hasNext()) {
                    MeetingRoom meetingRoom = iterator.next();
                    Room compareroom = meetingRoom.getRoom();
                    if (r == compareroom) {
                        participants = meetingRoom.getParticipants(); //Merke Anzahl Teilnehmer
                        meeting.removeRoom(meetingRoom);
                        break;
                    }
                }
            } else { //even room = new room
                final MeetingRoom meetingRoom = new MeetingRoom(meeting, r, participants);
                meeting.addRoom(meetingRoom);
            }
            counter++;
        }
        meetingRepository.save(meeting); //Meeting wird aktualisiert.
    }
}
