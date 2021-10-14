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

    void setStatus(String status) {
        this.status = status;
    }

    static FinancialRequest creationUI(Staff activeUser, int id, ArrayList<EventRequest> eventRequests) {
        int eventId = EventRequest.askForId();
        Scanner in = new Scanner(System.in);
        EventRequest eventRequest = EventRequest.getRequest(eventId, eventRequests);

        System.out.print("Enter reason: ");
        String reason = in.nextLine();

        System.out.print("Enter requested amount: ");
        int amount = in.nextInt();

        System.out.println("FinancialRequest created");
        return new FinancialRequest(id, activeUser.getSubteam(), eventRequest, amount, reason);
    }

    static String list(Staff activeUser, ArrayList<FinancialRequest> financialRequests) {
        String out = "\nID:     Status:\n";

        FinancialRequest temp;
        for(int i = 0; i < financialRequests.size(); i++) {
            temp = financialRequests.get(i);
            if((activeUser.getRole().equals("ProductionManager") || activeUser.getRole().equals("ServiceManager"))
                && temp.getDepartment().equals(activeUser.getSubteam())) {
                    out = out + temp.getId() + " " + temp.getStatus() + "\n";
            }   else if(activeUser.getRole().equals("FinancialManager")) {
                out = out + temp.getId() + " " + temp.getStatus() + "\n";
            }
        }
        return out;
    }

    static FinancialRequest getRequest(int id, ArrayList<FinancialRequest> financialRequests) {

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

    static String view(int id, ArrayList<FinancialRequest> financialRequests) {

        FinancialRequest r = getRequest(id, financialRequests);
        if(r == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        sb.append("\nId: " + r.getId() + "\n");
        sb.append("status: " + r.getStatus() + "\n");
        sb.append("department: " + r.getDepartment() + "\n");
        sb.append("related event request id: " + r.getEventRequest().getId() + "\n");
        sb.append("required amount: " + r.getAmount() + "\n");
        sb.append("reason: " + r.getReason() + "\n\n");

        return sb.toString();
    }

    static ArrayList<FinancialRequest> updateStatus(int id, String status, ArrayList<FinancialRequest> financialRequests) {
        FinancialRequest f = getRequest(id, financialRequests);
        if(f == null) {
            return null;
        }

        f.setStatus(status);
        financialRequests.remove(f.getId()-1);
        financialRequests.add(f.getId()-1, f);

        System.out.println("Status updated");
        return financialRequests;
    }

    public static void main(String[] args) {
        Staff s = new Staff("Jack", "pm1", "123", "ProductionManager", "ProductionDepartment");
        EventRequest ev = new EventRequest(1, "clientName", "type", "description", "startDate", "endDate", "expectedNumber", "expectedBudget", null);
        boolean creationTest;
        ArrayList<FinancialRequest> financialRequests = new ArrayList<FinancialRequest>();
        financialRequests.add(new FinancialRequest(1, "ProductionDepartment", ev, 1000, "expensive party"));
        financialRequests.add(new FinancialRequest(2, "ServiceDepartment", ev, 750, "expensive hot dogs"));
        FinancialRequest f = new FinancialRequest(1, s.getSubteam(), ev, 500, "reason");

        f.setStatus("status2");

        if(f.getId() == 1 && f.getDepartment().equals("ProductionDepartment") && f.getEventRequest().getId() == 1 &&
            f.getAmount() == 500 && f.getReason().equals("reason") && f.getStatus().equals("status2")) {
            creationTest = true;
        } else {
            creationTest = false;
        }

        // method test
        boolean[] test = new boolean[4];
        test[0] = list(s, financialRequests).equals("\nID:     Status:\n1 Created\n");
        test[1] = getRequest(1, financialRequests).getDepartment().equals("ProductionDepartment");
        test[2] = view(1, financialRequests).equals("\nId: 1\nstatus: Created\ndepartment: ProductionDepartment\nrelated event request id: 1\nrequired amount: 1000\nreason: expensive party\n\n");
        test[3] = updateStatus(1, "test status", financialRequests).get(0).getStatus().equals("test status");


        for(int i = 0; i < test.length; i++) {
            if(creationTest && !test[i]) {
              System.out.println("Method test failed at test index: " + i);
              return;
            }
          }
          System.out.println("\nTest completed without failure");
    }
}
