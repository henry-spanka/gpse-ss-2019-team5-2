package gpse.team52.web;

import gpse.team52.Room;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomdisplayController {


    private List<Room> roomList = new ArrayList<Room>();

    public RoomdisplayController() {
        // should get a list of rooms to choose for meeting, just set any default
        //Example rooms
        roomList.add(new Room(100, "mail100", "name", 5, 6, "description"));
        roomList.add(new Room(101, "mail101", "name2", 2, 2, "description2"));
    }

    @GetMapping("/rooms")
    public ModelAndView rooms(final @RequestParam(name = "noroom", required = false) String noroom) {
        final ModelAndView modelAndView = new ModelAndView("rooms");
        modelAndView.addObject("roomList", roomList);
        modelAndView.addObject("noroom", noroom != null);
        return modelAndView;
    }


    @GetMapping("/rooms/{roomID}")
    public ModelAndView roomdetails(@PathVariable("roomID") String roomID) {
        final ModelAndView modelAndView = new ModelAndView("roomdetails");
        Room room = getRoom(roomID);
        modelAndView.addObject("room", room);
        return modelAndView;
    }

    //TODO use database instead
    private Room getRoom(String roomID) {
        for (Room room : roomList) {
            if (room.getRoomID() == Integer.parseInt(roomID)) {
                return room;
            }
        }
        return null;
    }

    @GetMapping("/rooms/confirm")
    public ModelAndView confirm() { //add  this: @RequestParam(name = "room", required = true) String room
        final ModelAndView modelAndView = new ModelAndView("confirmbooking");
        // addObject(room) benötigt, und Meeting auch hinzufügen!
        return modelAndView;
    }

}
