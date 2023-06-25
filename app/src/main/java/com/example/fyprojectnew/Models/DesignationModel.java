package com.example.fyprojectnew.Models;

public class DesignationModel {
    private String image;
    private String employeedesignation;

    public DesignationModel() {
    }

    DesignationModel(String image, String employeedesignation){
        this.image=image;
        this.employeedesignation=employeedesignation;
    }

    public String getImage() {
        return image;
    }

    public String getEmployeedesignation() {
        return employeedesignation;
    }
}
