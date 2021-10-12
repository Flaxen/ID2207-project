
class Staff {
  private String name;
  private String username;
  private String password;
  private String role;
  private String subTeam;


  Staff(String name, String username, String password, String role, String subTeam) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.role = role;
    this.subTeam = subTeam;
  }

  String getName() {
    return name;
  }

  String getUsername() {
    return username;
  }

  String getPassword() {
    return password;
  }

  String getRole() {
    return role;
  }

  String getSubteam() {
    return subTeam;
  }

  public static void main(String[] args) {
    Staff george = new Staff("george", "gorg34", "123", "CustomerService", "CustomerService");
    String output = george.getName() + george.getUsername() + george.getPassword() + george.getRole() + george.getSubteam();

    if (output.equals("georgegorg34123CustomerServiceCustomerService")) {
      System.out.println("Test completed");
    } else {
      System.out.println("Test failed");

    }  }

}
