import java.util.Scanner;
import java.util.ArrayList;

class FullProgram {

  static private Staff[] staff = {new Staff("Sarah", "cs1", "123", "CustomerService", "CustomerService"),
                          new Staff("Janet", "scs1", "123", "SeniorCustomerService", "CustomerService"),
                          new Staff("Alice", "fm1", "123", "FinancialManager", "FinancialDepartment"),
                          new Staff("Jack", "pm1", "123", "ProductionManager", "ProductionDepartment"),
                          new Staff("BobareeBobaroo", "pm2", "123", "ProductionManager", "ProductionDepartment"),
                          new Staff("Natalie", "sm1", "123", "ServiceManager", "ServiceDepartment"),
                          new Staff("Mike", "am1", "123", "AdministrationManager", "AdministrationDepartment"),
                          new Staff("Simon", "hr1", "123", "SeniorHRManager", "HRTeam"),
                          new Staff("Tobias", "ph1", "123", "Photography", "Photography"),
                          new Staff("Kate", "wh1", "123", "SeniorWaitress", "Waitress")};

  static private ArrayList<EventRequest> eventRequests = new ArrayList<EventRequest>();
  static private ArrayList<Task> tasks = new ArrayList<Task>();
  static private ArrayList<RecruitmentRequest> recruitments = new ArrayList<RecruitmentRequest>();
  static private ArrayList<FinancialRequest> financialRequests = new ArrayList<FinancialRequest>();

  public static void main(String[] args) {
    boolean[] b = {false, false, false, false, true};
    eventRequests.add(new EventRequest(1, "Joe", "fika", "bullar och kaffe", "5", "6", "20", "1300", b));
    eventRequests.add(new EventRequest(2, "Emma", "fest", "partykväll", "2", "3", "100", "5000", b));
    eventRequests.add(new EventRequest(3, "Robert", "grill", "korv och burgare", "10", "12", "10", "700", b));

    tasks.add(new Task(1, eventRequests.get(1), "take photo of fika", "Photography", staff[3], 2));
    tasks.add(new Task(2, eventRequests.get(2), "serve fika", "Waitress", staff[5], 1));
    tasks.add(new Task(3, eventRequests.get(0), "take photo of grill", "Photography", staff[3], 0));

    recruitments.add(new RecruitmentRequest(1, "Full Time", "ProductionDepartment", 4, "NetworkEngineer", "connect cables"));
    recruitments.add(new RecruitmentRequest(2, "Part Time", "ServiceDepartment", 3, "Chef", "cook food"));

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
          EventRequest.view(activeUser, eventRequests);
          break;

        case "listEventRequests":
          EventRequest.list(activeUser, eventRequests);
          break;

        case "redirect":
          EventRequest.redirect(activeUser, eventRequests);
          break;

        case "approve":
          EventRequest.approve(activeUser, eventRequests);
          break;

        case "reject":
          break;

        case "updateFinancialRequestStatus":
          break;

        case "viewBudget":
          break;

        case "addFeedback":
          EventRequest.addFeedback(activeUser, eventRequests);
          break;

        case "listTasks":
          Task.list(activeUser, tasks);
          break;

        case "viewTask":
          Task.view(activeUser, tasks);
          break;

        case "createTask":
          Task task = Task.creationUI(activeUser, tasks.size() + 1, eventRequests);
          if(task != null) {
            tasks.add(task);
          }
          break;

        case "createFinancialRequest":
          FinancialRequest financialRequest = FinancialRequest.creationUI(activeUser, financialRequests.size() + 1, eventRequests);
          if(financialRequest != null) {
            financialRequests.add(financialRequest);
          }
          break;

        case "listFinancialRequests":
          FinancialRequest.list(activeUser, financialRequests);
          break;
        
        case "viewFinancialRequest":
          FinancialRequest.view(activeUser, financialRequests);
          break;

        case "createRecruitmentRequest":
          RecruitmentRequest recruitment = RecruitmentRequest.creationUI(activeUser, recruitments.size() + 1);
          if(recruitment != null) {
            recruitments.add(recruitment);
          }
          break;

        case "listRecruitmentRequests":
          RecruitmentRequest.list(activeUser, recruitments);
          break;
        
        case "viewRecruitmentRequest":
          RecruitmentRequest.view(activeUser, recruitments);
          break;

        default:
          System.out.println("No such command");
          break;
      }
    }
    in.close();
  }
}
