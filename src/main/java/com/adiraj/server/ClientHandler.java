package com.adiraj.server;
import com.adiraj.utils.HttpUtils;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;
            System.out.println("Request: " + requestLine);
            String responseBody = HttpUtils.routeRequest(requestLine);
            String response = HttpUtils.formatHttpResponse(responseBody);
            out.write(response);
            out.flush();
        } catch (IOException e) {
            logger.log(Level.INFO,"Client handling error", e);
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }
}
