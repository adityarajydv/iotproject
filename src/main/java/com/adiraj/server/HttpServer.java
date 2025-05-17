package com.adiraj.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpServer {
    private static final Logger logger = Logger.getLogger(HttpServer.class.getName());
    private final int port;
    private volatile boolean isRunning;
    private final ExecutorService threadPool;
    public HttpServer(int port) {
        this.port = port;
        this.threadPool = Executors.newCachedThreadPool();
    }
    public void start() {
        isRunning = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("HTTP Server started at http://localhost:" + port);
            Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                } catch (IOException e) {
                    if (isRunning) {
                        logger.log(Level.WARNING, "Error accepting client connection", e);
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start server on port " + port, e);
        }
    }
    private void handleClient(Socket clientSocket) {
        threadPool.submit(new ClientHandler(clientSocket));
    }
    public void stop() {
        isRunning = false;
        threadPool.shutdown();
        logger.info("HTTP Server is shutting down.");
    }
}
