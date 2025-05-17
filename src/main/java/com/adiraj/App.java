package com.adiraj;
import com.adiraj.server.HttpServer;

public class App {
    public static void main(String[] args) {
       new HttpServer(3001).start();
    }
}
