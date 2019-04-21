package gpse.team52.web;

import gpse.team52.Room;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RoomdetailsController {

    Room room = new Room("alaska", 3, "descriptionAndEquipment");

    @GetMapping("/roomdetails")
    public ModelAndView roomdetails() {
        final ModelAndView modelAndView = new ModelAndView("roomdetails");
        modelAndView.addObject("room", room);
        return modelAndView;
    }
}
