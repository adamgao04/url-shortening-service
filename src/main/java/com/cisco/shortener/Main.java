package com.cisco.shortener;

import com.cisco.shortener.exception.UrlNotFoundException;
import com.cisco.shortener.model.UrlRecord;
import com.cisco.shortener.service.InMemoryUrlService;
import com.cisco.shortener.service.UrlService;
import com.cisco.shortener.util.Base62Generator;

import java.util.Scanner;

public class Main {
    private static final UrlService service = new InMemoryUrlService(new Base62Generator());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Cisco URL Shortener Service Active ---");
        System.out.println("Type 'exit' to quit. Available: shorten, get, list, stats");

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting application...");
                break;
            }

            // Split input by space to simulate the args[] array
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

    private static void handleShorten(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Please provide a URL to shorten.");
            return;
        }
        String shortCode = service.shortenUrl(args[1]);
        System.out.println("Shortened URL: " + shortCode);
    }

    private static void handleGet(String[] args) {
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

    private static void handleList() {
        service.getAllRecords().forEach((code, record) ->
                System.out.println(code + " -> " + record.getLongUrl()));
    }

    private static void handleStats(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Please provide a short code.");
            return;
        }
        try {
            UrlRecord record = service.getStats(args[1]);
            System.out.println("Original URL: " + record.getLongUrl());
            System.out.println("Click count:" + record.getClickCount());
        } catch (UrlNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java UrlShortener <shorten|get|list> [argument]");
    }
}
