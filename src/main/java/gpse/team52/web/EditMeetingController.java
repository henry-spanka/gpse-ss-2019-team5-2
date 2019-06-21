package gpse.team52.web;

import gpse.team52.contract.*;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.Room;
import gpse.team52.domain.User;
import gpse.team52.exception.NoRoomAvailableException;
import gpse.team52.form.MeetingCreationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Edit meeting controller.
 */
@Controller
@SessionAttributes("meeting")
public class EditMeetingController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomFinderService roomFinderService;



    @GetMapping("/editMeeting")
    public ModelAndView showCreationForm(final @ModelAttribute("meeting") MeetingCreationForm meeting,
                                         final @RequestParam(required = false, name = "offset") Integer offset) {

        if (offset != null && meeting.getStartDate() != null) {
            meeting.addOffsetMinutes(offset);

            return new ModelAndView("redirect:/editMeeting/select");
        }

        return generateMeetingCreationView(meeting);
    }


    private ModelAndView generateMeetingCreationView(final MeetingCreationForm meeting) {
        final ModelAndView modelAndView = new ModelAndView("editMeeting");

        modelAndView.addObject("meeting", meeting);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("locations", locationService.getAllLocations());
        modelAndView.addObject("equipments", roomService.getAllEquipment());

        return modelAndView;
    }

    /**
     * returns page to edit meetings.
     * @param id meetingId
     * @return
     */
    @RequestMapping("/meeting/{id}/edit")
    public ModelAndView editMeeting(@PathVariable("id") final String id) {
        final Meeting meeting = meetingService.getMeetingById(id);
        System.out.println(meeting);
        ModelAndView modelAndView = new ModelAndView("editMeeting");
        modelAndView.addObject("meeting", meeting);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("locations", locationService.getAllLocations());
        modelAndView.addObject("equipments", roomService.getAllEquipment());

        return modelAndView;
    }


    @PostMapping("/editMeeting/confirm")
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
     * @param meeting The meeting form which contains the basic meeting information.
     * @param bindingResult Result of the validation.
     * @return Shows room selection view.
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, path = "/editMeeting/select")
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


    private ModelAndView generateRoomSelectionView(final MeetingCreationForm meeting) {
        final ModelAndView modelAndView = new ModelAndView("selectMeetingRooms");
        modelAndView.addObject("meeting", meeting);
        modelAndView.addObject("rooms", roomFinderService.find(meeting));

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
}

