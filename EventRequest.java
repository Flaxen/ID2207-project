import java.util.Scanner;

class EventRequest {

    private String clientName;
    private String type;
    private String description;
    private String startDate;
    private String endDate;
    private String expectedNumber;
    private String expectedBudget;
    private Boolean[] preferencesBool;

    private String[] preferencesText = {"decorations", "parties", "photos/filming", "breakfast,lunch,dinner", "soft/hot drinks"};

    EventRequest(String clientName, String type, String description, String startDate, 
    String endDate, String expectedNumber, String expectedBudget, Boolean[] preferencesBool) {
        this.clientName = clientName;
        this.type = type;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedNumber = expectedNumber;
        this.expectedBudget = expectedBudget;
        this.preferencesBool = preferencesBool;
    }

    String getclientName() {
        return clientName;
    }

    String getType() {
        return type;
    }

    String getDescription() {
        return description;
    }

    String getStartDate() {
        return startDate;
    }

    String getEndDate() {
        return endDate;
    }

    String getExpectedNumber() {
        return expectedNumber;
    }

    String getExpectedBudget() {
        return expectedBudget;
    }

    String getPreferences() {
        String tmp = "";

        for(int i=0; i < preferencesText.length; i++) {
            if(preferencesBool[i]) {
                tmp = tmp + preferencesText[i] + ", ";
            }
        }

        return tmp;
    }
}