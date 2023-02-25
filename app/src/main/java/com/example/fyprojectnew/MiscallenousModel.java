package com.example.fyprojectnew;

public class MiscallenousModel {
   private String employeename;
    private String employeedesignation;
    private String employeeimage;

    public String getEmployeename() {
        return employeename;
    }

    public String getEmployeedesignation() {
        return employeedesignation;
    }

    public String getEmployeeimage() {
        return employeeimage;
    }

    public MiscallenousModel() {
    }

    public MiscallenousModel(String employeename,String employeedesignation, String employeeimage) {
        this.employeename = employeename;
        this.employeedesignation = employeedesignation;
        this.employeeimage = "";
    }
}
