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

    /**
     * returns page to edit meetings.
     *
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

    @PostMapping("/editMeeting/{id}/confirm")
    public ModelAndView bookEditedMeeting(@PathVariable("id") final String id,
    final @ModelAttribute("meeting")
    @Validated({MeetingCreationForm.ValidateMeetingDetails.class, MeetingCreationForm.ValidateRoomSelection.class})
    MeetingCreationForm meeting) {
        editMeeting(meeting, meetingService.getMeetingById(id));
        return new ModelAndView("redirect:/meeting/" + id);

    }

    private void editMeeting(MeetingCreationForm meeting, Meeting curMeeting) {
        meetingService.editMeeting(meeting, curMeeting);
    }
}
