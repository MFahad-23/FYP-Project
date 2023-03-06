package com.example.fyprojectnew;

public class DepartmentModel {
    String departmentimage="";
    String departname="";

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
