package org.example.portals;

import org.example.ServerState;

public interface Portal {
    String handleRequest(String request, ServerState serverState);
}
