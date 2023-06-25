package com.example.fyprojectnew.Models;

public class DepartmentModel {
   public String departmentimage;
    private String departname;

    public DepartmentModel() {
    }

    public DepartmentModel(String departsimage, String departname){
        this.departmentimage=departsimage;
        this.departname=departname;
    }

    public String getDepartimage() {
        return departmentimage;
    }

    public String getDepartname() {
        return departname;
    }
}
