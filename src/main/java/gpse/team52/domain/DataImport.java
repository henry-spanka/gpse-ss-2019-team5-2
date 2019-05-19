package gpse.team52.domain;


import java.io.BufferedReader;
import java.io.FileReader;

public class DataImport {

    /*This method handles the import of user files*/
    static public void csvUserImport(String path) {
        try {
            FileReader reader = new FileReader(path);
            BufferedReader inBuffer = new BufferedReader(reader);
            String line = null;


            while ((line = inBuffer.readLine()) != null) {
                String[] split =line.split(";");
                if(split.length!=3){
                    throw new Exception();
                    }



            }
        } catch (Exception e) {
            System.out.println("Required format: csv containing: firstname, lastname, mail without header; seperated by ; ");
        }
    }

    /*This method handles the import of room files*/

    static public void csvRoomImport(String path) {
        try {
            int counter = 0;
            FileReader reader = new FileReader(path);
            BufferedReader inBuffer = new BufferedReader(reader);
            String line = null;
            Boolean isFirstLineHeader = true;


            while ((line = inBuffer.readLine()) != null) {
                if(isFirstLineHeader){
                    isFirstLineHeader=false;

                    continue;
                }
                String[] subSet = line.split(";");

                if (subSet.length != 8) {
                    System.out.println(subSet.length);

                    throw new Exception();
                } else {

                    Room room = new Room();
                    Location location = new Location(subSet[0]);
                    room.setLocation(location);
                    room.setRoomName(subSet[1]);

                    //number persons split for extra seats
                    String[] seats = subSet[2].split("\\+");
                    if (seats.length == 1) {
                        room.setSeats(Integer.parseInt(seats[0]));
                    } else if (seats.length == 2) {
                        room.setSeats(Integer.parseInt(seats[0]));
                        room.setExpandableSeats(Integer.parseInt(seats[1]));
                    }
                    //3 spliting for different equiptment items

                    String[] equiptments = subSet[3].split(",");
                    for (int i = 0; i < equiptments.length; i++) {
                        Equipment equipment = new Equipment();
                        room.addEquipment (equipment);
                       equipment.addRoom(room);}


                        room.setTelephone(subSet[4]);


                    room.setNotes(subSet[5]);

                    room.setOffice(subSet[6]);

                    room.setRoomEmail(subSet[7]);

                }


            }


        } catch (Exception e) {
            System.out.println(" Exception: file is not a csv file or the layout does not fit.");
        }
    }

}
