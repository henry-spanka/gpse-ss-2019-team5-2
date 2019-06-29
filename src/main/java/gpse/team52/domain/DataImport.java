package gpse.team52.domain;


import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import gpse.team52.contract.mail.MailService;
import gpse.team52.service.mail.MailServiceImpl;
import org.springframework.mail.MailException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

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
                String[] split = line.split(";");
                if (split.length != 3) {
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
            System.out.println("Required format: csv containing: firstname, lastname, mail with header; seperated by ; ");
        }
    }

    /*This method handles the import of csv files
     * containing room or User data. It is implied that there is a header*/

    static public void csvImport(final MultipartFile file) {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).withSkipLines(1).build();
            String line[] = null;
            while ((line = csvReader.readNext())!= null){


            if (line.length==8) {

                Room room = new Room();
                Location location = new Location(line[0]);
                room.setLocation(location);
                room.setRoomName(line[1]);

                //number persons split for extra seats
                String[] seats = line[2].split("\\+");
                if (seats.length == 1) {
                    room.setSeats(Integer.parseInt(seats[0]));
                } else if (seats.length == 2) {
                    room.setSeats(Integer.parseInt(seats[0]));
                    room.setExpandableSeats(Integer.parseInt(seats[1]));
                }
                //3 spliting for different equiptment items

                String[] equiptments = line[3].split(",");
                for (int i = 0; i < equiptments.length; i++) {
                    Equipment equipment = new Equipment();
                    room.addEquipment(equipment);
                    equipment.addRoom(room);
                }


                room.setTelephone(line[4]);


                room.setNotes(line[5]);

                room.setOffice(line[6]);

                room.setRoomEmail(line[7]);
                System.out.println("A new romm was submitted");
            }else if (line.length==3){
                System.out.println( "New user");

            } else {

                    throw new Exception();
                }





    } }catch(
    Exception e)

    {
        System.out.println(" Exception: file is not a csv file or the layout does not fit.");
    }
}





}
