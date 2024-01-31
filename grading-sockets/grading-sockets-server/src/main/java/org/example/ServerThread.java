package org.example;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {
    private final Socket client;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
             BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()))
        ) {
            ServerState serverState = new ServerState();
            StringBuilder request = new StringBuilder();
            while (client.isConnected()) {
                String line;
                while(!(line = fromClient.readLine()).equals("end-request"))
                    request.append(line).append("\n");
                if(request.toString().equals("exit\n")) break;
                String response = serverState.generateResponse(request.toString());
                toClient.write(response+"end-response\n");
                toClient.flush();
                request.delete(0, request.length());
            }
            client.close();
            System.out.println("- Closed: " +
                    client.getInetAddress() + ":" + client.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
