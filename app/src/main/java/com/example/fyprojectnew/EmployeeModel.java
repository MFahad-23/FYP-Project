package com.example.fyprojectnew;

public class EmployeeModel {
    private String employeeimage;
    private String delimage;
    private String employeename;
    private String employeeid;

    public EmployeeModel() {
    }

    EmployeeModel(String employeeimage,String delimage, String employeename, String employeeid){
        this.delimage = "";
        this.employeeimage="";
        this.employeename=employeename;
        this.employeeid=employeeid;
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

    public String getEmployeeid() {
        return employeeid;
    }
}
