package com.cisco.shortener.exception;

public class UrlNotFoundException extends Exception{
    public UrlNotFoundException(String shortCode) {
        super("Error: The shortcode '" + shortCode + "' does not exist.");
    }
}
