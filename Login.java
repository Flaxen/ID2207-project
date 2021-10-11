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

  public static void main(String[] args) {

    Staff[] staff = {new Staff("Sarah", "cs1", "123", "CustomerService"),
                     new Staff("Janet", "scs1", "123", "SeniorCustomerService"),
                     new Staff("Alice", "fm1", "123", "FinancialManager")};

    Staff ou1 = login("fm1", "123", staff);
    Staff ou2 = login("fm1", "321", staff);
    Staff ou3 = login("scs1", "123", staff);
    Staff ou4 = login("aphgaogh", "123", staff);

    if(ou1.getName().equals("Alice") && ou2 == null && ou3.getName().equals("Janet") && ou4 == null) {
      System.out.println("Test passed");
    } else {
      System.out.println("Test failed");
    }

  }
}