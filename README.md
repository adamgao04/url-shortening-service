# URL Shortening Service Overview
A URL shortening service built with Java 21 and Spring Boot. This application supports both an interactive Command Line Interface (CLI) and a RESTful Web API simultaneously.
We use a Gradle wrapper to ensure consistent build environment.

# Implemented extensions:
- Extension 1: Click Statistics
- Extension 3: Web API

# Compilation and Execution
## Compilation:
```bash
./gradlew build
```
## Run The Application:
```bash
./gradlew run --console=plain
```

# Command Examples and Output

**shorten:**	`shorten <url>`	Shortened URL: 2Bi 

**get:**	`get <code>`	Original URL: https://www.cisco.com 

**stats:**	`stats <code>`	Original URL: ... Click count: 5 

**list:**	`list`	2Bi -> https://www.cisco.com 

**exit:**	`exit`	Exiting application... 

# REST API usage
The service is available at http://localhost:8080/api. You can interact with it using curl or any API client.

- Shorten a URL
```bash
curl -X POST -H "Content-Type: text/plain" -d "https://www.cisco.com" http://localhost:8080/api/shorten
```
- Resolve a URL (redirect)
To use a short code, simply perform a GET request. In a browser, this will automatically redirect you. In a terminal, curl can show the redirect header.
```bash
curl -I http://localhost:8080/api/L9y2z
```
- Get Statistics
To view a click count for a specific shortcode via the API.
```bash
curl http://localhost:8080/api/stats/2Bi
```

# Design Decisions
## Dual-Interface Architecture (CLI + REST)
I used the CommandLineRunner interface to keep the CLI active while the Spring Web Server runs in the background. This allows a user to shorten a URL in the terminal and immediately test the redirect in a web browser.
## Thread-Safe In-Memory Storage
I implemented the storage using ConcurrentHashMap and AtomicInteger for click counts. This prevents data races during high-concurrency scenarios if we were to scale this service to multiple users.
## DTO pattern
I implemented a UrlRecord class to encapsulate the long URL and its associated metadata (such as click statistics) into a single object. This allows the service to store and manage multiple attributes (e.g., URL, click counts, timestamps) as a single unit, making it easier to add future features.
## Strategy Pattern for UrlService
Swappable Storage: Coding to the UrlService interface allows the current in-memory storage to be replaced with a persistent database (e.g., PostgreSQL) without touching the Controller or CLI logic.

Swappable Generation: The shortening logic is decoupled, allowing the Base62 generator to be swapped for another generator without impacting the rest of the application.
## Base62
I implemented a counter-based Base62 generator that transforms a unique number into a short, URL-safe string (0-9, a-z, A-Z). By using an AtomicLong counter, every URL is guaranteed a unique ID. We start the counter at 10,000, so codes are at least 3 letters long, which looks more professional. This approach allows for over 56 billion unique URLs before the short code even reaches 7 characters in length.
## Error Handling
Created a custom UrlNotFoundException coupled with a @ControllerAdvice global handler. This maps service-level errors to consistent terminal and web messages.
## Layered Architecture
The code is organized into distinct packages:

Controller: Handles HTTP request mapping and redirects.

Service: Contains business logic (Base62 generation, statistics incrementing).

Model: Defines the UrlRecord data structure.

Exception: Custom error handling for missing URLs.




