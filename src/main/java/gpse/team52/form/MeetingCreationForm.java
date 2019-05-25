package gpse.team52.form;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import gpse.team52.validator.LocationsHaveParticipants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Meeting Creation form which contains the basic parameters for creating a meeting.
 */
@LocationsHaveParticipants
public class MeetingCreationForm {
    /**
     * Set the default start/end time on object creation.
     */
    public MeetingCreationForm() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        currentDateTime = currentDateTime.withSecond(0).withNano(0).plusMinutes((75 - currentDateTime.getMinute()) % 15);

        startDate = currentDateTime.toLocalDate();
        startTime = currentDateTime.toLocalTime();

        LocalDateTime endDateTime = currentDateTime.plusMinutes(90);

        endDate = endDateTime.toLocalDate();
        endTime = endDateTime.toLocalTime();
    }

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Getter
    @Setter
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Getter
    @Setter
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Getter
    @Setter
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Getter
    @Setter
    @NotNull
    @Size(min = 1, message = "At least one location must be selected.")
    private List<String> locations;

    @Getter
    @Setter
    @NotNull
    private Map<String, Integer> participants;

    @Getter
    @Setter
    @NotNull
    private Map<String, List<String>> equipment;
}
