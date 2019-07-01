package gpse.team52.domain;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import gpse.team52.contract.UserService;
import gpse.team52.contract.mail.MailService;
import gpse.team52.exception.EmailNotFoundException;
import org.springframework.mail.MailException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


/**
 * Data Import class.
 */
public class DataImport {
    private UserService userService;
    private MailService mailService;

    private final List<Candidate> candidateList = new ArrayList<>();


    /**
     * Data Import constructor.
     *
     * @param userService userService.
     * @param mailService mailService.
     */
    public DataImport(final UserService userService, final MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;

    }


    /**
     * This method handles the import of csv files.
     *
     * @param file the file to import.
     * @throws IOException Thrown on error.
     */

    public void csvImport(final MultipartFile file) throws IOException { //NOPMD

        Boolean isUser = false; //NOPMD
        Boolean except = false; //NOPMD
        final Reader reader = new InputStreamReader(file.getInputStream());
        final CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(new CSVParserBuilder().withSeparator(';')
        .build()).withSkipLines(1).build();
        String[] line; //NOPMD
        while ((line = csvReader.readNext()) != null) { //NOPMD
            //equals a room file, therefore a new room will be created for every new line
            if (line.length == 8) { //NOPMD
                parseRoom(line);

            } else if (line.length == 3) { //NOPMD // equals user submitting format
                isUser = true;

                try {
                    final Candidate candidate = new Candidate(line[0], line[1], line[2]); //NOPMD
                    candidateList.add(candidate);
                } catch (Exception e) { //NOPMD
                    except = true;
                    continue;
                }


            } else if (line.length == 7) { //NOPMD //equals meeting format
                parseMeeting(line);

            } else {
                throw new IOException();
            }


            if (isUser && !candidateList.isEmpty()) {
                notifyCandidate();

            }

            if (except) {
                throw new IOException();
            }
        }

    }

    /* Parses String array to room */
    private void parseRoom(final String... line) {
        final Room room = new Room();
        final Location location = new Location(line[0]);
        room.setLocation(location);
        room.setRoomName(line[1]);

        //number persons split for extra seats
        final String[] seats = line[2].split("\\+");
        if (seats.length == 1) { //NOPMD
            room.setSeats(Integer.parseInt(seats[0]));
        } else if (seats.length == 2) { //NOPMD
            room.setSeats(Integer.parseInt(seats[0]));
            room.setExpandableSeats(Integer.parseInt(seats[1]));
        }

        // spliting for different equipment items
        final String[] equipments = line[3].split(",");
        for (final String equip : equipments) { //NOPMD
            final Equipment equipment = new Equipment(); //NOPMD
            room.addEquipment(equipment);
            equipment.addRoom(room);
        }

        room.setTelephone(line[4]);
        room.setNotes(line[5]);
        room.setOffice(line[6]);
        room.setRoomEmail(line[7]);
        // System.out.println(room.roomToString());
    }

    //this method sends an e mail to the candidates
    private void notifyCandidate() { //NOPMD
        final ModelAndView modelAndView = new ModelAndView("email/mail-import.html"); //NOPMD

        for (final Candidate candidate : candidateList) {
            try {
                mailService.sendEmailToCAndidate(candidate, "You have beeen added to Roomed", modelAndView);

            } catch (MailException e) {
                continue;
            }
        }


    }

    /* Parses line to meeting*/
    private void parseMeeting(final String[] line) { //NOPMD
        final Meeting meeting = new Meeting(line[0]);

        //String to LocalDAteTime Parser
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        meeting.setStartAt(LocalDateTime.parse(line[1], formatter));

        meeting.setEndAt(LocalDateTime.parse(line[2], formatter));


        //parse participants and check if he is user #3
        final String[] participants = line[3].split(",");
        for (final String part : participants) {

            final String[] participant = part.split("_");
            Participant participant1;
            try {
                final User participant2 = userService.loadUserByEmail(participant[0]);
                participant1 = new Participant(participant2); //NOPMD

            } catch (EmailNotFoundException e) {
                participant1 = new Participant(participant[0], participant[1], participant[2]); //NOPMD

            }
            meeting.addParticipant(participant1);
            participant1.setMeeting(meeting);


        }
        //If owner User he becomes user, if not there is no owner
        try {
            final User owner = userService.loadUserByEmail(line[4]);
            meeting.setOwner(owner);
        } catch (EmailNotFoundException e) { //NOPMD
            //
        }
        meeting.setConfirmed(Boolean.parseBoolean(line[5]));

        meeting.setDescription(line[6]);

        // System.out.println(meeting.meetingToString());


    }


}
