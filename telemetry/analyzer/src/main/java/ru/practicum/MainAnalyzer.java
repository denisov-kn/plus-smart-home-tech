package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.practicum.service.HubEventProcessor;
import ru.practicum.service.SnapshotProcessor;

@SpringBootApplication
public class MainAnalyzer {
    public static void main(String[] args) {
                SpringApplication.run(MainAnalyzer.class, args);
    }
}
