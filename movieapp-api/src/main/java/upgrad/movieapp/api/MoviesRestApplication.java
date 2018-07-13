package upgrad.movieapp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import upgrad.movieapp.api.config.ApiConfiguration;
import upgrad.movieapp.api.config.WebConfiguration;
import upgrad.movieapp.service.config.ServiceConfiguration;

@SpringBootApplication
@Import({ApiConfiguration.class, WebConfiguration.class, ServiceConfiguration.class})
public class MoviesRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesRestApplication.class, args);
    }

}