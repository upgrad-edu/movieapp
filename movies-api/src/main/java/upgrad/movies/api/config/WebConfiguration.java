package upgrad.movies.api.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("apps.proman.api.controller")
@ServletComponentScan("apps.proman.api.servlet")
public class WebConfiguration {

}
