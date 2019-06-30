package gpse.team52.domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataExport {
    public DataExport(){
        try {
            writeRoomFile();
        }catch (IOException e){
            System.out.println("DAs war wohl nichts");
        }

    }

    public void writeRoomFile()
    throws IOException {
        String str = "World";
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\stell\\Documents\\gpse-ss-2019-team5-2\\src\\main\\resources\\templates\\csv\\roomedRooms.csv", true));
        writer.append(' ');
        writer.append(str);

        writer.close();
    }
}
