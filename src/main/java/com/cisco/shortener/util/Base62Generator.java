package com.cisco.shortener.util;

import java.util.concurrent.atomic.AtomicLong;

public class Base62Generator implements CodeGenerator {
    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = CHARACTERS.length();

    // The counter lives here now!
    private final AtomicLong idCounter = new AtomicLong(10000);

    @Override
    public String generate(String longUrl) {
        long id = idCounter.getAndIncrement();

        if (id == 0) return String.valueOf(CHARACTERS.charAt(0));

        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            int remainder = (int) (id % BASE);
            sb.append(CHARACTERS.charAt(remainder));
            id = id / BASE;
        }

        return sb.reverse().toString();
    }
}