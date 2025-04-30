package org.example;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        // Create a thread-safe visitor counter
        AtomicInteger visitorCount = new AtomicInteger(0);

        // Create the HTTP Server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Register the root endpoint handler
        server.createContext("/", new CounterHandler(visitorCount));

        // Start the server
        server.start();
        System.out.println("Server started at http://localhost:8080");
    }
}