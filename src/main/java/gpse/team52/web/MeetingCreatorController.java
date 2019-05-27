package gpse.team52.web;

import gpse.team52.contract.EquipmentService;
import gpse.team52.contract.MeetingService;
import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Meeting;
import gpse.team52.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NamedStoredProcedureQuery;
import java.sql.Time;
import java.util.Date;

@Controller
public class MeetingCreatorController {

    @Autowired
    private UserService userService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/createMeeting")
    public ModelAndView createMeeting() {
        final ModelAndView modelAndView = new ModelAndView("createMeeting");

        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("locations", roomService.getAllLocations());
        modelAndView.addObject("equipments", equipmentService.getAllEquipments());

        return modelAndView;
    }

    @RequestMapping("/addParticipants")
    public ModelAndView addParticipants(
    @RequestParam(name = "location", required = false) String location,
    @RequestParam(name = "date", required = false) String date,
    @RequestParam(name = "time-start", required = false) String time_start,
    @RequestParam(name = "time-end", required = false) String time_end,
    @RequestParam(name = "equipment", required = false) String equipment) {
        final ModelAndView modelAndView = new ModelAndView("addParticipants");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }
/*
    @GetMapping("/addParticipants")
    public ModelAndView addParticipants() {
        final ModelAndView modelAndView = new ModelAndView("addParticipants");

        modelAndView.addObject("users", userService.getAllUsers());

        return modelAndView;
    }
*/
}
