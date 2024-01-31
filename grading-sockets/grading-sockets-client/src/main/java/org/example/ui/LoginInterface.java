package org.example.ui;

import org.example.ClientState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoginInterface extends ConsoleInterface {

    @Override
    public String getTitle() {
        return "Login Portal";
    }

    @Override
    public List<String> defineOptions() {
        return Stream.of("Exit", "Login")
                .collect(Collectors.toList());
    }

    @Override
    public String processOption(int option) {
        if(option == 0) return "exit\n";
        else if (option == 1) return takeLoginInfo()+"\n";
        else throw new IllegalArgumentException("Can't process invalid option");
    }

    private String takeLoginInfo() {
        displayInputPrompt("Email");
        String email = scanner.nextLine();
        displayInputPrompt("Password");
        String password = scanner.nextLine();
        return email+"\n" + password;
    }

    @Override
    public void handleResponse(String response, ClientState clientState) {
        try (BufferedReader responseReader = new BufferedReader(new StringReader(response))) {
            String line = responseReader.readLine();
            if ("login-fail".equals(line))
                displayError("Invalid email or password");
            else if ("login-success".equals(line)) {
                line = responseReader.readLine();
                if ("student".equals(line))
                    clientState.setCurrent(clientState.getStudent());
                else if ("instructor".equals(line))
                    clientState.setCurrent(clientState.getInstructor());
                else if ("admin".equals(line))
                    clientState.setCurrent(clientState.getAdmin());
                line = responseReader.readLine();
                int userId = Integer.parseInt(line);
                setUserId(userId);
            }
        }
        catch (IOException e) {
            displayError(e.getMessage());
        }
    }
}
