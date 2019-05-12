package gpse.team52.web;

//import gpse.team52.Equipment;
import gpse.team52.Equipment;
import gpse.team52.Room;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomdisplayController {

    private List<Room> roomList = new ArrayList<Room>();
    private List<Equipment> equipmentList = new ArrayList<Equipment>();

    public RoomdisplayController() {
        // should get a list of rooms to choose for meeting, just set any default
        //Example rooms
        roomList.add(new Room(100, "mail100", "name", 5, 6, "description"));
        roomList.add(new Room(101, "mail101", "name2", 2, 2, "description2"));
        // test equipment
        equipmentList.add(new Equipment(100, "table_1", "chalk", 100, false ));
        equipmentList.add(new Equipment(101, "table_2", "chalk", 101, false ));
        equipmentList.add(new Equipment(102, "whiteboard_1", "pen", 100, true ));
        equipmentList.add(new Equipment(103, "whiteboard_2", "pen", 101, false ));
        equipmentList.add(new Equipment(104, "beamer_1", "", 100, true ));

    }

    /**
     * @return Page with rooms to choose from
     */
    @GetMapping("/rooms")
    public ModelAndView rooms() {
        final ModelAndView modelAndView = new ModelAndView("rooms");
        modelAndView.addObject("roomList", roomList);
        return modelAndView;
    }


    @GetMapping("/rooms/{roomID}")
    public ModelAndView roomdetails(@PathVariable("roomID") String roomID) {
        final ModelAndView modelAndView = new ModelAndView("roomdetails");
        Room room = getRoom(roomID); // should add some error handling, if the get method fails
        //modelAndView.addObject("equipmentList", equipmentList);
        modelAndView.addObject("room", room);
        List<Equipment> equipment = getEquipment(roomID);
        modelAndView.addObject("equipmentList", equipment);
        return modelAndView;
    }

    //TODO use database instead
    private Room getRoom(String roomID) {
        for (Room room : roomList) {
            if (roomID != null && room.getRoomID() == Integer.parseInt(roomID)) {
                return room;
            }
        }
        return null;
    }

    private List<Equipment> getEquipment(String roomID) {
        List<Equipment> roomEquipmentList = new ArrayList<Equipment>();
        int intRoomID = Integer.parseInt(roomID);
        for (Equipment equipment : equipmentList) {
            if (equipment.getRoomID() == intRoomID) {
                 roomEquipmentList.add(equipment);
            }
        }
        System.out.println(roomID + roomEquipmentList);
        return roomEquipmentList;
    }


    @RequestMapping("/rooms/confirm")
    public ModelAndView confirm(
    @RequestParam(name = "roomID", required = false) String roomID,
    @RequestParam(name = "error", required = false) String error) { // , @RequestParam(name = "meeting", required = true) String meeting
        if(roomID == null){ //return rooms-page with alert message
            final ModelAndView roomsError = new ModelAndView("rooms");
            roomsError.addObject("roomList", roomList);
            roomsError.addObject("error", true);
            return roomsError;
        }
        // else return confirmation page
        final ModelAndView modelAndView = new ModelAndView("confirmbooking");
        String[] chosen = roomID.split(",");
        List<Room> chosenRooms = new ArrayList<Room>();
        for(int i = 0; i < chosen.length; i++){
            chosenRooms.add(getRoom(chosen[i]));
        }
        modelAndView.addObject("chosenRooms", chosenRooms);//benötigt, und Meeting auch hinzufügen!
        return modelAndView;
    }

}
