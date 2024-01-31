package org.example.ui;

import org.example.ClientState;

import java.util.List;
import java.util.Scanner;

public abstract class ConsoleInterface {
    protected final Scanner scanner = new Scanner(System.in);
    protected final List<String> options = defineOptions();
    private final String title = getTitle();
    protected static int userId;

    public abstract void handleResponse(String response, ClientState clientState);

    public String generateRequest() {
        displaySectionTitle(title);
        displayOptionsList();
        int option = promptOption();
        return processOption(option);
    }

    protected abstract String getTitle();

    protected abstract List<String> defineOptions();

    protected abstract String processOption(int option);

    protected final void displayData(String data) {
        String dataSplit = "*********************************************************************";
        System.out.printf("%s\n%s\n%s\n", dataSplit, data, dataSplit);
    }

    protected final void displaySectionTitle(String title) {
        String halfSplit = "+++++";
        System.out.printf("%s %s %s\n", halfSplit, title, halfSplit);
    }

    protected final void displayInputPrompt(String item){
        System.out.printf("-> %s: ", item);
    }

    protected final void displayError(String error) {
        System.out.printf("!! Error: %s\n", error);
    }

    protected final int takeId(String label) {
        displayInputPrompt(label);
        while (true){
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                displayError("Invalid number format, try again");
            }
        }
    }

    protected static void setUserId(int userId) {
        ConsoleInterface.userId = userId;
    }
    private void displayOptionsList() {
        int i = 0;
        for (String option: options)
            System.out.println(i++ + ". " + option);
    }

    private int promptOption() {
        while (true){
            try {
                displayInputPrompt("Choose option number");
                int optionId = Integer.parseInt(scanner.nextLine().trim());
                if (optionId < 0 || optionId > options.size()) {
                    displayError("Invalid option number, try again");
                    continue;
                }
                return optionId;
            } catch (NumberFormatException e) {
                displayError("Invalid number format, try again");
            }
        }
    }
}
