package org.example.ui;

import org.example.ClientState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminInterface extends ConsoleInterface {
    @Override
    public void handleResponse(String response, ClientState clientState) {
        try (BufferedReader responseReader = new BufferedReader(new StringReader(response))) {
            String line = responseReader.readLine();
            if("logout-success".equals(line)) {
                clientState.setCurrent(clientState.getLogin());
                setUserId(0);
            }
            else if ("view-users-success".equals(line))
                displayData(responseReader.lines().collect(Collectors.joining("\n")));
            else if ("add-user-success".equals(line))
                displayData("User added successfully");
            else if ("delete-user-success".equals(line))
                displayData("User deleted successfully");
        }
        catch (IOException e) {
            displayError(e.getMessage());
        }
    }

    @Override
    protected String getTitle() {
        return "Admin Portal";
    }

    @Override
    protected List<String> defineOptions() {
        return Stream.of("Logout", "View Users By Role", "Add User", "Delete User")
                .collect(Collectors.toList());
    }

    @Override
    protected String processOption(int option) {
        if(option == 0) return "logout\n";
        else if (option == 1) return "view-users\n" + takeRole()+"\n";
        else if (option == 2) return "add-user\n" + takeUserInfo()+"\n";
        else if (option == 3) return "delete-user\n" + takeId("Student Id")+"\n";
        else throw new IllegalArgumentException("Can't process invalid option");
    }

    private String takeUserInfo() {
        displayInputPrompt("Email");
        String email = scanner.nextLine();
        displayInputPrompt("Name");
        String name= scanner.nextLine();
        displayInputPrompt("Role");
        String role = scanner.nextLine();
        return email+"\n" + name+"\n" + role;
    }

    private String takeRole() {
        displayInputPrompt("Role");
        return scanner.nextLine();
    }
}
