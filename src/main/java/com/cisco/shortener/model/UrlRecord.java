package com.cisco.shortener.model;

import java.util.concurrent.atomic.AtomicInteger;

public class UrlRecord {
    private final String longUrl;
    private final AtomicInteger clickCount = new AtomicInteger(0);
    public UrlRecord(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }
    public void incrementClickCount() {
        clickCount.incrementAndGet();
    }
    public int getClickCount() {
        return clickCount.get();
    }
}
