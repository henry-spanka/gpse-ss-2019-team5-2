package gpse.team52.seeder;

import javax.annotation.PostConstruct;

import gpse.team52.contract.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Initializes the default locations.
 */
@Service
@RequiredArgsConstructor
public class InitializeDefaultLocations {

    private final RoomService roomService;

    /**
     * Initializes locations to use them in DefaultMeetings and DefaultRooms.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @PostConstruct
    public void init() {

        roomService.createLocation("Bielefeld");
        roomService.createLocation("Gütersloh");
        roomService.createLocation("Düsseldorf");

    }
}
