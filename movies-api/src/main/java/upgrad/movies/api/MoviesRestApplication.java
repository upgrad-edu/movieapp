package upgrad.movies.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import upgrad.movies.api.config.ApiConfiguration;
import upgrad.movies.api.config.WebConfiguration;
import upgrad.movies.service.ServiceConfiguration;

@SpringBootApplication
@Import({ApiConfiguration.class, WebConfiguration.class, ServiceConfiguration.class})
public class MoviesRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesRestApplication.class, args);
    }

}