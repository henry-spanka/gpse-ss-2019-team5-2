package gpse.team52;

import gpse.team52.service.DBFileStorageServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import gpse.team52.storage.StorageProperties;
import gpse.team52.contract.DBFileStorageService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.concurrent.Executor;


/**
 * Run Spring Boot.
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
@EnableTransactionManagement
@EnableConfigurationProperties(StorageProperties.class)
public class RoomBrokerApplication {

    public static void main(final String... args) {
        SpringApplication.run(RoomBrokerApplication.class, args);
    }

        @Bean
        CommandLineRunner init(DBFileStorageService dbFileStorageService) {
            return (args) -> {
                dbFileStorageService.deleteAll();
                dbFileStorageService.init();
            };
        }

        @Bean
        public Executor taskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(2);
            executor.setMaxPoolSize(2);
            executor.setThreadNamePrefix("MeetingCheck");
            executor.initialize();
            return executor;
        }



}

