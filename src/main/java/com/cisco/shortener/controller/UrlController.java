package com.cisco.shortener.controller;

import com.cisco.shortener.exception.UrlNotFoundException;
import com.cisco.shortener.service.UrlService;
import com.cisco.shortener.model.UrlRecord;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController // Combines @Controller and @ResponseBody for REST APIs
@RequestMapping("/api")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public String shorten(@RequestBody String longUrl) {
        return urlService.shortenUrl(longUrl);
    }

    @GetMapping("/{code}")
    public RedirectView redirect(@PathVariable String code) throws UrlNotFoundException {
        UrlRecord record = urlService.getRecord(code);
        if (record == null) {
            return new RedirectView("/error"); // Redirect to error if not found
        }
        return new RedirectView(record.getLongUrl()); // Standard HTTP 302 redirect
    }

    @GetMapping("/stats/{code}")
    public int getStats(@PathVariable String code) throws UrlNotFoundException{
        UrlRecord record = urlService.getStats(code);
        return (record != null) ? record.getClickCount() : 0;
    }
}