package org.test;

import java.io.Serializable;
import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class DemoApplication implements Serializable {

    @Bean
    CommandLineRunner runner(ReservationRepository rr) {
        return args -> {
            Stream.of("Dr. Rod", "Dr. Syer", "ALL THE COMMUNITY", "Josh")
                    .map(Reservation::new)
                    .forEach(rr::save);
            rr.findAll().forEach(System.out::println);
        };
    }

    @Bean
    HealthIndicator myIndic() {
        return () -> Health.status("It works !").build();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
