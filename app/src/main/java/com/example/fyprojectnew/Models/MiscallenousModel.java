package com.example.fyprojectnew.Models;

public class MiscallenousModel {
   private String employeename;
    private String employeedesignation;
    private String employeeimage;
    public String Key;



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

    public MiscallenousModel(String employeename,String employeedesignation, String employeeimage,String Key) {
        this.employeename = employeename;
        this.employeedesignation = employeedesignation;
        this.employeeimage = "";
        this.Key=Key;
    }
}
