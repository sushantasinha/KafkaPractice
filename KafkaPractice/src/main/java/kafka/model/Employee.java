package kafka.model;

public class Employee {

    String empId;
    String name;
    String designation;

    public Employee() {
    }

    public Employee(String empId, String name, String designation) {
        this.empId = empId;
        this.name = name;
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override public String toString() {
        return "Employee(" + empId + ", " + name + ", " + designation + ")";
    }



}
