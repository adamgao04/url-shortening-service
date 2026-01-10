package com.cisco.shortener.model;

public class UrlRecord {
    private final String longUrl;

    public UrlRecord(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }
}
