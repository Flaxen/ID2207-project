import java.util.Scanner;

class Login {
  private static Staff login(String username, String password, Staff[] users) {

    for(int i = 0; i < users.length; i++) {
      if(users[i].getUsername().equals(username)) {
        if(users[i].getPassword().equals(password)) {
          // correct password
          System.out.println("Logged in");
          return users[i];
        } else {
          // wrong password
          System.out.println("Wrong password");
          return null;
        }
      }
    }
    // username not exist
    System.out.println("No such username");
    return null;
  }

  public static Staff ui(Staff[] staff) {
    Scanner in = new Scanner(System.in);
    System.out.print("Username: ");

    String username = in.nextLine();

    System.out.print("Password: ");
    String password = in.nextLine();

    Staff user = login(username, password, staff);

    return user;
  }

  public static String getCommands(Staff staff) {
    if(staff == null) {
      return "login, logout, stop, commands";
    }

    switch(staff.getRole()) {
      case "CustomerService":
        return "createEventRequest, logout, stop, viewTask, listTasks, commands";

      case "SeniorCustomerService":
        return "listEventRequests, viewEventRequest, approve, reject, createTask, logout, stop, viewTask, listTasks, commands";

      case "FinancialManager":
        return "listEventRequests, viewEventRequest, listFinancialRequests, viewFinancialRequest, updateFinancialRequestStatus, addFeedback, setDiscount, logout, stop, viewTask, listTasks, commands";

      case "ProductionManager":
        return "listTasks, viewTask, createTask, createFinancialRequest, createRecruitmentRequest, listFinancialRequests, listRecruitmentRequest, logout, stop, commands";

      case "ServiceManager":
        return "listTasks, viewTask, createTask, createFinancialRequest, createRecruitmentRequest, listFinancialRequests, listRecruitmentRequest, logout, stop, commands";

      case "AdministrationManager":
        return "listEventRequest, viewEventRequest, approve, reject, viewTask, listTasks, logout, stop, commands";

      case "SeniorHRManager":
        return "listRecruitmentRequest, viewRecruitmentRequest, updateRecruitmentRequestStatus, viewTask, listTasks, logout, stop, commands";

      case "Photography":
        return "addPlan, viewTask, logout, stop, listTasks, commands";

      case "SeniorWaitress":
        return "addPlan, viewTask, logout, stop, listTasks, commands";

      default:
        return "error: unknown role";
    }
  }

  public static void main(String[] args) {

    Staff[] staff = {new Staff("Sarah", "cs1", "123", "CustomerService", "CustomerService"),
                     new Staff("Janet", "scs1", "123", "SeniorCustomerService", null),
                     new Staff("Alice", "fm1", "123", "FinancialManager", null)};

    Staff ou1 = login("fm1", "123", staff);
    Staff ou2 = login("fm1", "321", staff);
    Staff ou3 = login("scs1", "123", staff);
    Staff ou4 = login("aphgaogh", "123", staff);
    String ou5 = getCommands(staff[0]);

    if(ou1.getName().equals("Alice") && ou2 == null && ou3.getName().equals("Janet") && ou4 == null &&
      ou5.equals("listEventRequests, viewEventRequest, approve, reject, logout, stop, listTasks, commands")) {

      System.out.println("Test passed");
    } else {
      System.out.println("Test failed");
    }
  }
}
