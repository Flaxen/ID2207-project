import java.util.Scanner;
import java.util.ArrayList;

class FullProgram {

  static private Staff[] staff = {new Staff("Sarah", "cs1", "123", "CustomerService", "CustomerService"),
                          new Staff("Janet", "scs1", "123", "SeniorCustomerService", "CustomerService"),
                          new Staff("Alice", "fm1", "123", "FinancialManager", "FinancialDepartment"),
                          new Staff("Jack", "pm1", "123", "ProductionManager", "ProductionDepartment"),
                          new Staff("Bob", "pm2", "123", "ProductionManager", "ProductionDepartment"),
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
    eventRequests.add(new EventRequest(1, "Joe", "coffe", "free pastry and coffe", "5", "6", "20", "1300", b));
    eventRequests.add(new EventRequest(2, "Emma", "party", "party night", "2", "3", "100", "5000", b));
    eventRequests.add(new EventRequest(3, "Robert", "grill", "hot dogs and burgers", "10", "12", "10", "700", b));

    tasks.add(new Task(1, eventRequests.get(1), "take photo of coffe", "Photography", staff[3], 2));
    tasks.add(new Task(2, eventRequests.get(2), "serve pastries", "Waitress", staff[5], 1));
    tasks.add(new Task(3, eventRequests.get(0), "take photo of grill", "Photography", staff[4], 0));

    recruitments.add(new RecruitmentRequest(1, "Full Time", "ProductionDepartment", 4, "NetworkEngineer", "connect cables"));
    recruitments.add(new RecruitmentRequest(2, "Part Time", "ServiceDepartment", 3, "Chef", "cook food"));

    financialRequests.add(new FinancialRequest(1, "ProductionDepartment", eventRequests.get(1), 1000, "expensive party"));
    financialRequests.add(new FinancialRequest(2, "ServiceDepartment", eventRequests.get(2), 750, "expensive hot dogs"));

    Scanner in = new Scanner(System.in);
    Staff activeUser = null;
    boolean run = true;
    String[] authorizedStaff;
    int id;
    String status;

    System.out.println("SEP system 0.2:");
    System.out.println("Available Commands: " + Login.getCommands(null) + "\n");

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
          authorizedStaff = new String[]{"SeniorCustomerService", "FinancialManager", "AdministrationManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            break;
          }

          id = EventRequest.askForId("event request");

          System.out.println(EventRequest.view(id, eventRequests));
          break;

        case "listEventRequests":
          authorizedStaff = new String[]{"SeniorCustomerService", "FinancialManager", "AdministrationManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            break;
          }

          System.out.println(EventRequest.list(eventRequests));
          System.out.println();
          break;

        case "redirect":
          authorizedStaff = new String[]{"FinancialManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            break;
          }

          id = EventRequest.askForId("event request");

          EventRequest.nextChainInCommand(id, activeUser, eventRequests);
          break;

        case "approve":
          authorizedStaff = new String[]{"SeniorCustomerService", "AdministrationManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            break;
          }

          id = EventRequest.askForId("event request");

          EventRequest.nextChainInCommand(id, activeUser, eventRequests);
          break;

        case "reject":
          authorizedStaff = new String[]{"SeniorCustomerService", "AdministrationManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            break;
          }

          id = EventRequest.askForId("event request");

          EventRequest.reject(id, activeUser, eventRequests);
          break;

        case "updateFinancialRequestStatus":
          authorizedStaff = new String[]{"FinancialManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            break;
          }

          id = EventRequest.askForId("financial request");

          System.out.print("Enter new status: ");
          status = in.nextLine();

          FinancialRequest.updateStatus(id, status, financialRequests);
          break;

        case "setDiscount":
          authorizedStaff = new String[]{"FinancialManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            break;
          }

          id = EventRequest.askForId("event request");
          System.out.print("Enter new discount (%): ");
          String discount = in.nextLine();

          EventRequest.updateDiscount(id, discount, eventRequests);
          break;

        case "updateRecruitmentRequestStatus":
          authorizedStaff = new String[]{"SeniorHRManager", "HRAssistant"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
              break;
          }

          id = EventRequest.askForId("recruitment request");

          System.out.print("Enter new status: ");
          status = in.nextLine();

          RecruitmentRequest.updateStatus(id, status, recruitments);
          break;

        case "addFeedback":
          authorizedStaff = new String[]{"FinancialManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            break;
          }

          id = EventRequest.askForId("event request");

          System.out.print("Enter feedback: ");
          String feedback = in.nextLine();

          EventRequest.addFeedback(id, feedback, eventRequests);
          break;

        case "listTasks":
          if(activeUser == null) {
            System.out.println("Permission denied");
            break;
          }

          System.out.println(Task.list(activeUser, tasks));
          break;

        case "viewTask":
          if(activeUser == null) {
            System.out.println("Permission denied");
            break;
          }

          id = EventRequest.askForId("task");

          System.out.println(Task.view(id, activeUser, tasks));
          break;

        case "createTask":
          Task task = Task.creationUI(activeUser, tasks.size() + 1, eventRequests);
          if(task != null) {
            tasks.add(task);
          }
          break;

        case "addPlan":
          if(activeUser == null) {
            System.out.println("Permission denied");
            break;
          }

          id = EventRequest.askForId("task");
          System.out.print("Enter plan: ");
          String plan = in.nextLine();

          Task.addPlan(id, plan, activeUser, tasks);
          break;

        case "createFinancialRequest":
          authorizedStaff = new String[]{"ServiceManager" , "ProductionManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            break;
          }

          FinancialRequest financialRequest = FinancialRequest.creationUI(activeUser, financialRequests.size() + 1, eventRequests);
          if(financialRequest != null) {
            financialRequests.add(financialRequest);
          }
          break;

        case "listFinancialRequests":
          authorizedStaff = new String[]{"ProductionManager", "ServiceManager", "FinancialManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
              break;
          }

          System.out.println(FinancialRequest.list(activeUser, financialRequests));;
          break;

        case "viewFinancialRequest":
          authorizedStaff = new String[]{"FinancialManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
              break;
          }

          id = EventRequest.askForId("financial request");

          System.out.println(FinancialRequest.view(id, financialRequests));
          break;

        case "createRecruitmentRequest":
          authorizedStaff = new String[]{"ServiceManager" , "ProductionManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            break;
          }

          RecruitmentRequest recruitment = RecruitmentRequest.creationUI(activeUser, recruitments.size() + 1);
          if(recruitment != null) {
            recruitments.add(recruitment);
          }
          break;

        case "listRecruitmentRequests":
          authorizedStaff = new String[]{"SeniorHRManager", "HRAssistant", "ProductionManager", "ServiceManager"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
              break;
          }

          System.out.println(RecruitmentRequest.list(activeUser, recruitments));
          break;

        case "viewRecruitmentRequest":
          authorizedStaff = new String[]{"SeniorHRManager", "HRAssistant"};
          if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
              break;
          }

          id = EventRequest.askForId("recruitment request");

          System.out.println(RecruitmentRequest.view(id, recruitments));
          break;

        default:
          System.out.println("No such command");
          break;
      }
    }
    in.close();
  }
}
