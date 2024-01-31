package org.example.ui;

import org.example.ClientState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentInterface extends ConsoleInterface {
    @Override
    public void handleResponse(String response, ClientState clientState) {
        try (BufferedReader responseReader = new BufferedReader(new StringReader(response))) {
            String line = responseReader.readLine();
            if("logout-success".equals(line)) {
                clientState.setCurrent(clientState.getLogin());
                setUserId(0);
            }
            else if ("view-grades-success".equals(line) || "view-stats-success".equals(line))
                displayData(responseReader.lines().collect(Collectors.joining("\n")));
        }
        catch (IOException e) {
            displayError(e.getMessage());
        }
    }

    @Override
    protected String getTitle() {
        return "Student Portal";
    }

    @Override
    protected List<String> defineOptions() {
        return Stream.of("Logout", "View Courses Stats", "View Course Grades")
                .collect(Collectors.toList());
    }

    @Override
    protected String processOption(int option) {
        if(option == 0) return "logout\n";
        else if (option == 1) return "view-stats\n" + userId+"\n";
        else if (option == 2) return "view-grades\n" + takeId("Course Id")+"\n";
        else throw new IllegalArgumentException("Can't process invalid option");
    }
}
