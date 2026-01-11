package com.cisco.shortener;

import com.cisco.shortener.exception.UrlNotFoundException;
import com.cisco.shortener.model.UrlRecord;
import com.cisco.shortener.service.UrlService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class UrlShortenerApplication implements CommandLineRunner {

    private final UrlService service;

    public UrlShortenerApplication(UrlService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Cisco URL Shortener Service Active ---");
        System.out.println("Type 'exit' to quit. Available: shorten, get, list, stats");

        while (true) {
            System.out.print("\n> ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting application...");
                // Shut down the web server when CLI exits
                System.exit(0);
                break;
            }

            String[] parts = input.split("\\s+");
            if (parts.length == 0 || parts[0].isEmpty()) continue;

            String command = parts[0].toLowerCase();

            switch (command) {
                case "shorten":
                    handleShorten(parts);
                    break;
                case "get":
                    handleGet(parts);
                    break;
                case "list":
                    handleList();
                    break;
                case "stats":
                    handleStats(parts);
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    printUsage();
            }
        }
        scanner.close();
    }

    private void handleShorten(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Please provide a URL to shorten.");
            return;
        }
        String shortCode = service.shortenUrl(args[1]);
        System.out.println("Shortened URL: " + shortCode);
    }

    private void handleGet(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Please provide a short code.");
            return;
        }
        try {
            UrlRecord record = service.getRecord(args[1]);
            System.out.println("Original URL: " + record.getLongUrl());
        } catch (UrlNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleList() {
        service.getAllRecords().forEach((code, record) ->
                System.out.println(code + " -> " + record.getLongUrl()));
    }

    private void handleStats(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Please provide a short code.");
            return;
        }
        try {
            UrlRecord record = service.getStats(args[1]);
            System.out.println("Original URL: " + record.getLongUrl());
            System.out.println("Click count: " + record.getClickCount());
        } catch (UrlNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printUsage() {
        System.out.println("Usage: java UrlShortener <shorten|get|list|stats> [argument]");
    }
}