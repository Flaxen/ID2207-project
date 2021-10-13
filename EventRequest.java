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
    private boolean[] preferencesBool;
    private String feedback;
    private String status;
    private String discountPercentage;
    private String[] preferencesText = {"decorations", "parties", "photos/filming", "breakfast,lunch,dinner", "soft/hot drinks"};

    EventRequest(int id, String clientName, String type, String description, String startDate,
    String endDate, String expectedNumber, String expectedBudget, boolean[] preferencesBool) {
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
        this.feedback = "";
        this.discountPercentage = "0";
    }

    int getId() {
      return id;
    }

    String getStatus() {
      return status;
    }

    String getDiscountPercentage() {
      return discountPercentage;
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

    void setStatus(String status) {
        this.status = status;
      }

    void setDiscountPercentage(String discount) {
      this.discountPercentage = discount;
    }

    void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    static EventRequest creationUI(Staff activeUser, int id) {
      String[] authorizedStaff = {"CustomerService"};
      if(!allowedUser(activeUser, authorizedStaff)) {
        return null;
      }

      String clientName;
      String type;
      String description;
      String startDate;
      String endDate;
      String expectedNumber;
      String expectedBudget;
      boolean[] preferencesBool = {false, false, false, false, false};

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

      return new EventRequest(id, clientName, type, description, startDate, endDate, expectedNumber, expectedBudget, preferencesBool);
    }

    static EventRequest getRequest(ArrayList<EventRequest> eventRequests) {
      Scanner in = new Scanner(System.in);
      System.out.print("Enter request Id: ");
      int id = in.nextInt();

      EventRequest er = null;
      EventRequest temp = null;
      for(int i = 0; i < eventRequests.size(); i++) {
          temp = eventRequests.get(i);
          if(temp.getId() == id) {
              er = temp;
              return er;
          }
      }
      System.out.println("No such event request");
      return null;
    }

    static boolean allowedUser(Staff activeUser, String[] authorizedStaff) {
      if(activeUser == null) {
        System.out.println("Permission denied");
        return false;
      }
      for(int i = 0; i < authorizedStaff.length; i++) {
        if(activeUser.getRole().equals(authorizedStaff[i])) {
          return true;
        }
      }
      System.out.println("Permission denied");
      return false;
    }

    static void list(Staff activeUser, ArrayList<EventRequest> eventRequests) {

      String[] authorizedStaff = {"SeniorCustomerService", "FinancialManager", "AdministrationManager"};
      if(!allowedUser(activeUser, authorizedStaff)) {
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

    static void view(Staff activeUser, ArrayList<EventRequest> eventRequests) {

      String[] authorizedStaff = {"SeniorCustomerService", "FinancialManager", "AdministrationManager"};
      if(!allowedUser(activeUser, authorizedStaff)) {
        return;
      }

      Scanner in = new Scanner(System.in);
      EventRequest er = getRequest(eventRequests);
      if(er == null) {
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
      System.out.println("Discount (%): " + er.getDiscountPercentage());
      System.out.println("Financial feedback: " + er.getFeedback());
      System.out.println("\nPrefereces: ");
      System.out.println(er.getPreferences());
      System.out.println();
    }

    static ArrayList<EventRequest> approve(Staff activeUser, ArrayList<EventRequest> eventRequests) {
      String[] authorizedStaff = {"SeniorCustomerService", "AdministrationManager"};
      if(!allowedUser(activeUser, authorizedStaff)) {
        return null;
      }
        return nextChainInCommand(activeUser, eventRequests);
    }

    static ArrayList<EventRequest> updateDiscount(Staff activeUser, ArrayList<EventRequest> eventRequests) {
      String[] authorizedStaff = {"FinancialManager"};
      if(!allowedUser(activeUser, authorizedStaff)) {
        return null;
      }

      EventRequest er = getRequest(eventRequests);
      if(er == null) {
        return null;
      }

      if(!er.getStatus().equals("Redirected to Financial Manager")) {
        System.out.println("Permission denied, wrong status");
        return null;
      }

      Scanner in = new Scanner(System.in);
      System.out.print("Enter new discount (%): ");
      String discount = in.nextLine();

      er.setDiscountPercentage(discount);
      eventRequests.remove(er.getId()-1);
      eventRequests.add(er.getId()-1, er);
      System.out.println("Discount updated");
      return eventRequests;
    }

    static ArrayList<EventRequest> reject(Staff activeUser, ArrayList<EventRequest> eventRequests) {
      String[] authorizedStaff = {"SeniorCustomerService", "AdministrationManager"};
      if(!allowedUser(activeUser, authorizedStaff)) {
        return null;
      }

      EventRequest er = getRequest(eventRequests);
      if(er == null) {
        return null;
      }

      er.setStatus("Rejected");
      eventRequests.remove(er.getId()-1);
      eventRequests.add(er.getId()-1, er);

      System.out.println("Rejected event request");
      return eventRequests;
    }

    static ArrayList<EventRequest> redirect(Staff activeUser, ArrayList<EventRequest> eventRequests) {
      String[] authorizedStaff = {"FinancialManager"};
      if(!allowedUser(activeUser, authorizedStaff)) {
        return null;
      }
        return nextChainInCommand(activeUser, eventRequests);
    }

    static ArrayList<EventRequest> nextChainInCommand(Staff activeUser, ArrayList<EventRequest> eventRequests) {

      EventRequest er = getRequest(eventRequests);
      if(er == null) {
        return null;
      }

      if(activeUser.getRole().equals("SeniorCustomerService") && er.getStatus().equals("Created")) {
          er.setStatus("Redirected to Financial Manager");
          System.out.println("Event request forwarded to Financial Manager");
      } else if(activeUser.getRole().equals("FinancialManager") && er.getStatus().equals("Redirected to Financial Manager")) {

        if(er.feedback == "") {
            System.out.println("No feedback added");
            return null;
        }
        er.setStatus("Redirected to Administration Manager");
        System.out.println("Event request forwarded to Administration Manager");

      } else if(activeUser.getRole().equals("AdministrationManager") && er.getStatus().equals("Redirected to Administration Manager")){
        er.setStatus("Approved");
        System.out.println("Event request approved");
      } else {
        System.out.println("Permission denied, wrong status");
        return null;
      }
        eventRequests.remove(er.getId()-1);
        eventRequests.add(er.getId()-1, er);

        return eventRequests;
    }

    static void addFeedback(Staff activeUser, ArrayList<EventRequest> eventRequests) {
        Scanner in = new Scanner(System.in);

        EventRequest er = getRequest(eventRequests);
        if(er == null) {
          return;
        }

        if(!er.getStatus().equals("Redirected to Financial Manager")) {
          System.out.println("Permission denied, wrong status");
          return;
        }

        System.out.print("Enter feedback: ");
        String feedback = in.nextLine();
        er.setFeedback(feedback);
        eventRequests.remove(er.getId()-1);
        eventRequests.add(er.getId()-1, er);
        System.out.println("Feedback added");

    }

    public static void main(String[] args) {
      // Creation testing
      // Completed test gets Permission denied print. is ok.

      boolean[] arr = {true, false, false, true, true};
      EventRequest er = new EventRequest(1, "client", "type", "description", "startDate", "endDate", "expectedNumber", "expectedBudget", arr);

      er.setStatus("status2");
      er.setDiscountPercentage("20");
      er.setFeedback("feedback2");

      if(er.getClientName().equals("client") && er.getType().equals("type") && er.getDescription().equals("description") && er.getStartDate().equals("startDate") &&
            er.getEndDate().equals("endDate") && er.getExpectedNumber().equals("expectedNumber") && er.getExpectedBudget().equals("expectedBudget") &&
                er.getPreferences().equals("decorations, breakfast,lunch,dinner, soft/hot drinks, ") && er.getId() == 1 && er.getStatus().equals("status2") &&
                    er.getFeedback() == "feedback2" && er.discountPercentage == "20") {

        System.out.println("Creation test completed");
      } else {
        System.out.println("Creation test failed");
      }

      // Method testing
      // Functions with UI input are tested using Acceptence tests

      Staff[] staff = {new Staff("Sarah", "cs1", "123", "CustomerService", "CustomerService"),
                       new Staff("Janet", "scs1", "123", "SeniorCustomerService", "CustomerService"),
                       new Staff("Alice", "fm1", "123", "FinancialManager", null)};

      ArrayList<EventRequest> eventRequests = new ArrayList<EventRequest>();

      boolean[] b = {false, false, false, false, true};
      eventRequests.add(new EventRequest(1, "Joe", "fika", "bullar och kaffe", "5", "6", "20", "1300", b));
      eventRequests.add(new EventRequest(2, "Emma", "fest", "partykväll", "2", "3", "100", "5000", b));
      eventRequests.add(new EventRequest(3, "Robert", "grill", "korv och burgare", "10", "12", "10", "700", b));

      String[] s1 = {"kakor", "elefanter", "somethign", "CustomerService"};
      boolean shouldBeTrue1 = allowedUser(staff[0], s1);
      boolean shouldBeFalse1 = allowedUser(staff[1], s1);

      if(shouldBeTrue1 == true && shouldBeFalse1 == false) {
        System.out.println("Method test completed");
      } else {
        System.out.println("Method test failed");
      }
    }
}
















//
