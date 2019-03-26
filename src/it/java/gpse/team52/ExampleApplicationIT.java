package gpse.team52;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ExampleApplication.class)
public class ExampleApplicationIT {

    @Test
    public void contextLoads() {
        //Tests Application-Start.
    }

}
