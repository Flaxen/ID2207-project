import java.util.Scanner;
import java.util.ArrayList;


class RecruitmentRequest {
    private int id;
    private String status;
    private String contractType;
    private String department;
    private int experience;
    private String jobTitle;
    private String description;

    RecruitmentRequest(int id, String contractType, String department,
        int experience, String jobTitle, String description) {
            this.id = id;
            this.status = "created";
            this.contractType = contractType;
            this.department = department;
            this.experience = experience;
            this.jobTitle = jobTitle;
            this.description = description;
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

    String getContractType() {
        return contractType;
    }

    String getDepartment() {
        return department;
    }

    int getExperience() {
        return experience;
    }

    String getJobTitle() {
        return jobTitle;
    }

    String getDescription() {
        return description;
    }

    static RecruitmentRequest creationUI(Staff activeUser, int id) {
        String[] authorizedStaff = {"ServiceManager" , "ProductionManager"};
        if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
          return null;
        }

        Scanner in = new Scanner(System.in);
        System.out.print("Enter contract type (Full time / Part time): ");
        String contractType = in.nextLine();

        System.out.print("Enter job title: ");
        String jobTitle = in.nextLine();

        System.out.print("Enter job description: ");
        String description = in.nextLine();

        System.out.print("Enter years of experience: ");
        int experience = in.nextInt();

        System.out.println("RecruitmentRequest created");
        return new RecruitmentRequest(id, contractType, activeUser.getSubteam(), experience, jobTitle, description);
    }

    static void list(Staff activeUser, ArrayList<RecruitmentRequest> recruitments) {
        String[] authorizedStaff = {"SeniorHRManager", "HRAssistant", "ProductionManager", "ServiceManager"};
        if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            return;
        }

        System.out.println("\nID:     Status:");

        RecruitmentRequest temp;
        for(int i = 0; i < recruitments.size(); i++) {
            temp = recruitments.get(i);
            if((activeUser.getRole().equals("ProductionManager") || activeUser.getRole().equals("ServiceManager"))
                && temp.getDepartment().equals(activeUser.getSubteam())) {
                    System.out.println(temp.getId() + " " + temp.getStatus());
            }   else if(activeUser.getRole().equals("SeniorHRManager") || activeUser.getRole().equals("HRAssistant")) {
                System.out.println(temp.getId() + " " + temp.getStatus());
            }
        }
        System.out.println();
    }

    static ArrayList<RecruitmentRequest> updateStatus(Staff activeUser, ArrayList<RecruitmentRequest> recruitments) {
        String[] authorizedStaff = {"SeniorHRManager", "HRAssistant"};
        if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            return null;
        }

        Scanner in = new Scanner(System.in);
        RecruitmentRequest r = getRequest(recruitments);
        if(r == null) {
            return null;
        }

        System.out.print("Enter new status: ");
        String status = in.nextLine();

        r.setStatus(status);
        recruitments.remove(r.getId()-1);
        recruitments.add(r.getId()-1, r);

        System.out.println("Status updated");
        return recruitments;
    }

    static RecruitmentRequest getRequest(ArrayList<RecruitmentRequest> recruitments) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter request Id: ");
        int id = in.nextInt();

        RecruitmentRequest r = null;
        for(int i = 0; i < recruitments.size(); i++) {
            r = recruitments.get(i);
            if(r.getId() == id) {
                return r;
            }
        }
        System.out.println("No such recruitment request");
        return null;
    }

    static void view(Staff activeUser, ArrayList<RecruitmentRequest> recruitments) {
        String[] authorizedStaff = {"SeniorHRManager", "HRAssistant"};
        if(!EventRequest.allowedUser(activeUser, authorizedStaff)) {
            return;
        }

        Scanner in = new Scanner(System.in);
        RecruitmentRequest r = getRequest(recruitments);
        if(r == null) {
            return;
        }

        System.out.println("\nId: " + r.getId());
        System.out.println("status: " + r.getStatus());
        System.out.println("conctract type: " + r.getContractType());
        System.out.println("department: " + r.getDepartment());
        System.out.println("required years of experience: " + r.getExperience());
        System.out.println("job title: " + r.getJobTitle());
        System.out.println("description: " + r.getDescription());
        System.out.println();
    }

    public static void main(String[] args) {
        Staff s = new Staff("Jack", "pm1", "123", "ProductionManager", "ProductionDepartment");
        RecruitmentRequest r = new RecruitmentRequest(1, "contractType", s.getSubteam(), 5, "jobTitle", "description");

        r.setStatus("status2");

        if(r.getId() == 1 && r.getContractType().equals("contractType") && r.getDepartment().equals("ProductionDepartment") &&
            r.getExperience() == 5 && r.getJobTitle().equals("jobTitle") && r.getDescription().equals("description") && r.getStatus("status2")) {
                System.out.println("Test completed");
            } else {
                System.out.println("Test failed");
            }
    }
}
