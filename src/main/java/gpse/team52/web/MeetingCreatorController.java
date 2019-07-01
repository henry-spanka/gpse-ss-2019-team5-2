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
import gpse.team52.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

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

    private List<AlternativeMeetingRoom> alternativeMeetingRooms;

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
                final Meeting createdMeeting = createMeeting(meeting, user);
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
        final LocalDateTime start = meeting.getStartDateTime();
        final LocalDateTime end = meeting.getEndDateTime();
        Map<String, List<Room>> roomsForNew = roomFinderService.find(meeting);
        alternativeMeetingRooms = new ArrayList<>();

        final ArrayList<Meeting> checkMeetings = new ArrayList<>();
        meetingService.getMeetinginTimeFrameAndFlexibleIsTrue(start, end, true)
        .forEach(checkMeetings::add);
        for (final Meeting m : checkMeetings) {
            // check every meeting which lies in time frame and adjust available rooms
            try {
                roomsForNew = smartrebooking(m, roomsForNew);
            } catch (RebookingNotNecessaryException e) {
                // if meeting doesn't interfere it won't be rebooked
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

        if (alternativeMeetingRooms != null) {
            for (final Room room : rooms) {
                for (final AlternativeMeetingRoom alternativeMeetingRoom : alternativeMeetingRooms) {
                    final Map<String, List<Room>> alter = alternativeMeetingRoom.getAlternatives();
                    if (alter.containsKey(room.getRoomID().toString())) {
                        final Meeting m = alternativeMeetingRoom.getMeeting();
                        final Room roomAlter = alter.get(room.getRoomID().toString()).get(0);
                        final List<Room> changeRoom = new ArrayList<>();
                        changeRoom.add(room);
                        changeRoom.add(roomAlter);
                        // just get first entry in alternative room selection to select new room
                        rebook(m, changeRoom);
                        System.out.println("Would have rebooked " + m.getTitle() + " from " + room.getRoomName()
                        + " to " + roomAlter.getRoomName());
                        break; // bc room won't be there twice
                    }
                }
            }
        }
        return meetingService.createMeeting(meeting, rooms, meeting.getParticipants(), user);
    }

    /**
     * Determines if there are alternative rooms for rebookable meetings.
     * Stores them in AlternativeMeetingRoom class for later use.
     *
     * @param meeting     The meeting that needs to be rebooked.
     * @param roomsForNew rooms which might be used for the new meeting creation
     * @return Remaining rooms which can be used for the new meeting
     */
    private Map<String, List<Room>> smartrebooking(final Meeting meeting, final Map<String, List<Room>> roomsForNew)
    throws RebookingNotNecessaryException {
        // remove rooms if meeting not rebookable
        try {
            final Map<String, List<Room>> alternatives = roomFinderService.findOther(meeting, roomsForNew);
            final AlternativeMeetingRoom alt = new AlternativeMeetingRoom(meeting, alternatives);
            alternativeMeetingRooms.add(alt);
            for (final Map.Entry<String, List<Room>> entry : alternatives.entrySet()) {
                if (entry.getValue().isEmpty()) {
                    // removing a specific room without alternatives
                    final Room room = roomRepository.findById(UUID.fromString(entry.getKey())).orElseThrow();
                    final String locationId = room.getLocation().getLocationId().toString();
                    final List<Room> removeFrom = roomsForNew.get(locationId);
                    removeFrom.removeIf((Room r) -> r.getRoomID().equals(UUID.fromString(entry.getKey())));
                    roomsForNew.put(locationId, removeFrom);
                }
            }
        } catch (RebookingImpossibleException e) {
            // removing all rooms according to a meeting
            final Iterator<MeetingRoom> it = meeting.getRooms().iterator();
            while (it.hasNext()) {
                final Room removeRoom = it.next().getRoom();
                final String locationId = removeRoom.getLocation().getLocationId().toString();
                final List<Room> removeFrom = roomsForNew.get(locationId);
                removeFrom.removeIf((Room room) -> room.getRoomID().equals(removeRoom.getRoomID()));
                roomsForNew.put(locationId, removeFrom);
            }
        }

        return roomsForNew;
    }

    /**
     * Rebooks a meeting. It deletes the old room(s) and adds new alternatives.
     * List should have only even amount of rooms and at least two!
     *
     * @param rooms A list of rooms and the meeting. Every odd room in the List is the old room,
     *              which has to be removed, every even number is the new room.
     */
    private void rebook(Meeting meeting, final List<Room> rooms) {
        boolean found;
        MeetingRoom meetingRoom = null;
        final Set<MeetingRoom> roomset = meeting.getRooms();
        final Iterator<MeetingRoom> iterator = roomset.iterator();
        for (int i = 0; i < rooms.size(); i++) {
            found = false;
            while (iterator.hasNext() && !found) {
                meetingRoom = iterator.next();
                final Room compareroom = meetingRoom.getRoom();
                if (rooms.get(i).getRoomID().equals(compareroom.getRoomID())) {
                    found = true;
                }
            }
            if (found) {
                MeetingRoom newMeetingRoom = new MeetingRoom(rooms.get(i + 1), meetingRoom.getParticipants()); //NOPMD
                //meeting.removeRoom(meetingRoom);
                //meeting.addRoom(newMeetingRoom);
                meetingRoom.setRoom(rooms.get(i + 1));

                //Hier passiert der Fehler
                //SQL Error: insert into meeting_room [...] null is not allowed for column 'ROOM_ID'
                meetingRepository.save(meeting); //Meeting wird aktualisiert

                i++;
            }
        }

        // Refresh meeting
        meeting = meetingService.getMeetingById(meeting.getMeetingId());
        for (final Participant participant : meeting.getParticipants()) {
            System.out.println(participant.isNotifiable());
            if (participant.isNotifiable()) {
                meetingService.notifyParticipantAboutLocationChange(meeting, participant);
            }
        }

    }
}
