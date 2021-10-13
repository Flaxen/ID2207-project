import java.util.Scanner;
import java.util.ArrayList;

class FinancialRequest {
    private int id;
    private String status;
    private String department;
    private EventRequest eventRequest;
    private int amount;
    private String reason;

    FinancialRequest(int id, String department, EventRequest eventRequest, int amount, String reason) {
            this.id = id;
            this.status = "Created";
            this.department = department;
            this.eventRequest = eventRequest;
            this.amount = amount;
            this.reason = reason;
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

    String getDepartment() {
        return department;
    }

    int getAmount() {
        return amount;
    }

    EventRequest getEventRequest() {
        return eventRequest;
    }

    String getReason() {
        return reason;
    }

    static FinancialRequest creationUI(Staff activeUser, int id, ArrayList<EventRequest> eventRequests) {
        String[] authorizedStaff = {"ServiceManager" , "ProductionManager"};
        if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
          return null;
        }

        Scanner in = new Scanner(System.in);
        EventRequest eventRequest = EventRequest.getRequest(eventRequests);

        System.out.print("Enter reason: ");
        String reason = in.nextLine();

        System.out.print("Enter requested amount: ");
        int amount = in.nextInt();

        System.out.println("FinancialRequest created");
        return new FinancialRequest(id, activeUser.getSubteam(), eventRequest, amount, reason);
    }

    static void list(Staff activeUser, ArrayList<FinancialRequest> financialRequests) {
        String[] authorizedStaff = {"ProductionManager", "ServiceManager", "FinancialManager"};
        if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            return;
        }

        System.out.println("\nID:     Status:");

        FinancialRequest temp;
        for(int i = 0; i < financialRequests.size(); i++) {
            temp = financialRequests.get(i);
            if((activeUser.getRole().equals("ProductionManager") || activeUser.getRole().equals("ServiceManager"))
                && temp.getDepartment().equals(activeUser.getSubteam())) {
                    System.out.println(temp.getId() + " " + temp.getStatus());
            }   else if(activeUser.getRole().equals("FinancialManager")) {
                System.out.println(temp.getId() + " " + temp.getStatus());
            }
        }
        System.out.println();
    }

    static FinancialRequest getRequest(ArrayList<FinancialRequest> financialRequests) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter request Id: ");
        int id = in.nextInt();

        FinancialRequest f = null;
        for(int i = 0; i < financialRequests.size(); i++) {
            f = financialRequests.get(i);
            if(f.getId() == id) {
                return f;
            }
        }
        System.out.println("No such financial request");
        return null;
    }

    static void view(Staff activeUser, ArrayList<FinancialRequest> financialRequests) {
        String[] authorizedStaff = {"FinancialManager"};
        if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            return;
        }

        Scanner in = new Scanner(System.in);
        FinancialRequest r = getRequest(financialRequests);
        if(r == null) {
            return;
        }

        System.out.println("\nId: " + r.getId());
        System.out.println("status: " + r.getStatus());
        System.out.println("department: " + r.getDepartment());
        System.out.println("related event request id: " + r.getEventRequest().getId());
        System.out.println("required amount: " + r.getAmount());
        System.out.println("reason: " + r.getReason());
        System.out.println();
    }

    static ArrayList<FinancialRequest> updateStatus(Staff activeUser, ArrayList<FinancialRequest> financialRequests) {
        String[] authorizedStaff = {"FinancialManager"};
        if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            return null;
        }

        Scanner in = new Scanner(System.in);
        FinancialRequest f = getRequest(financialRequests);
        if(f == null) {
            return null;
        }

        System.out.print("Enter new status: ");
        String status = in.nextLine();

        f.setStatus(status);
        financialRequests.remove(f.getId()-1);
        financialRequests.add(f.getId()-1, f);

        System.out.println("Status updated");
        return financialRequests;
    }

    public static void main(String[] args) {
        Staff s = new Staff("Jack", "pm1", "123", "ProductionManager", "ProductionDepartment");
        EventRequest ev = new EventRequest(1, "clientName", "type", "description", "startDate", "endDate", "expectedNumber", "expectedBudget", null);
        FinancialRequest f = new FinancialRequest(1, s.getSubteam(), ev, 500, "reason");

        f.setStatus("status2");

        if(f.getId() == 1 && f.getDepartment().equals("ProductionDepartment") && f.getEventRequest().getId() == 1 &&
            f.getAmount() == 500 && f.getReason().equals("reason") && f.getStatus().equals("status2")) {
                System.out.println("Test completed");
            } else {
                System.out.println("Test failed");
            }
    }
}
