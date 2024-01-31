package org.example.portals;

import org.example.ServerState;
import org.example.bean.StatelessContainer;
import org.example.dto.User;
import org.example.service.UserService;
import org.example.service.PasswordService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
public class LoginPortal implements Portal {
    private final UserService userService = StatelessContainer.getUserService();
    private final PasswordService passwordService = StatelessContainer.getPasswordService();

    @Override
    public String handleRequest(String request, ServerState serverState) {
        try(BufferedReader requestReader = new BufferedReader(new StringReader(request))) {
            String email = requestReader.readLine();
            String password = requestReader.readLine();
            User user = userService.getUserByEmail(email);
            if(user != null) {
                boolean isValid = passwordService.validatePassword(password, user.getPasswordHash());
                if(isValid) {
                    String role = user.getRole();
                    if ("student".equals(role))
                        serverState.setCurrent(serverState.getStudent());
                    else if ("instructor".equals(role))
                        serverState.setCurrent(serverState.getInstructor());
                    else if ("admin".equals(role))
                        serverState.setCurrent(serverState.getAdmin());
                    return "login-success\n" + role+"\n" + user.getId()+"\n";
                }
            }
            return "login-fail\n";
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
