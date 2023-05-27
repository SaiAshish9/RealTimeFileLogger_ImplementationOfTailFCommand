package com.example.demo;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduledPushMessages {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private static List<String> contents = new ArrayList<>();

    public ScheduledPushMessages(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedRate = 500)
    public void sendMessage() throws IOException {
        WatchFolder wf = new WatchFolder(simpMessagingTemplate);
        wf.watchFolder();
    }

}