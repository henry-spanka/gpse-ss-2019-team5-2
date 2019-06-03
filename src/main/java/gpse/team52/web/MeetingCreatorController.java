package gpse.team52.web;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import gpse.team52.contract.*;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Room;
import gpse.team52.domain.User;
import gpse.team52.exception.NoRoomAvailableException;
import gpse.team52.form.MeetingCreationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
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

    /**
     * Creates a new meeting from the user selected input.
     * @param meeting The meeting form which contains all the meeting information.
     * @param bindingResult Result of the validation.
     * @param authentication User Authentication context.
     * @param sessionStatus Session status.
     * @return Redirects to start on success otherwise shows room selection view.
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
                createMeeting(meeting, user);
                sessionStatus.setComplete();

                return new ModelAndView("redirect:/");
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
    @PostMapping("/createMeeting")
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

    @GetMapping("/createMeeting")
    public ModelAndView showCreationForm(final @ModelAttribute("meeting") MeetingCreationForm meeting) {
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
