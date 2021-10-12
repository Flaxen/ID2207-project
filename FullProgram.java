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
          break;

        case "listEventRequest":
          EventRequest.listEventRequest(activeUser, eventRequests);
          break;

        case "approve":
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
