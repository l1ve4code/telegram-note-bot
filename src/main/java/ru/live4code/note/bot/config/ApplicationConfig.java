package ru.live4code.note.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class ApplicationConfig {

    @Bean
    private static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        var config = new PropertySourcesPlaceholderConfigurer();
        var resources = new Resource[]{
                new ClassPathResource("telegram.properties"),
                new ClassPathResource("local.properties")
        };
        config.setLocations(resources);
        config.setIgnoreUnresolvablePlaceholders(true);
        config.setIgnoreResourceNotFound(true);

        return config;
    }

}
