package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class SampleController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private Scanner scanner;
    private String prevContent;
    private static final String path = "/Users/saiashish/Desktop/demo/src/main/resources/";
    private static final Path source = Paths.get(path + "log.txt");

    public SampleController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @MessageMapping("/get")
    @SendTo("/topic/read")
    public void sendInitialRead() throws IOException {
        scanner = new Scanner(source, StandardCharsets.UTF_8);
        scanner = scanner.useDelimiter("\\A");
        List<String> contents = new ArrayList<>();
        while (scanner.hasNextLine()){
            prevContent = scanner.nextLine();
            contents.add(prevContent);
        }
        scanner.close();
        simpMessagingTemplate.convertAndSend("/topic/read",
                new Message(contents.toString()));
    }

}
