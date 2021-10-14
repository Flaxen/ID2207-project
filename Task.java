import java.util.ArrayList;
import java.util.Scanner;

class Task {
    private int id;
    private EventRequest eventRequest;
    private String description;
    private String subTeam;
    private Staff sender;
    private int priority;           // 0:high, 1:medium, 2:low
    private String status;
    private String plan;

    Task(int id, EventRequest eventRequest, String description, String subTeam, Staff sender, int priority) {
        this.id = id;
        this.eventRequest = eventRequest;
        this.description = description;
        this.subTeam = subTeam;
        this.sender = sender;
        this.priority = priority;
        status = "Created";
        plan = "";
    }

    int getId() {
        return id;
    }

    String getPlan() {
      return plan;
    }

    void setPlan(String plan) {
      this.plan = plan;
    }

    String getStatus() {
      return status;
    }

    void setStatus(String status) {
      this.status = status;
    }

    EventRequest getEventRequest() {
        return eventRequest;
    }

    String getDescription() {
        return description;
    }

    String getSubteam() {
        return subTeam;
    }

    Staff getSender() {
        return sender;
    }

    int getPriority() {
        return priority;
    }

    static String list(Staff activeUser, ArrayList<Task> tasks) {

      String out = "\nID:  Status:  Priority:  Sender:\n";

      Task temp;
      for(int i = 0; i < tasks.size(); i++) {

        temp = tasks.get(i);
        if(activeUser.getRole().equals(temp.getSender().getRole()) || activeUser.getSubteam().equals(temp.getSubteam())) {
          out = out + temp.getId() + " " + temp.getStatus() + " " + temp.getPriority() + " " + temp.getSender().getName() + "\n";
        }
      }
      return out;
    }

    static Task getTask(int id, ArrayList<Task> tasks) {

      Task task = null;
      Task temp = null;
      for(int i = 0; i < tasks.size(); i++) {
          temp = tasks.get(i);
          if(temp.getId() == id) {
              task = temp;
              return task;
          }
      }
      System.out.println("No such task");
      return null;
    }

    static String view(int id, Staff activeUser, ArrayList<Task> tasks) {
      // anycan needs to check tasks, no need for authorizedStaff

      Task task = getTask(id, tasks);
      if(task == null) {
        return "";
      }

      StringBuilder sb = new StringBuilder();

      if(activeUser.getRole().equals(task.getSender().getRole()) || activeUser.getSubteam().equals(task.getSubteam())) {
        sb.append("\nId: " + task.getId() + "\n");
        sb.append("Related event request Id: " + task.getEventRequest().getId() + "\n");
        sb.append("Description: " + task.getDescription() + "\n");
        sb.append("Assigned SubTeam: " + task.getSubteam() + "\n");
        sb.append("Sender: " + task.getSender().getName() + "\n");
        sb.append("Priority: " + task.getPriority() + "\n");           // 0:high, 1:medium, 2:low
        sb.append("Status: " + task.getStatus() + "\n");
        sb.append("Plan: " + task.getPlan() + "\n\n");

      } else {
        System.out.println("Permission denied");
      }

      return sb.toString();

    }

    static ArrayList<Task> addPlan(int id, String plan, Staff activeUser, ArrayList<Task> tasks) {

      Task task = getTask(id, tasks);
      if(task == null) {
        return null;
      }

      if(!activeUser.getSubteam().equals(task.getSubteam())) {
        System.out.println("Permission denied");
        return null;
      }

      task.setPlan(plan);
      tasks.remove(task.getId() - 1);
      tasks.add(task.getId() - 1, task);
      System.out.println("Plan added");
      return tasks;
    }

    static Task creationUI(Staff activeUser, int id, ArrayList<EventRequest> eventRequests) {
        String[] authorizedStaff = {"ProductionManager", "ServiceManager", "SeniorCustomerService"};
        if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            return null;
        }

        EventRequest eventRequest;
        String description;
        String subTeam;
        int priority;

        Scanner in = new Scanner(System.in);
        System.out.print("Enter event request id: ");
        int eventid = Integer.parseInt(in.nextLine());
        eventRequest = EventRequest.getRequest(eventid, eventRequests);

        if(eventRequest == null) {
            return null;
        }

        System.out.print("Enter description: ");
        description = in.nextLine();

        System.out.print("Enter Sub Team: ");
        subTeam = in.nextLine();

        System.out.print("Enter priority (0 - high, 1 - medium, 2 - low):  ");
        priority = in.nextInt();

        System.out.println("Task created");
        return new Task(id, eventRequest, description, subTeam, activeUser, priority);

    }

    public static void main(String[] args) {
        Staff s = new Staff("Jack", "pm1", "123", "ProductionManager", "ProductionDepartment");
        Staff s2 = new Staff("Tobias", "ph1", "123", "Photography", "Photography");
        boolean[] b = {true, false, true, true, false};
        Task t = new Task(1, new EventRequest(1, "bob", "fika", "description", "startDate", "endDate", "expectedNumber", "expectedBudget", b),
        "description", "Photography", new Staff("name", "username", "password", "role", "subTeam"), 47);
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(t);


        t.setStatus("status2");
        t.setPlan("plan2");

        boolean creationTest;

        if(t.getId() == 1 && t.getEventRequest().getId() == 1 && t.getDescription().equals("description") && t.getPlan().equals("plan2") &&
            t.getSubteam().equals("subTeam") && t.getSender().getName().equals("name") && t.getPriority() == 47 && t.getStatus().equals("status2")) {
                creationTest = true;
            }
        else {
            creationTest = false;
        }

        boolean[] test = new boolean[4];
        test[0] = list(s2, tasks).equals("\nID:  Status:  Priority:  Sender:\n1 status2 47 name");
        test[1] = getTask(1, tasks).getDescription().equals("description");
        test[2] = view(1, s2, tasks).equals("\nId: 1\nRelated event request Id: 1\nDescription: description\nAssigned SubTeam: Photography\nSender: name\nPriority: 47\nStatus: status2\nPlan: plan2\n\n");

        for(int i = 0; i < test.length; i++) {
            if(creationTest && !test[i]) {
              System.out.println("Method test failed at test index: " + i);
              return;
            }
          }
          System.out.println("\nTest completed without failure");
    }
}

















//
