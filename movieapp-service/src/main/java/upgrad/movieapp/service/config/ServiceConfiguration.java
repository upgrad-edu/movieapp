package upgrad.movieapp.service.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("upgrad.movieapp.service")
@EntityScan({"upgrad.movieapp.service.user.entity", "upgrad.movieapp.service.movie.entity"})
public class ServiceConfiguration {
}
