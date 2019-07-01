package gpse.team52.web;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import gpse.team52.contract.MeetingService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ICal Controller.
 */
@RestController
public class ICalController {
    @Autowired
    private UserService userService;

    @Autowired
    private MeetingService meetingService;

    /**
     * Get iCal for a user.
     *
     * @param token unique oken generated for the user.
     * @return calendar in iCal format.
     */
    @GetMapping("/ical/{token}")
    public ResponseEntity getICalForToken(final @PathVariable("token") UUID token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        User user;

        try {
            user = userService.findUserByICalToken(token).orElseThrow();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        final Iterable<Meeting> meetings = meetingService.findByStartAtWithUser(user);

        final ICalendar ical = new ICalendar();

        for (final Meeting meeting : meetings) {
            final VEvent event = new VEvent();
            event.setSummary(meeting.getTitle()).setLanguage("en-us");
            event.setDescription(meeting.getDescription()).setLanguage("en-us");
            event.setDateStart(Date.from(meeting.getStartAt().toInstant(ZoneOffset.ofHours(2))));
            event.setDateEnd(Date.from(meeting.getEndAt().toInstant(ZoneOffset.ofHours(2))));

            ical.addEvent(event);
        }

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("charset", "utf-8");
        responseHeaders.set("Content-Disposition", "inline; filename=\"calendar.ics\"");
        responseHeaders.set("filename", "calendar.ics");

        return ResponseEntity.ok().contentType(MediaType.valueOf("text/calendar"))
        .headers(responseHeaders).body(Biweekly.write(ical).go());
    }
}
