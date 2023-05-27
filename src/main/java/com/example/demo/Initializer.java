package com.example.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Service
public class Initializer implements InitializingBean {


    private Scanner scanner;
    private String prevContent;
    private static final String path = "/Users/saiashish/Desktop/demo/src/main/resources/";
    private static final Path source = Paths.get(path + "log.txt");
    private static final Path directory = Path.of(path);

    private static List<String> contents = new ArrayList<>();

    private final SimpMessagingTemplate simpMessagingTemplate;

    public Initializer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        new WatchFolder(simpMessagingTemplate).initialize();
    }
}