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


    // should get a list of rooms to choose for meeting, just set any default
    // Arrays.asList("room1", "room2");
    // @Value()
    //Example rooms
    Room room = new Room("name", 5, "description");
    Room room2 = new Room("name2", 2, "description2");
    private List<Room> roomList = new ArrayList<Room>();
    //private Room[] roomList = {room, room2};

    @GetMapping("/rooms")
    public ModelAndView rooms() {
        final ModelAndView modelAndView = new ModelAndView("rooms");
        // Example rooms
        roomList.add(room);
        roomList.add(room2);
        modelAndView.addObject("roomList", roomList);
        return modelAndView;
    }


    @GetMapping("/rooms/{roomID}")
    public ModelAndView roomdetails(@PathVariable("roomID") String roomID) {
        final ModelAndView modelAndView = new ModelAndView("roomdetails");
        room = new Room(roomID, 3, "testraum");
        modelAndView.addObject("room", room);
        return modelAndView;
    }

}
