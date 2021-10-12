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
    private String feedback;
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
        this.feedback = null;
    }

    int getId() {
      return id;
    }

    String getStatus() {
      return status;
    }

    void setStatus(String status) {
        this.status = status;
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

    String getFeedback() {
        return feedback;
    }

    void setFeedback(String feedback) {
        this.feedback = feedback;
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
      Boolean[] preferencesBool = {false, false, false, false, false};

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

      if(tempIn.toLowerCase().equals("yes") || tempIn.toLowerCase().equals("y")) {
        preferencesBool[0] = true;
      }

      System.out.print("Parties? Y/N: ");
      tempIn = in.nextLine();

      if(tempIn.toLowerCase().equals("yes") || tempIn.toLowerCase().equals("y")) {
        preferencesBool[1] = true;
      }

      System.out.print("Photos/Filming? Y/N: ");
      tempIn = in.nextLine();

      if(tempIn.toLowerCase().equals("yes") || tempIn.toLowerCase().equals("y")) {
        preferencesBool[2] = true;
      }

      System.out.print("Breakfast, Lunch, Dinner? Y/N: ");
      tempIn = in.nextLine();

      if(tempIn.toLowerCase().equals("yes") || tempIn.toLowerCase().equals("y")) {
        preferencesBool[3] = true;
      }

      System.out.print("Soft/Hot drinks? Y/N: ");
      tempIn = in.nextLine();

      if(tempIn.toLowerCase().equals("yes") || tempIn.toLowerCase().equals("y")) {
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

    static void viewEventRequest(Staff activeUser, ArrayList<EventRequest> eventRequests) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter request Id: ");
        int id = in.nextInt();

        if(!activeUser.getRole().equals("SeniorCustomerService") && !activeUser.getRole().equals("FinancialManager") &&
          !activeUser.getRole().equals("AdministrationManager")) {
        System.out.println("Permission denied");
        return;
      }

      EventRequest er = null;
      for(int i = 0; i < eventRequests.size(); i++) {
        er = eventRequests.get(i);
        if(er.getId() == id) {
            break;
        }
      }

      if(er == null) {
        System.out.println("No such event request");
        return;
      }

      System.out.println("\nId: " + er.getId());
      System.out.println("status: " + er.getStatus());
      System.out.println("client name: " + er.getClientName());
      System.out.println("type: " + er.getType());
      System.out.println("description: " + er.getDescription());
      System.out.println("start date: " + er.getStartDate());
      System.out.println("end date: " + er.getEndDate());
      System.out.println("expected number of attendees: " + er.getExpectedNumber());
      System.out.println("expected budget: " + er.getExpectedBudget());
      System.out.println("\nPrefereces: ");
      System.out.println(er.getPreferences());
      System.out.println();
    }

    static ArrayList<EventRequest> approve(Staff activeUser, ArrayList<EventRequest> eventRequests) {
        if(!activeUser.getRole().equals("SeniorCustomerService") &&
          !activeUser.getRole().equals("AdministrationManager")) {
            System.out.println("Permission denied");
            return null;
        }
        return nextChainInCommand(activeUser, eventRequests);
    }

    static ArrayList<EventRequest> redirect(Staff activeUser, ArrayList<EventRequest> eventRequests) {
        if(!activeUser.getRole().equals("FinancialManager")) {
            System.out.println("Permission denied");
            return null;
        }
        return nextChainInCommand(activeUser, eventRequests);
    }

    static ArrayList<EventRequest> nextChainInCommand(Staff activeUser, ArrayList<EventRequest> eventRequests) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter request Id: ");
        int id = in.nextInt();

        EventRequest er = null;
        for(int i = 0; i < eventRequests.size(); i++) {
            er = eventRequests.get(i);
            if(er.getId() == id) {
                break;
            }
        }

        if(er == null) {
            System.out.println("No such event request");
            return null;
        }

        if(activeUser.getRole().equals("SeniorCustomerService")) {
            er.setStatus("Redirected to Financial Manager");
            System.out.println("Event request forwarded to Financial Manager");
        } else if(activeUser.getRole().equals("FinancialManager")) {
            if(er.feedback == null) {
                System.out.println("No feedback added");
                return null;
            }
            er.setStatus("Redirected to Administration Manager");
            System.out.println("Event request forwarded to Administration Manager");
        }
        else {
            er.setStatus("Approved");
            System.out.println("Event request approved");
        }
        eventRequests.remove(id-1);
        eventRequests.add(id-1, er);

        return eventRequests;
    }

    static void addFeedback(Staff activeUser, ArrayList<EventRequest> eventRequests) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter request Id: ");
        int id = in.nextInt();

        EventRequest er = null;
        for(int i = 0; i < eventRequests.size(); i++) {
            er = eventRequests.get(i);
            if(er.getId() == id) {
                break;
            }
        }

        if(er == null) {
            System.out.println("No such event request");
            return;
        }

        System.out.print("Enter feedback: ");
        String feedback = in.nextLine();
        er.setFeedback(feedback);
        eventRequests.remove(id-1);
        eventRequests.add(id-1, er);
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
