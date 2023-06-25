package com.example.fyprojectnew.Models;

import java.io.Serializable;

public class AdministrationModel {
    private String employeeimage;
    private String employeename;
    private String employeedesignation;
    public String key;
    private String deleteimage;

    public AdministrationModel(String deleteimage) {
        this.deleteimage = deleteimage;
    }

    public String getDeleteimage() {
        return deleteimage;
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

    public AdministrationModel() {
    }

    public AdministrationModel(String employeeimage, String employeename, String employeedesignation,String key) {
        this.employeeimage = employeeimage;
        this.employeename = employeename;
        this.employeedesignation = "";
        this.key=key;
    }
}
