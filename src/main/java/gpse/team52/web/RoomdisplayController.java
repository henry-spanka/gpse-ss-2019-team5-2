package gpse.team52.web;

import gpse.team52.contract.EquipmentService;
import gpse.team52.contract.RoomService;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class RoomdisplayController {

    @Autowired
    private RoomService roomService;

    public RoomdisplayController() {
    }

    /**
     * @return Page with available rooms to choose from
     */
    @GetMapping("/rooms")
    public ModelAndView rooms() {
        final ModelAndView modelAndView = new ModelAndView("rooms");
        modelAndView.addObject("roomList", roomService.getAllRooms());
        return modelAndView;
    }

    /**
     *
     * @param roomID room to display details
     * @return Details about specified room
     */
    @GetMapping("/rooms/{roomID}")
    public ModelAndView roomdetails(@PathVariable("roomID") UUID roomID) {
        final ModelAndView modelAndView = new ModelAndView("roomdetails");
        Room room = roomService.getRoom(roomID).orElseThrow(); // should add some error handling, if the get method fails, use database instead
        modelAndView.addObject("room", room);
        List<Equipment> equipment = room.getEquipment();
        modelAndView.addObject("equipmentList", equipment);
        return modelAndView;
    }

    /**
     *
     * @param roomID rooms which are selected
     * @param error if no room was selected
     * @return Page to check and submit data or, in case of error, page with rooms to choose from
     */
    @RequestMapping("/rooms/confirm")
    public ModelAndView confirm(
    @RequestParam(name = "roomID", required = false) String roomID,
    @RequestParam(name = "error", required = false) String error) { // , @RequestParam(name = "meeting", required = true) String meeting
        if (roomID == null) { //return rooms-page with alert message
            final ModelAndView roomsError = new ModelAndView("rooms");
            roomsError.addObject("roomList", roomService.getAllRooms());
            roomsError.addObject("error", true);
            return roomsError;
        }
        // else return confirmation page
        final ModelAndView modelAndView = new ModelAndView("confirmbooking");
        String[] chosen = roomID.split(",");
        List<Room> chosenRooms = new ArrayList<Room>();
        for (int i = 0; i < chosen.length; i++) {
            chosenRooms.add(roomService.getRoom(UUID.fromString(chosen[i])).orElseThrow());
        }
        modelAndView.addObject("chosenRooms", chosenRooms);//benötigt, und Meeting auch hinzufügen!
        return modelAndView;
    }
}
