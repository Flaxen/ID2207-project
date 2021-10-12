import java.util.Scanner;
import java.util.ArrayList;

class FullProgram {

  static private Staff[] staff = {new Staff("Sarah", "cs1", "123", "CustomerService"),
                           new Staff("Janet", "scs1", "123", "SeniorCustomerService"),
                           new Staff("Alice", "fm1", "123", "FinancialManager"),
                           new Staff("Jack", "pm1", "123", "ProductionManager"),
                           new Staff("Mike", "am1", "123", "AdministrationManager"),
                           new Staff("Simon", "hr1", "123", "SeniorHRManager")};

  static private ArrayList<EventRequest> eventRequests = new ArrayList<EventRequest>();

  public static void main(String[] args) {
    Boolean[] b = {false, false, false, false, true};
    eventRequests.add(new EventRequest(1, "Joe", "fika", "bullar och kaffe", "5", "6", "20", "1300", b));
    eventRequests.add(new EventRequest(2, "Emma", "fest", "partykväll", "2", "3", "100", "5000", b));
    eventRequests.add(new EventRequest(3, "Robert", "grill", "korv och burgare", "10", "12", "10", "700", b));

    Scanner in = new Scanner(System.in);
    Staff activeUser = null;

    boolean run = true;

    System.out.println("SEP system 1.0:\n");

    while(run) {
      String input = in.nextLine();
      switch(input) {
        case "login":
          activeUser = Login.ui(staff);
          break;

        case "logout":
          System.out.println("Logged out");
          activeUser = null;
          break;

        case "stop":
          System.out.println("Stopping");
          run = false;
          break;

        case "commands":
          System.out.println(Login.getCommands(activeUser));
          break;

        case "createEventRequest":
          EventRequest er = EventRequest.creationUI(activeUser, eventRequests.size() + 1);
          if(er != null) {
            eventRequests.add(er);
          }
          break;

        case "viewEventRequest":
          EventRequest.viewEventRequest(activeUser, eventRequests);
          break;

        case "listEventRequest":
          EventRequest.listEventRequest(activeUser, eventRequests);
          break;

        case "redirect":
          EventRequest.redirect(activeUser, eventRequests);
          break;

        case "approve":
          EventRequest.approve(activeUser, eventRequests);
          break;

        case "reject":
          break;

        case "listFinancialRequest":
          break;

        case "updateFinancialRequestStatus":
          break;

        case "viewBudget":
          break;

        case "addFeedback":
          EventRequest.addFeedback(activeUser, eventRequests);
          break;

        case "listTasks":
          break;

        case "viewTask":
          break;

        case "createTask":
          break;

        case "createFinancialRequest":
          break;

        case "createStaffRecruitmentRequest":
          break;

        default:
          System.out.println("No such command");
          break;
      }
    }
    in.close();
  }
}
