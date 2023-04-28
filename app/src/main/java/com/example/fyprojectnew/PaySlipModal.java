package com.example.fyprojectnew;

import java.io.Serializable;

public class PaySlipModal implements Serializable {
    public String Name;
    public String Department;
    private String uri;

    public PaySlipModal(String generatedFilePath) {
    }

    public PaySlipModal(String name, String department, String uri) {
        Name = name;
        Department = department;
        this.uri = uri;
    }
}
