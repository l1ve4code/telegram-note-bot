package ru.live4code.note.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NoteBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteBotApplication.class, args);
    }

}
