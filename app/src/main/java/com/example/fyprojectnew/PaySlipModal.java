package com.example.fyprojectnew;

import java.io.Serializable;

public class PaySlipModal implements Serializable {
    public String filePath;
    public String name;
    public String department;
    String generated_date;

    public PaySlipModal(String filePath, String name, String department,String generated_date) {
        filePath = filePath;
        name = name;
        department = department;
        generated_date = generated_date;
    }
}
