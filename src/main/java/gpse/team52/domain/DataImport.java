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


            while ((line = inBuffer.readLine()) != null) {

                String[] subSet = line.split(";");
                if (subSet.length != 8) {

                    throw new Exception();
                } else {
                    Room room = new Room();

                    for (int i = 0; i < subSet.length; i++) {
                        switch (i) {
                            case 0:
                                room.setLocation(subSet[i]);
                                break;
                            case 1:
                                room.setRoomName(subSet[i]);
                                break;
                            case 2:
                                String[] seats = subSet[i].split("\\+");
                                if (seats.length == 1) {
                                    room.setSeats(Integer.parseInt(seats[0]));
                                } else if (seats.length == 2) {
                                    room.setSeats(Integer.parseInt(seats[0]));
                                    room.setExtraSeats(Integer.parseInt(seats[1]));
                                }
                                break;
                            case 3:
                                Equipment equipment = new Equipment();
                                // room.setExtraSeats(subSet[i]);
                                break;
                            case 4:
                                room.setLocation(subSet[i]);
                                break;
                            case 5:
                                room.setLocation(subSet[i]);
                                break;
                            case 6:
                                room.setLocation(subSet[i]);
                                break;
                            case 7:
                                room.setLocation(subSet[i]);
                                break;


                        }

                        System.out.println(subSet[i]);
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(" Exception");
        }
    }


    public static void main(final String... args) {
        System.out.println("Gal");
        csvRoomImport("C:\\Users\\stell\\Desktop\\UniBielefeld\\SS19\\Software-Gruppen-Projekt\\Test.txt");
    }
}
