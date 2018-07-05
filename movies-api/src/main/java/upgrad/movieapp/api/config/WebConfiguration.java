package upgrad.movieapp.api.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("upgrad.movieapp.api.controller")
@ServletComponentScan("upgrad.movieapp.api.servlet")
public class WebConfiguration {
}
