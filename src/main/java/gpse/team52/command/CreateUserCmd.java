package gpse.team52.command;

import lombok.Getter;
import lombok.Setter;

/**
 * class that reads the input of the edit profile page.
 */

public class CreateUserCmd {

    @Getter
    @Setter
    private String firstname;

    @Getter
    @Setter
    private String lastname;

    @Getter
    @Setter
    private String location;

}

