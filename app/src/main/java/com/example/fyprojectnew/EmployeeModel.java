package com.example.fyprojectnew;

public class EmployeeModel {
    private String employeeimage;
    private String delimage;
    private String employeename;
    private String employee_designation;
    public String key;

    public EmployeeModel() {
    }

    EmployeeModel(String employeeimage,String delimage, String employeename, String employee_designation, String key){
        this.delimage = "";
        this.employeeimage="";
        this.employeename=employeename;
        this.employee_designation=employee_designation;
        this.key=key;
    }
    public String getDelimage() {
        return delimage;
    }
    public String getEmployeeimage() {
        return employeeimage;
    }

    public String getEmployeename() {
        return employeename;
    }

    public String getEmployee_designation() {
        return employee_designation;
    }

}
