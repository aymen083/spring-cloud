package org.test;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Philippe
 */
@RestController
@RequestMapping("/reservations")
public class ReservationApiGatewayRestController {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    public Collection<String> getReservationNamesFallback() {
        return Collections.emptyList();
    }
    
    @GetMapping("/names")
    @HystrixCommand(fallbackMethod = "getReservationNamesFallback")
    public Collection<String> getReservationNames() {

        ParameterizedTypeReference<Resources<Reservation>> ptr = 
                new ParameterizedTypeReference<Resources<Reservation>>(){};
        
        ResponseEntity<Resources<Reservation>> reponseEntity =
                this.restTemplate.exchange("http://reservation-service/reservations",
                HttpMethod.GET, null, ptr);
        
        return reponseEntity
                .getBody()
                .getContent()
                .stream()
                .map(Reservation::getReservationName)
                .collect(Collectors.toList());
    }

}

class Reservation {

    private Long id;
    private String reservationName;

    public Long getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }

}
