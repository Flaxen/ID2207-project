import java.util.ArrayList;
import java.util.Scanner;

class Task {
    private int id;
    private EventRequest eventRequest;
    private String description;
    private String subTeam;
    private Staff sender;
    private int priority;           // 0:high, 1:medium, 2:low
    
    Task(int id, EventRequest eventRequest, String description, String subTeam, Staff sender, int priority) {
        this.id = id;
        this.eventRequest = eventRequest;
        this.description = description;
        this.subTeam = subTeam;
        this.sender = sender;
        this.priority = priority;
    }

    int getId() {
        return id;
    }

    EventRequest getEventRequest() {
        return eventRequest;
    }

    String getDescription() {
        return description;
    }

    String getSubTeam() {
        return subTeam;
    }

    Staff getSender() {
        return sender;
    }

    int getPriority() {
        return priority;
    }

    static Task creationUI(Staff activeUser, int id, ArrayList<EventRequest> eventRequests) {
        String[] authorizedStaff = {"ProdictionManager", "ServiceManager"};
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
            t.getSubTeam().equals("subTeam") && t.getSender().getName().equals("name") && t.getPriority() == 47) {
                System.out.println("Test completed");
            }
        else {
            System.out.println("Test failed");
        }
    }
}
