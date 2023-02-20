package com.example.fyprojectnew;

public class EmployeeModel {
    private String employeeimage;
    private String employeename;
    private String employeeid;

    public EmployeeModel() {
    }

    EmployeeModel(String employeeimage, String employeename, String employeeid){
        this.employeeimage=employeeimage;
        this.employeename=employeename;
        this.employeeid=employeeid;
    }
    public String getEmployeeimage() {
        return employeeimage;
    }

    public String getEmployeename() {
        return employeename;
    }

    public String getEmployeeid() {
        return employeeid;
    }
}
