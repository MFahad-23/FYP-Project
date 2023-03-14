package com.example.fyprojectnew;

public class MiscallenousModel {
   private String employeename;
    private String employeedesignation;
    private String employeeimage;
    private String deleteimage;
    public String Key;


    public String getDeleteimage() {
        return deleteimage;
    }

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

    public MiscallenousModel(String employeename,String employeedesignation, String employeeimage,String deleteimage,String Key) {
        this.employeename = employeename;
        this.employeedesignation = employeedesignation;
        this.employeeimage = "";
        this.deleteimage=deleteimage;
        this.Key=Key;
    }
}
