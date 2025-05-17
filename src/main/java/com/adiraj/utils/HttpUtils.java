package com.adiraj.utils;
public class HttpUtils {

    public static String formatHttpResponse(String body) {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;
    }

    public static String routeRequest(String requestLine) {
        if (requestLine.startsWith("GET /hello")) {
            return "Hello world!";
        } else if (requestLine.startsWith("GET /")) {
            return "Welcome to your custom HTTP server!";
        } else {
            return "Unknown route!";
        }
    }
}
