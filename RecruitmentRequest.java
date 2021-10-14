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
            this.status = "Created";
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

    static String list(Staff activeUser, ArrayList<RecruitmentRequest> recruitments) {

        String out = "\nID:     Status:\n";

        RecruitmentRequest temp;
        for(int i = 0; i < recruitments.size(); i++) {
            temp = recruitments.get(i);
            if((activeUser.getRole().equals("ProductionManager") || activeUser.getRole().equals("ServiceManager"))
                && temp.getDepartment().equals(activeUser.getSubteam())) {
                    out = out + temp.getId() + " " + temp.getStatus() + "\n";
            }   else if(activeUser.getRole().equals("SeniorHRManager") || activeUser.getRole().equals("HRAssistant")) {
                    out = out + temp.getId() + " " + temp.getStatus() + "\n";
            }
        }
        return out;
    }

    static ArrayList<RecruitmentRequest> updateStatus(int id, String status, ArrayList<RecruitmentRequest> recruitments) {
        
        RecruitmentRequest r = getRequest(id, recruitments);
        if(r == null) {
            return null;
        }

        r.setStatus(status);
        recruitments.remove(r.getId()-1);
        recruitments.add(r.getId()-1, r);

        System.out.println("Status updated");
        return recruitments;
    }

    static RecruitmentRequest getRequest(int id, ArrayList<RecruitmentRequest> recruitments) {
    
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

    static String view(int id, ArrayList<RecruitmentRequest> recruitments) {

        RecruitmentRequest r = getRequest(id, recruitments);
        if(r == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("\nId: " + r.getId() + "\n");
        sb.append("status: " + r.getStatus() + "\n");
        sb.append("contract type: " + r.getContractType() + "\n");
        sb.append("department: " + r.getDepartment() + "\n");
        sb.append("required years of experience: " + r.getExperience() + "\n");
        sb.append("job title: " + r.getJobTitle() + "\n");
        sb.append("description: " + r.getDescription() + "\n\n");

        return sb.toString();
    }

    public static void main(String[] args) {
        Staff s = new Staff("Jack", "pm1", "123", "ProductionManager", "ProductionDepartment");
        RecruitmentRequest r = new RecruitmentRequest(1, "contractType", s.getSubteam(), 5, "jobTitle", "description");
        boolean creationTest;
        ArrayList<RecruitmentRequest> recruitments = new ArrayList<RecruitmentRequest>();
        recruitments.add(new RecruitmentRequest(1, "Full Time", "ProductionDepartment", 4, "NetworkEngineer", "connect cables"));
        recruitments.add(new RecruitmentRequest(2, "Part Time", "ServiceDepartment", 3, "Chef", "cook food"));

        r.setStatus("status2");

        if(r.getId() == 1 && r.getContractType().equals("contractType") && r.getDepartment().equals("ProductionDepartment") &&
            r.getExperience() == 5 && r.getJobTitle().equals("jobTitle") && r.getDescription().equals("description") && r.getStatus().equals("status2")) {
                creationTest = true;
            } else {
                creationTest = false;
        }

        boolean[] test = new boolean[4];
        test[0] = list(s, recruitments).equals("\nID:     Status:\n1 Created\n");
        test[1] = updateStatus(1, "test status", recruitments).get(0).getStatus().equals("test status");
        test[2] = getRequest(1, recruitments).getDepartment().equals("ProductionDepartment");
        test[3] = view(1, recruitments).equals("\nId: 1\nstatus: test status\ncontract type: Full Time\ndepartment: ProductionDepartment\nrequired years of experience: 4\njob title: NetworkEngineer\ndescription: connect cables\n\n");
    
        for(int i = 0; i < test.length; i++) {
            if(creationTest && !test[i]) {
              System.out.println("Method test failed at test index: " + i);
              return;
            }
          }
          System.out.println("\nTest completed without failure");
    }
}
