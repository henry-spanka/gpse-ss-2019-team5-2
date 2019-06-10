package gpse.team52.web;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Room display page controller.
 */
@Controller
public class RoomdisplayController {

    @Autowired
    private RoomService roomService;

    /**
     * @return Page with available rooms to choose from
     */
    @GetMapping("/rooms") //TODO request params for meeting, set required = true
    public ModelAndView rooms(
    /*
    @RequestParam(name = "start", required = false) int start,
    @RequestParam(name = "end", required = false) int end,
    @RequestParam(name = "participants", required = false) int seats,
     */
    final @RequestParam(name = "location", required = false) Location location,
    final @RequestParam(name = "date", required = false) Date date,
    final @RequestParam(name = "equipment", required = false) List<Equipment> equipment
    ) {
        final ModelAndView modelAndView = new ModelAndView("selectMeetingRooms");

        // TODO instead of all rooms, get only the ones which are suitable
        // Iterable<Room> roomList = roomService.getAvailableRooms(location, seats, date, start, end,equipment);
        //!! TEST !!
        //location = roomService.getLocation("Bielefeld").orElseThrow();
        final Iterable<Room> roomList = roomService.getAllRooms();
        //!! TEST !!
        final List<Room> rooms = new ArrayList<>(); // is there no other way to check whether an iterable is empty?
        roomList.forEach(rooms::add);
        if (rooms.isEmpty()) {
            modelAndView.addObject("noRoom", true);
        }
        modelAndView.addObject("roomList", roomList);
        return modelAndView;
    }

    /**
     * Details for each room.
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

    /**
     * Shows page to confirm selected rooms and parameters of meeting.
     * @param roomID rooms which are selected
     * @return Page to check and submit data or, in case of error, page with rooms to choose from
     */
    @RequestMapping("/rooms/confirm")
    public ModelAndView confirm(
    final @RequestParam(name = "roomID", required = false) String roomID) { // , @RequestParam(name = "meeting", required = true) String meeting
        if (roomID == null) { //return rooms-page with alert message
            final ModelAndView roomsError = new ModelAndView("selectMeetingRooms");
            roomsError.addObject("roomList", roomService.getAllRooms());
            roomsError.addObject("error", true);
            return roomsError;
        }
        // else return confirmation page
        final ModelAndView modelAndView = new ModelAndView("confirmbooking");
        final String[] chosen = roomID.split(",");
        final List<Room> chosenRooms = new ArrayList<>();
        for (int i = 0; i < chosen.length; i++) {
            chosenRooms.add(roomService.getRoom(UUID.fromString(chosen[i])).orElseThrow());
        }
        modelAndView.addObject("chosenRooms", chosenRooms); //benötigt, und Meeting auch hinzufügen!
        return modelAndView;
    }
    //TODO success message and return to start page
}
