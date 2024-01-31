package org.example.portals;

import org.example.ServerState;
import org.example.bean.StatelessContainer;
import org.example.dto.User;
import org.example.service.PasswordService;
import org.example.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class AdminPortal implements Portal {

    private final UserService userService = StatelessContainer.getUserService();
    private final PasswordService passwordService = StatelessContainer.getPasswordService();
    @Override
    public String handleRequest(String request, ServerState serverState) {
        try(BufferedReader requestReader = new BufferedReader(new StringReader(request))) {
            String line = requestReader.readLine();
            switch (line) {
                case "logout":
                    serverState.setCurrent(serverState.getLogin());
                    return "logout-success\n";
                case "view-users":
                    String userRole = requestReader.readLine();
                    return fetchUsersByRole(userRole);
                case "add-user":
                    String email = requestReader.readLine();
                    String name = requestReader.readLine();
                    String role = requestReader.readLine();
                    return createUser(email, name, role);
                case "delete-user":
                    line = requestReader.readLine();
                    int userId = Integer.parseInt(line);
                    return deleteUser(userId);
                default:
                    throw new IllegalArgumentException("Invalid admin action");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String fetchUsersByRole(String role) {
        List<User> usersList = userService.getUsersByRole(role);
        String header = "ID, Email, Name\n";
        StringBuilder response = new StringBuilder(header);
        usersList.forEach(record -> response
                .append(record.getId()).append(", ")
                .append(record.getEmail()).append(", ")
                .append(record.getName()).append("\n")
        );
        return "view-users-success\n" + response;
    }

    private String createUser(String email, String name, String role) {
        String hashedPassword = passwordService.hashPassword("123");
        User user = User.builder().email(email).name(name).passwordHash(hashedPassword).role(role).build();
        int status = userService.addUser(user);
        return status == 1 ? "add-user-success\n" : "\n";
    }

    private String deleteUser(int userId) {
        User user = User.builder().id(userId).build();
        int status = userService.deleteUser(user);
        return status == 1 ? "delete-user-success\n" : "\n";
    }
}
