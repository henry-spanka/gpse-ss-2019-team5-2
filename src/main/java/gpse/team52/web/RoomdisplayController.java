package gpse.team52.web;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import gpse.team52.contract.RoomService;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Location;
import gpse.team52.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * Room display page controller.
 */
@Controller
public class RoomdisplayController {

    @Autowired
    private RoomService roomService;

    public RoomdisplayController() {
        Location location;
        Date date;
        List<Equipment> equipment;
        int start;
        int end;
        int seats;
    }


    /**
     * Details for each room.
     *
     * @param roomID room to display details
     * @return Details about specified room
     */
    @GetMapping("/rooms/{roomID}")
    public ModelAndView roomdetails(final @PathVariable("roomID") UUID roomID) {
        final ModelAndView modelAndView = new ModelAndView("roomdetails");
        final Room room = roomService.getRoom(roomID).orElseThrow(()
        -> new IllegalArgumentException("No meeting with id: " + roomID + " found"));
        // should add some error handling, if the get method fails

        modelAndView.addObject("room", room);
        final List<Equipment> equipment = room.getEquipment();
        modelAndView.addObject("equipmentList", equipment);
        return modelAndView;
    }
}
