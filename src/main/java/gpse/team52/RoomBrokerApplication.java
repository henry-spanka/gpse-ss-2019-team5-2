package gpse.team52;

import gpse.team52.service.DBFileStorageServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import gpse.team52.storage.StorageProperties;
import gpse.team52.contract.DBFileStorageService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;



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
        CommandLineRunner init(DBFileStorageService dbFileStorageService) {
            return (args) -> {
                // This doesnt work properly.
                /*dbFileStorageService.deleteAll();
                dbFileStorageService.init();*/
            };
        }



}

