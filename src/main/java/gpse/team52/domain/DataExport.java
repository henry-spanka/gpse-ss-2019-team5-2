package gpse.team52.domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;

/**
 * Data Export class.
 */
public class DataExport {
    private RoomService roomService;
    private UserService userService;
    private MeetingService meetingService;

    /**
     * Data Export Constructor.
     * @param roomService Roomservice.
     * @param userService Userservice.
     * @param meetingService Meetingservice.
     */
    public DataExport(final RoomService roomService, final UserService userService, final MeetingService meetingService) {
        this.meetingService = meetingService;
        this.roomService = roomService;
        this.userService = userService;
        try {
            writeRoomFile();
            writeUserFile();
            writeMeetingFile();
        } catch (IOException e) {
            //
        }

    }

    /**
     * writeRoomFile.
     * @throws IOException Thrown on IO error.
     */
    public void writeRoomFile() throws IOException {
        final BufferedWriter writer = new BufferedWriter(new FileWriter("roomedRooms.csv", true));
        String string = "Standort;Name;Max.Personen;Ausstattung;Telephone;Notizen;Office;E-Mailadresse"
        + System.lineSeparator();
        for (final Room room : roomService.getAllRooms()) {
            string = string + room.roomToString() + System.lineSeparator();
        }


        writer.write(string);
        writer.close();
    }

    /**
     * writeUserFile.
     */
    public void writeUserFile() {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter("roomedUsers.csv", true));
            String string = "username;firstname;lastname;email;role,list"
            + System.lineSeparator();
            for (final User user : userService.getAllUsers()) {
                string = string + user.userToString() + System.lineSeparator();
            }


            writer.write(string);
            writer.close();

        } catch (IOException e) {
            //
        }
    }

    /**
     * writeMeetingFile.
     */
    public void writeMeetingFile() {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter("roomedMeetings.csv", true));
            String string = "title;startdate;enddate;participants;owner;confirmed;description" + System.lineSeparator();
            for (final Meeting meeting : meetingService.getAllMeetings()) {
                string = string + meeting.meetingToString() + System.lineSeparator();
            }


            writer.write(string);
            writer.close();

        } catch (IOException e) {
            //
        }
    }
}
