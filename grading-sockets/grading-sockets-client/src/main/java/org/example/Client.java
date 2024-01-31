package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static final int PORT = 8000;
    private static final String serverHost = "localhost";

    public static void main(String[] args) {
        try (Socket server = new Socket(serverHost, PORT);
             BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
             BufferedReader fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()))
        ) {
            ClientState clientState = new ClientState();
            StringBuilder response = new StringBuilder();
            while (server.isConnected()) {
                String request = clientState.generateRequest();
                toServer.write(request+"end-request\n");
                toServer.flush();
                if(request.equals("exit\n")) break;
                String line;
                while (!(line = fromServer.readLine()).equals("end-response"))
                    response.append(line).append("\n");
                clientState.updateState(response.toString());
                response.delete(0, response.length());
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown");
        } catch (IOException e) {
            System.out.println("IO");
            throw new RuntimeException(e);
        }
    }
}
