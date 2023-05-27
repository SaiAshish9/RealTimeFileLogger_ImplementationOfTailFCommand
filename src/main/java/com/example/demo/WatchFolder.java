package com.example.demo;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WatchFolder {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private Scanner scanner;
    private String prevContent;
    private static final String path = "/Users/saiashish/Desktop/demo/src/main/resources/";
    private static final Path source = Paths.get(path + "log.txt");
    private static final Path directory = Path.of(path);

    public WatchFolder(SimpMessagingTemplate simpMessagingTemplate) throws IOException {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void watchFolder() throws IOException {

        scanner = new Scanner(source, StandardCharsets.UTF_8);
        scanner = scanner.useDelimiter("\\A");
        prevContent = scanner.next();
        scanner.close();

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
            while (true) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        scanner = new Scanner(source, StandardCharsets.UTF_8.name());
                        String content = scanner.useDelimiter("\\A").next();
                        scanner.close();
                        if (!content.equals(prevContent)) {
                            String ans = content.replace(prevContent, "");
                            simpMessagingTemplate.convertAndSend("/topic/file",
                                    new Message(ans));
                            prevContent = content;
                        }
                    }
                }

                boolean valid = watchKey.reset();
                if (!valid) {
                    break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
