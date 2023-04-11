package com.example.fyprojectnew;

public class ApprovalsModel {
    public String employee_image;
    public String employee_name;
    public String employee_designation;
    public String employee_qualification;
    public String department;
    public String teaching_subject;
    public String datepicker;
    public String session_spinner;
    public String semister_spinner;
    public String class_spinner;
    public String session;
    public String section;
    public String subject;
    public String key;

    public ApprovalsModel() {
    }

    public ApprovalsModel(String employee_image, String employee_name, String employee_designation, String employee_qualification, String department, String teaching_subject, String datepicker, String session_spinner, String semister_spinner, String class_spinner, String session, String section, String subject, String key) {
        this.employee_image = employee_image;
        this.employee_name = employee_name;
        this.employee_designation = employee_designation;
        this.employee_qualification = employee_qualification;
        this.department = department;
        this.teaching_subject = teaching_subject;
        this.datepicker = datepicker;
        this.session_spinner = session_spinner;
        this.semister_spinner = semister_spinner;
        this.class_spinner = class_spinner;
        this.session = session;
        this.section = section;
        this.subject = subject;
        this.key = key;
    }
}