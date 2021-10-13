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

    Task(int id, EventRequest eventRequest, String description, String subTeam, Staff sender, int priority) {
        this.id = id;
        this.eventRequest = eventRequest;
        this.description = description;
        this.subTeam = subTeam;
        this.sender = sender;
        this.priority = priority;
        status = "Created";
    }

    int getId() {
        return id;
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

    static void list(Staff activeUser, ArrayList<Task> tasks) {
      // anycan needs to check tasks, no need for authorizedStaff
      if(activeUser == null) {
        System.out.println("Permission denied");
        return;
      }

      System.out.println("\nID:  Status:  Priority:  Sender:");

      Task temp;
      for(int i = 0; i < tasks.size(); i++) {

        temp = tasks.get(i);
        if(activeUser.getRole().equals(temp.getSender().getRole()) || activeUser.getSubteam().equals(temp.getSubteam())) {
          System.out.println(temp.getId() + " " + temp.getStatus() + " " + temp.getPriority() + " " + temp.getSender().getName());
        }

      }
      System.out.println();
    }

    static Task getTask(ArrayList<Task> tasks) {
      Scanner in = new Scanner(System.in);
      System.out.print("Enter task Id: ");
      int id = in.nextInt();

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


    static void view(Staff activeUser, ArrayList<Task> tasks) {
      // anycan needs to check tasks, no need for authorizedStaff
      if(activeUser == null) {
        System.out.println("Permission denied");
        return;
      }

      Task task = getTask(tasks);
      if(task == null) {
        return;
      }

      if(activeUser.getRole().equals(task.getSender().getRole()) || activeUser.getSubteam().equals(task.getSubteam())) {
        System.out.println("\nId: " + task.getId());
        System.out.println("Related event request Id: " + task.getEventRequest().getId());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Assigned SubTeam: " + task.getSubteam());
        System.out.println("Sender: " + task.getSender().getName());
        System.out.println("Priority: " + task.getPriority());           // 0:high, 1:medium, 2:low
        System.out.println("Status: " + task.getStatus());
      } else {
        System.out.println("Permission denied");
      }

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
        eventRequest = EventRequest.getRequest(eventRequests);

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
        boolean[] b = {true, false, true, true, false};
        Task t = new Task(1, new EventRequest(1, "bob", "fika", "description", "startDate", "endDate",
        "expectedNumber", "expectedBudget", b),
        "description", "subTeam", new Staff("name", "username", "password", "role", "subTeam"), 47);

        if(t.getId() == 1 && t.getEventRequest().getId() == 1 && t.getDescription().equals("description") &&
            t.getSubteam().equals("subTeam") && t.getSender().getName().equals("name") && t.getPriority() == 47 && t.getStatus().equals("Created")) {
                System.out.println("Test completed");
            }
        else {
            System.out.println("Test failed");
        }
    }
}
