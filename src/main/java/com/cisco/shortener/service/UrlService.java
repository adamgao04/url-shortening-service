package com.cisco.shortener.service;

import com.cisco.shortener.exception.UrlNotFoundException;
import com.cisco.shortener.model.UrlRecord;

import java.util.Map;

public interface UrlService {
    String shortenUrl(String longUrl);
    UrlRecord getRecord(String shortUrl) throws UrlNotFoundException;
    Map<String, UrlRecord> getAllRecords();
}
