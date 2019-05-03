package gpse.team52;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Run Spring Boot.
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class RoomBrokerApplication {

    public static void main(final String... args) {
        SpringApplication.run(RoomBrokerApplication.class, args);
    }
}
