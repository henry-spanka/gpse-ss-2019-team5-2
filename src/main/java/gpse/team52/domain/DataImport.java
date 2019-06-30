package gpse.team52.domain;


import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import gpse.team52.contract.mail.MailService;
import gpse.team52.service.mail.MailServiceImpl;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DataImport {
    private final ArrayList<Candidate> candidateList = new ArrayList<>();

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

    public void csvImport(final MultipartFile file) throws Exception {

        Boolean isUser = false;
        Boolean except = false;
        Reader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).withSkipLines(1).build();
        String line[] = null;
        while ((line = csvReader.readNext()) != null) {
            //equals a room file, therefore a new room will be created for every new line
            if (line.length == 8) {

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

                // spliting for different equipment items
                String[] equipments = line[3].split(",");
                for (int i = 0; i < equipments.length; i++) {
                    Equipment equipment = new Equipment();
                    room.addEquipment(equipment);
                    equipment.addRoom(room);
                }

                room.setTelephone(line[4]);
                room.setNotes(line[5]);
                room.setOffice(line[6]);
                room.setRoomEmail(line[7]);
                System.out.println("A new romm was submitted");
            } else if (line.length == 3) {          // equals user submitting format
                isUser = true;

                try {
                    Candidate candidate = new Candidate(line[0], line[1], line[2]);
                    candidateList.add(candidate);
                } catch (Exception e) {
                    except = true;
                    continue;
                }


            } else if (line.length == 9) {
                Meeting meeting = new Meeting(line[0]);
                //owner;room;description

                //String to LocalDAteTime Parser
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                meeting.setStartAt(LocalDateTime.parse(line[1], formatter));

                meeting.setEndAt(LocalDateTime.parse(line[2], formatter));


                //parse participants #3
                String[] participants = line[3].split(",");
                for (int i = 0; i < participants.length; i++) {
                    String[] participant = participants[i].split("_");

                    Participant participant1 = new Participant(participant[0], participant[1], participant[2]);
                    meeting.addParticipant(participant1);
                }
                //look up owner as user --> how?    #4
                //meeting.setOwner(line[4]);

                //parse rooms --> also look up in db by id?     #5

                meeting.setConfirmed(Boolean.parseBoolean(line[6]));

                meeting.setDescription(line[7]);

                System.out.println("Meeting submitted");


            } else {
                System.out.println(line.length);

                throw new Exception();
            }


            if (isUser && candidateList.size() != 0) {
                notifyCandidate();

            }

            if (except) {
                throw new Exception();
            }
        }

    }

    //this method sends an e mail to the candidates
    public void notifyCandidate() {


    }


}
