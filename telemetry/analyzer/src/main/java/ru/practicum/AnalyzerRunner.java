package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.practicum.service.HubEventProcessor;
import ru.practicum.service.SnapshotProcessor;

@Component
@RequiredArgsConstructor
public class AnalyzerRunner implements CommandLineRunner {
    final HubEventProcessor hubEventProcessor;
    final SnapshotProcessor snapshotProcessor;


    @Override
    public void run(String... args) throws Exception {

        Thread hubEventProcessorThread = new Thread(hubEventProcessor);
        hubEventProcessorThread.setName("hub-event-processor");
        hubEventProcessorThread.start();

        snapshotProcessor.start();
    }
}
