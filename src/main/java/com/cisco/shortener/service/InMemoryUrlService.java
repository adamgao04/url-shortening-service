package com.cisco.shortener.service;

import com.cisco.shortener.exception.UrlNotFoundException;
import com.cisco.shortener.model.UrlRecord;
import com.cisco.shortener.util.CodeGenerator;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUrlService implements UrlService{
    private final Map<String, UrlRecord> storage = new HashMap<>();
    private final CodeGenerator codeGenerator;

    public InMemoryUrlService(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    @Override
    public String shortenUrl(String longUrl) {
        String code = codeGenerator.generate(longUrl);

        storage.put(code, new UrlRecord(longUrl));
        return code;
    }

    @Override
    public UrlRecord getRecord(String shortUrl) throws UrlNotFoundException {
        UrlRecord record = storage.get(shortUrl);
        if (record == null) {
            throw new UrlNotFoundException(shortUrl);
        }
        record.incrementClickCount();
        return record;
    }

    @Override
    public Map<String, UrlRecord> getAllRecords() {
        return new HashMap<>(storage);
    }

    @Override
    public UrlRecord getStats(String shortUrl) throws UrlNotFoundException {
        UrlRecord record = storage.get(shortUrl);
        if (record == null) {
            throw new UrlNotFoundException(shortUrl);
        }
        return record;
    }
}
