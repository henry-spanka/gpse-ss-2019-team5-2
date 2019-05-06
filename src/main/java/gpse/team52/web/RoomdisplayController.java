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

    /**
     *
     * @param error
     * @return Page with rooms to choose from
     */
    @GetMapping("/rooms")
    public ModelAndView rooms(@RequestParam(name = "error", required = false) String error) {
        final ModelAndView modelAndView = new ModelAndView("rooms");
        modelAndView.addObject("roomList", roomList);
        modelAndView.addObject("error", error != null);
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

    //TODO get this working!
    @GetMapping("/rooms/confirm")
    public ModelAndView confirm() { // @RequestParam(name = "room", required = true) String room, @RequestParam(name = "meeting", required = true) String meeting
        String room = "100"; // any test data, remove!!
        final ModelAndView modelAndView = new ModelAndView("confirmbooking");
        modelAndView.addObject(getRoom(room));//benötigt, und Meeting auch hinzufügen!
        return modelAndView;
    }

}
