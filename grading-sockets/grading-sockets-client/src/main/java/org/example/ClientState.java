package org.example;

import org.example.ui.*;

public class ClientState {
    private ConsoleInterface current;
    private final ConsoleInterface student, instructor, admin, login;

    public ClientState() {
        current = login = new LoginInterface();
        student = new StudentInterface();
        admin = new AdminInterface();
        instructor = new InstructorInterface();
    }

    public String generateRequest() {
        return current.generateRequest();
    }
    public void updateState(String response) {
        current.handleResponse(response, this);
    }

    public void setCurrent(ConsoleInterface current) {
        this.current = current;
    }

    public ConsoleInterface getCurrent() {
        return current;
    }

    public ConsoleInterface getStudent() {
        return student;
    }

    public ConsoleInterface getInstructor() {
        return instructor;
    }

    public ConsoleInterface getAdmin() {
        return admin;
    }

    public ConsoleInterface getLogin() {
        return login;
    }
}
