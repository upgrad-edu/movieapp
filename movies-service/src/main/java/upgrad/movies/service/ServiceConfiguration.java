package upgrad.movies.service;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("upgrad.movies.service")
@EntityScan({"upgrad.movies.service.user.entity"})
public class ServiceConfiguration {
}
