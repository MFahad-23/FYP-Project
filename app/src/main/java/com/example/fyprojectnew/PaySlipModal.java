package com.example.fyprojectnew;
import java.io.Serializable;

public class PaySlipModal implements Serializable {
    public String generatedFilePath;
    public String employeename;
    public String employeedesignation;
    public String date;
    public String key;

    public PaySlipModal() {
    }

    public PaySlipModal(String generatedFilePath, String employeename, String employeedesignation, String  date, String key) {
        this.generatedFilePath = generatedFilePath;
        this.employeename = employeename;
        this.employeedesignation = employeedesignation;
        this.date = date;
        this.key = key;
    }
}
