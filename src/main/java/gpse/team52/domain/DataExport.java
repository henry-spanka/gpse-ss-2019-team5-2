package gpse.team52.domain;

import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataExport {
    RoomService roomService;
    UserService userService;
    public DataExport(RoomService roomService, UserService userService){
        this.roomService = roomService;
        this.userService = userService;
        try {
            writeRoomFile();
            writeUserFile();
        }catch (IOException e){
            System.out.println("DAs war wohl nichts");
        }

    }

    public void writeRoomFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\stell\\Documents\\gpse-ss-2019-team5-2\\src\\main\\resources\\templates\\csv\\roomedRooms.csv", true));
        String string="";
        for (Room room : roomService.getAllRooms()){
            string=string +room.roomToString()+System.lineSeparator(); }


        writer.write(string);
        writer.close();
    }

    public void writeUserFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\stell\\Documents\\gpse-ss-2019-team5-2\\src\\main\\resources\\templates\\csv\\roomedUsers.csv", true));
            String string="";
            for (User user : userService.getAllUsers()){
                string=string + user.userToString()+System.lineSeparator(); }


            writer.write(string);
            writer.close();

        }catch (IOException e){

        }
    }
}
