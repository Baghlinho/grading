package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8000;

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT)) {
//            serverSocket.setReuseAddress(true);
            System.out.println("Grading system server initiated");
            System.out.println("Listening for requests...");
            while(true) {
                Socket nextClient = server.accept();
                System.out.println("- Opened: " +
                        nextClient.getInetAddress() + ":" + nextClient.getPort());
                Thread nextThread = new Thread(new ServerThread(nextClient));
                nextThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
