package gpse.team52.domain;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataExport {
    RoomService roomService;
    UserService userService;
    MeetingService meetingService;
    public DataExport(RoomService roomService, UserService userService, MeetingService meetingService){
        this.meetingService = meetingService;
        this.roomService = roomService;
        this.userService = userService;
        try {
            writeRoomFile();
            writeUserFile();
            writeMeetingFile();
        }catch (IOException e){

        }

    }

    public void writeRoomFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\stell\\Documents\\gpse-ss-2019-team5-2\\src\\main\\resources\\templates\\csv\\roomedRooms.csv", true));
        String string="Standort;Name;Max.Personen;Ausstattung;Telephone;Notizen;Office;E-Mailadresse"+System.lineSeparator();
        for (Room room : roomService.getAllRooms()){
            string=string +room.roomToString()+System.lineSeparator(); }


        writer.write(string);
        writer.close();
    }

    public void writeUserFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\stell\\Documents\\gpse-ss-2019-team5-2\\src\\main\\resources\\templates\\csv\\roomedUsers.csv", true));
            String string="username;firstname;lastname;email;role,list"+System.lineSeparator();
            for (User user : userService.getAllUsers()){
                string=string + user.userToString()+System.lineSeparator(); }


            writer.write(string);
            writer.close();

        }catch (IOException e){

        }
    }

    //
    public void writeMeetingFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\stell\\Documents\\gpse-ss-2019-team5-2\\src\\main\\resources\\templates\\csv\\roomedMeetings.csv", true));
            String string="title;startdate;enddate;participants;owner;confirmed;description"+System.lineSeparator();
            for (Meeting meeting : meetingService.getAllMeetings()){
                string=string + meeting.meetingToString() +System.lineSeparator(); }


            writer.write(string);
            writer.close();

        }catch (IOException e){

        }
    }
}
