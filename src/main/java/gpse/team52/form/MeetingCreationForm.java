package gpse.team52.form;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import gpse.team52.domain.Location;
import gpse.team52.validator.LocationsHaveParticipants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Meeting Creation form which contains the basic parameters for creating a meeting.
 */
@LocationsHaveParticipants(groups = {MeetingCreationForm.ValidateMeetingDetails.class})
public class MeetingCreationForm {
    public interface ValidateMeetingDetails {
        //
    }

    public interface ValidateRoomSelection {
        //
    }

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
    @NotBlank(groups = {ValidateMeetingDetails.class})
    private String name;

    @Getter
    @Setter
    @NotNull(groups = {ValidateMeetingDetails.class})
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Getter
    @Setter
    @NotNull(groups = {ValidateMeetingDetails.class})
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Getter
    @Setter
    @NotNull(groups = {ValidateMeetingDetails.class})
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Getter
    @Setter
    @NotNull(groups = {ValidateMeetingDetails.class})
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Getter
    @Setter
    @NotNull(groups = {ValidateMeetingDetails.class})
    @Size(min = 1, message = "At least one location must be selected.", groups = {ValidateMeetingDetails.class})
    private List<String> locations;

    @Getter
    @Setter
    @NotNull(groups = {ValidateMeetingDetails.class})
    private Map<String, Integer> participants;

    @Getter
    @Setter
    @NotNull(groups = {ValidateMeetingDetails.class})
    private Map<String, List<String>> equipment;

    @Getter
    @Setter
    @NotNull(groups = {ValidateRoomSelection.class})
    private List<String> rooms;

    @Setter
    private List<Location> locationDetails;

    public LocalDateTime getStartDateTime() {
        return startDate.atTime(startTime);
    }

    public LocalDateTime getEndDateTime() {
        return endDate.atTime(endTime);
    }

    public int getDuration() {
        return (int) (Duration.between(getStartDateTime(), getEndDateTime()).getSeconds() / 60);
    }

    public Location getLocation(String uuid) {
        return locationDetails.stream()
        .filter(location -> location.getLocationId().toString().equals(uuid)).findFirst().orElseThrow();
    }

    public int getParticipant(String uuid) {
        Integer noParticipants = participants.get(uuid);

        if (noParticipants == null) {
            throw new NoSuchElementException("Hash key could not be found.");
        }

        return noParticipants;
    }

    public int getTotalParticipants() {
        return participants.values().stream().mapToInt(i -> i == null ? 0 : i).sum();
    }
}
