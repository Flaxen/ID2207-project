
class Staff {

  private String name;
  private String username;
  private String password;
  private String role;

  Staff(String name, String username, String password, String role) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.role = role;
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

  public static void main(String[] args) {
    Staff george = new Staff("george", "gorg34", "123", "CustomerService");
    String output = george.getName() + george.getUsername() + george.getPassword() + george.getRole();

    if (output.equals("georgegorg34123CustomerService")) {
      System.out.println("Test completed");
    } else {
      System.out.println("Test failed");

    }  }

}
