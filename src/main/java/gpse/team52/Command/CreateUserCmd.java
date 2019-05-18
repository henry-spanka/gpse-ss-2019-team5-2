package gpse.team52.Command;

import lombok.Getter;
import lombok.Setter;

/**
 * class that reads the input of the edit profile page
 */
public class CreateUserCmd {
    @Getter
    @Setter
    String firstname;
    @Getter
    @Setter
    String lastname;
    @Getter
    @Setter
    String location;


}
