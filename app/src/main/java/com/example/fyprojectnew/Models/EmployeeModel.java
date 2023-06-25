package com.example.fyprojectnew.Models;

public class EmployeeModel {
    private String employeeimage;
    private String delimage;
    private String employeename;
    private String employeedesignation;
    public String key;

    public EmployeeModel() {
    }

    EmployeeModel(String employeeimage,String delimage, String employeename, String employee_designation, String key){
        this.delimage = "";
        this.employeeimage="";
        this.employeename=employeename;
        this.employeedesignation=employee_designation;
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

    public String getEmployeedesignation() {
        return employeedesignation;
    }

}
