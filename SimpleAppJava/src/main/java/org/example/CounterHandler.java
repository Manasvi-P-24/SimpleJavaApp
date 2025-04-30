package org.example;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterHandler implements HttpHandler {
    private final AtomicInteger visitorCount;

    public CounterHandler(AtomicInteger visitorCount) {
        this.visitorCount = visitorCount;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Ignore favicon requests
        if (exchange.getRequestURI().getPath().equals("/favicon.ico")) {
            exchange.sendResponseHeaders(404, -1); // Return 404 Not Found for favicon
            return;
        }

        // Increment the visitor counter
        int currentCount = visitorCount.incrementAndGet();

        // Path to the HTML file
        String pathToHtmlFile = "src/main/resources/index.html";

        // Read the HTML file
        String html = new String(Files.readAllBytes(Paths.get(pathToHtmlFile)));

        // Replace the placeholder {{counter}} with the current visitor count
        html = html.replace("{{counter}}", String.valueOf(currentCount));

        // Write the HTTP response (HTML)
        exchange.sendResponseHeaders(200, html.getBytes().length); // HTTP 200 OK
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(html.getBytes());
        outputStream.close();
    }
}