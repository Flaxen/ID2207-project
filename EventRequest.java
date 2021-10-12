import java.util.Scanner;
import java.util.ArrayList;

class EventRequest {

    private int id;

    private String clientName;
    private String type;
    private String description;
    private String startDate;
    private String endDate;
    private String expectedNumber;
    private String expectedBudget;
    private Boolean[] preferencesBool;

    private String status;
    private String[] preferencesText = {"decorations", "parties", "photos/filming", "breakfast,lunch,dinner", "soft/hot drinks"};

    EventRequest(int id, String clientName, String type, String description, String startDate,
    String endDate, String expectedNumber, String expectedBudget, Boolean[] preferencesBool) {
        this.id = id;
        this.clientName = clientName;
        this.type = type;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedNumber = expectedNumber;
        this.expectedBudget = expectedBudget;
        this.preferencesBool = preferencesBool;
        this.status = "Created";

    }

    int getId() {
      return id;
    }

    String getStatus() {
      return status;
    }

    String getClientName() {
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

    static EventRequest creationUI(Staff activeUser, int id) {
      if(!activeUser.getRole().equals("CustomerService")) {
        System.out.println("Permission denied");
        return null;
      }

      String clientName;
      String type;
      String description;
      String startDate;
      String endDate;
      String expectedNumber;
      String expectedBudget;
      Boolean[] preferencesBool = new Boolean[5];

      String tempIn;

      Scanner in = new Scanner(System.in);

      System.out.print("Client name:");
      clientName = in.nextLine();

      System.out.print("Event type: ");
      type = in.nextLine();

      System.out.print("Description: ");
      description = in.nextLine();

      System.out.print("Start date: ");
      startDate = in.nextLine();

      System.out.print("End date: ");
      endDate = in.nextLine();

      System.out.print("Expected number of attendees: ");
      expectedNumber = in.nextLine();

      System.out.print("Expected budget: ");
      expectedBudget = in.nextLine();

      System.out.println("Preferences: ");
      System.out.print("Decoration? Y/N: ");
      tempIn = in.nextLine();

      if(tempIn.toLowerCase().equals("yes") || tempIn.toLowerCase().equals("Y")) {
        preferencesBool[0] = true;
      }

      System.out.print("Parties? Y/N: ");
      tempIn = in.nextLine();

      if(tempIn.toLowerCase().equals("yes") || tempIn.toLowerCase().equals("Y")) {
        preferencesBool[1] = true;
      }

      System.out.print("Photos/Filming? Y/N: ");
      tempIn = in.nextLine();

      if(tempIn.toLowerCase().equals("yes") || tempIn.toLowerCase().equals("Y")) {
        preferencesBool[2] = true;
      }

      System.out.print("Breakfast, Lunch, Dinner? Y/N: ");
      tempIn = in.nextLine();

      if(tempIn.toLowerCase().equals("yes") || tempIn.toLowerCase().equals("Y")) {
        preferencesBool[3] = true;
      }

      System.out.print("Soft/Hot drinks? Y/N: ");
      tempIn = in.nextLine();

      if(tempIn.toLowerCase().equals("yes") || tempIn.toLowerCase().equals("Y")) {
        preferencesBool[4] = true;
      }
      System.out.println("EventRequest created");

      // in.close();

      return new EventRequest(id, clientName, type, description, startDate, endDate, expectedNumber, expectedBudget, preferencesBool);
    }

    static void listEventRequest(Staff activeUser, ArrayList<EventRequest> eventRequests) {
      if(!activeUser.getRole().equals("SeniorCustomerService") && !activeUser.getRole().equals("FinancialManager") &&
          !activeUser.getRole().equals("AdministrationManager")) {
        System.out.println("Permission denied");
        return;
      }

      System.out.println("\nID:     Status:");

      EventRequest temp;
      for(int i = 0; i < eventRequests.size(); i++) {
        temp = eventRequests.get(i);
        System.out.println(temp.getId() + " " + temp.getStatus());
      }
      System.out.println();
    }

    public static void main(String[] args) {
      Boolean[] arr = {true, false, false, true, true};
      EventRequest er = new EventRequest(1, "client", "type", "description", "startDate", "endDate", "expectedNumber", "expectedBudget", arr);

      if(er.getClientName().equals("client") && er.getType().equals("type") && er.getDescription().equals("description") && er.getStartDate().equals("startDate") &&
            er.getEndDate().equals("endDate") && er.getExpectedNumber().equals("expectedNumber") && er.getExpectedBudget().equals("expectedBudget") &&
                er.getPreferences().equals("decorations, breakfast,lunch,dinner, soft/hot drinks, ") && er.getId() == 1 && er.getStatus().equals("Created")) {

                  System.out.println("Test completed");
                } else {
                  System.out.println("Test failed");
                }
    }
}
















//
