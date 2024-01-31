package org.example;

import org.example.portals.*;

public class ServerState {
    private Portal current;
    private final Portal student, instructor, admin, login;

    public ServerState() {
        current = login = new LoginPortal();
        student = new StudentPortal();
        admin = new AdminPortal();
        instructor = new InstructorPortal();
    }

    public String generateResponse(String request) {
        return current.handleRequest(request, this);
    }

    public Portal getCurrent() {
        return current;
    }

    public void setCurrent(Portal current) {
        this.current = current;
    }

    public Portal getStudent() {
        return student;
    }

    public Portal getInstructor() {
        return instructor;
    }

    public Portal getAdmin() {
        return admin;
    }

    public Portal getLogin() {
        return login;
    }
}
