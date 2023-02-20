package com.example.fyprojectnew;

public class DepartmentModel {
    private String departsimage;
    private String departname;

    public DepartmentModel() {
    }

    DepartmentModel(String departsimage, String departname){
        this.departsimage=departsimage;
        this.departname=departname;
    }

    public String getDepartimage() {
        return departsimage;
    }

    public String getDepartname() {
        return departname;
    }
}
