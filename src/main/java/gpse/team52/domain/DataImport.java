package gpse.team52.domain;


import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;
import gpse.team52.contract.mail.MailService;
import gpse.team52.exception.EmailNotFoundException;

import org.springframework.mail.MailException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class DataImport {
    UserService userService;
    MailService mailService;


    public DataImport(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;

    }

    private final ArrayList<Candidate> candidateList = new ArrayList<>();


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
                parseRoom(line);

            } else if (line.length == 3) {          // equals user submitting format
                isUser = true;

                try {
                    Candidate candidate = new Candidate(line[0], line[1], line[2]);
                    candidateList.add(candidate);
                } catch (Exception e) {
                    except = true;
                    continue;
                }


            } else if (line.length == 7) { //equals meeting format
                parseMeeting(line);

            } else {
                System.out.println(line.length);

                throw new Exception();
            }


            if (isUser && candidateList.size() != 0) {
                notifyCandidate();

            }

            if (except) {
                System.out.println(line.length);
                throw new Exception();
            }
        }

    }

    /* Parses String array to room */
    private void parseRoom(String[] line){
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
        System.out.println(room.roomToString());
         }

    //this method sends an e mail to the candidates
    private void notifyCandidate() {

        for (int i = 0; i < candidateList.size(); i++) {
            try {
                ModelAndView modelAndView = new ModelAndView("email/mail-import.html");


                mailService.sendEmailToCAndidate(candidateList.get(i), "You have beeen added to Roomed", modelAndView);
                System.out.println("MAil sent");

            } catch (MailException e) {
                continue;
            }
        }


    }

   /* Parses line to meeting*/
    private void parseMeeting(String[] line){
        Meeting meeting = new Meeting(line[0]);

        //String to LocalDAteTime Parser
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        meeting.setStartAt(LocalDateTime.parse(line[1], formatter));

        meeting.setEndAt(LocalDateTime.parse(line[2], formatter));


        //parse participants and check if he is user #3
        String[] participants = line[3].split(",");
        for (int i = 0; i < participants.length; i++) {

            String[] participant = participants[i].split("_");
            Participant participant1;
            try {
                User participant2 = userService.loadUserByEmail(participant[0]);
                participant1 = new Participant(participant2);

            } catch (EmailNotFoundException e) {
                participant1 = new Participant(participant[0], participant[1], participant[2]);

            }
            meeting.addParticipant(participant1);
            participant1.setMeeting(meeting);


        }
        //If owner User he becomes user, if not there is no owner
        try {
            User owner = userService.loadUserByEmail(line[4]);
            meeting.setOwner(owner);
        } catch (EmailNotFoundException e) {

        }
        meeting.setConfirmed(Boolean.parseBoolean(line[5]));

        meeting.setDescription(line[6]);

        System.out.println("Meeting submitted");


    }


}
