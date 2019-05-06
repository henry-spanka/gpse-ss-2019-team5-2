package gpse.team52.web;

//import gpse.team52.Equipment;
import gpse.team52.Room;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomdisplayController {

    private List<Room> roomList = new ArrayList<Room>();
    // private List<Equipment> equipmentList = new ArrayList<Equipment>();

    public RoomdisplayController() {
        // should get a list of rooms to choose for meeting, just set any default
        //Example rooms
        roomList.add(new Room(100, "mail100", "name", 5, 6, "description"));
        roomList.add(new Room(101, "mail101", "name2", 2, 2, "description2"));
        // test equipment
        /*
        equipmentList.add(new Equipment(100, "table_1", "chalk", 100, false ));
        equipmentList.add(new Equipment(101, "table_2", "chalk", 101, false ));
        equipmentList.add(new Equipment(102, "whiteboard_1", "pen", 100, true ));
        equipmentList.add(new Equipment(103, "whiteboard_2", "pen", 101, false ));
        equipmentList.add(new Equipment(104, "beamer_1", "", 100, true ));
         */
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
        //modelAndView.addObject("equipmentList", equipmentList);
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
    /*
    private Equipment getEquipment(String roomID) {
        for (Equipment equipment : equipmentList) {
            if (equipment.getGetRoomID() == Integer.parseInt(roomID)) {
                return equipment;
            }
        }
        return null;
    }
     */

    //TODO get this working!
    @RequestMapping("/rooms/confirm")
    public ModelAndView confirm(@RequestParam(name = "room", required = true) String room) { // , @RequestParam(name = "meeting", required = true) String meeting
        //String room = "100"; // any test data, remove!!
        final ModelAndView modelAndView = new ModelAndView("confirmbooking");
        modelAndView.addObject(getRoom(room));//benötigt, und Meeting auch hinzufügen!
        return modelAndView;
    }

}
