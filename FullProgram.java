import java.util.Scanner;

class FullProgram {

  static private Staff[] staff = {new Staff("Sarah", "cs1", "123", "CustomerService"),
                           new Staff("Janet", "scs1", "123", "SeniorCustomerService"),
                           new Staff("Alice", "fm1", "123", "FinancialManager"),
                           new Staff("Jack", "pm1", "123", "ProductionManager"),
                           new Staff("Mike", "am1", "123", "AdministrationManager"),
                           new Staff("Simon", "hr1", "123", "SeniorHRManager")};

  public static void main(String[] args) {


    Scanner in = new Scanner(System.in);
    Staff activeUser;

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
      }

    }

  }

}
