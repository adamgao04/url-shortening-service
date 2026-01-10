package com.cisco.shortener;

import com.cisco.shortener.exception.UrlNotFoundException;
import com.cisco.shortener.model.UrlRecord;
import com.cisco.shortener.service.InMemoryUrlService;
import com.cisco.shortener.service.UrlService;
import com.cisco.shortener.util.Base62Generator;

public class Main {
    private static final UrlService service = new InMemoryUrlService(new Base62Generator());

    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
        }
        String command = args[0].toLowerCase();

        switch (command) {
            case "shorten":
                handleShorten(args);
                break;
            case "get":
                handleGet(args);
                break;
            case "list":
                handleList();
                break;
            default:
                System.out.println("Unknown command: " + command);
                printUsage();
        }
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

    private static void printUsage() {
        System.out.println("Usage: java UrlShortener <shorten|get|list> [argument]");
    }
}
