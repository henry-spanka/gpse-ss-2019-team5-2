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
                }
                for (int i = 0; i < subSet.length; i++) {
                    System.out.println(subSet[i]);
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
