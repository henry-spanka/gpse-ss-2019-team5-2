package gpse.team52.domain;


import gpse.team52.contract.mail.MailService;
import gpse.team52.service.mail.MailServiceImpl;
import org.springframework.mail.MailException;
import org.springframework.web.servlet.ModelAndView;
import java.io.BufferedReader;
import java.io.FileReader;

public class DataImport {

   // private final MailServiceImpl mailService = new MailServiceImpl() ;

    /*This method handles the import of user files
    * when a csv file containing firstname, lastname and mail adress
    * is uploded the person gets an email*/
     public void csvUserImport(String path) {
        try {
            FileReader reader = new FileReader(path);
            BufferedReader inBuffer = new BufferedReader(reader);
            String line = null;


            while ((line = inBuffer.readLine()) != null) {
                String[] split =line.split(";");
                if(split.length!=3){
                    throw new Exception();
                    
                    }
                final ModelAndView modelAndView = new ModelAndView("email/mail-import.html");


                  /*  @Override
                    public void sendEmailMessageToUser(User user, String subject, String message, boolean html) throws MailException {

                    }*/

                   /* @Override
                    public void sendEmailTemplateToUser(User user, String subject, ModelAndView template) throws MailException {

                    }*/
                }

               // mailService.sendEmailTemplateToUser(user, "Email Verification", modelAndView);




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
