package gpse.team52.domain;

import gpse.team52.contract.RoomService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataExport {
    RoomService roomService;
    public DataExport(RoomService roomService){
        this.roomService = roomService;
        try {
            writeRoomFile();
        }catch (IOException e){
            System.out.println("DAs war wohl nichts");
        }

    }

    public void writeRoomFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\stell\\Documents\\gpse-ss-2019-team5-2\\src\\main\\resources\\templates\\csv\\roomedRooms.csv", true));
        String string="";
        for (Room room : roomService.getAllRooms()){
            string=string +room.roomToString()+System.lineSeparator();

           // writer.append(room.roomToString());
           // writer.append("/n");

        }


        writer.write(string);
        writer.close();
    }
}
