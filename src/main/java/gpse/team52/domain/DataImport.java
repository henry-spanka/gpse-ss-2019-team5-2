package gpse.team52.domain;


import java.io.BufferedReader;
import java.io.FileReader;

import gpse.team52.*;

public class DataImport {

    /*This method handles the import of user files*/
    static public void csvUserImport(String path) {
        try {
            FileReader reader = new FileReader(path);
            BufferedReader inBuffer = new BufferedReader(reader);
            String line = null;


            while ((line = inBuffer.readLine()) != null) {
                System.out.println(line);


            }
        } catch (Exception e) {
            System.out.println("Required format: Username, Firstname, Lastname,  ");
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

            System.out.println("hal");
            while ((line = inBuffer.readLine()) != null) {
                if(isFirstLineHeader){
                    isFirstLineHeader=false;
                    System.out.println("lo");
                    continue;
                }
                String[] subSet = line.split(";");

                if (subSet.length != 8) {
                    System.out.println(subSet.length);

                    throw new Exception();
                } else {

                    Room room = new Room();
                    room.setLocation(subSet[0]);
                    room.setRoomName(subSet[1]);

                    //number persons split for extra seats
                    String[] seats = subSet[2].split("\\+");
                    if (seats.length == 1) {
                        room.setSeats(Integer.parseInt(seats[0]));
                    } else if (seats.length == 2) {
                        room.setSeats(Integer.parseInt(seats[0]));
                        room.setExtraSeats(Integer.parseInt(seats[1]));
                    }
                    //3 spliting for different equiptment items

                    String[] equiptments = subSet[3].split(",");
                    for (int i = 0; i < equiptments.length; i++) {
                        Equipment equipment = new Equipment();
                        room.addEquiptment(equipment);
                        equipment.setEquipmentName(equiptments[i]);
                        equipment.setGetRoomID(room.getRoomID());
                    }


                        room.setTelephone(subSet[4]);


                    room.setNotes(subSet[5]);

                    room.setOffice(subSet[6]);

                    room.setMailAdress(subSet[7]);

                }


            }


        } catch (Exception e) {
            System.out.println(" Exception: file is not a csv file or the layout does not fit.");
        }
    }

}
