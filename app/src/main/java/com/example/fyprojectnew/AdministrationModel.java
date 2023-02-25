package com.example.fyprojectnew;

public class AdministrationModel {
    private String employeeimage;
    private String employeename;
    private String employeedesignation;

    public String getEmployeeimage() {
        return employeeimage;
    }

    public String getEmployeename() {
        return employeename;
    }

    public String getEmployeedesignation() {
        return employeedesignation;
    }

    public AdministrationModel() {
    }

    public AdministrationModel(String employeeimage, String employeename, String employeedesignation) {
        this.employeeimage = employeeimage;
        this.employeename = employeename;
        this.employeedesignation = "";
    }
}
