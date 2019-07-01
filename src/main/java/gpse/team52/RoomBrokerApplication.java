package gpse.team52;

import gpse.team52.contract.DBFileStorageService;
import gpse.team52.storage.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;



/**
 * Run Spring Boot.
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableConfigurationProperties(StorageProperties.class)
public class RoomBrokerApplication {

    public static void main(final String... args) {
        SpringApplication.run(RoomBrokerApplication.class, args);
    }

        @Bean
        CommandLineRunner init(final DBFileStorageService dbFileStorageService) {
            return (args) -> {
                //dbFileStorageService.deleteAll();
                //dbFileStorageService.init();
            };
        }



}

