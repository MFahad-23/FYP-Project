package com.example.fyprojectnew;

public class ClaculationModel {
    public String employee;
    public String designation;
    public String spinner;
    public int baisic_pay;
    public int trade_tax;
    public int income_tax;
    public int senior_post_allowance;
    public int house_rent_allowance;
    public int conveyance_allowance;
    public int qualification_allowance;
    public int medical_allowance;
    public int adhoc_relief_2016;
    public int adhoc_relief_2017;
    public int adhoc_relief_2018;
    public int adhoc_relief_2019;
    public int adhoc_relief_2021;
    public int social_security_benefit;
    public int leave_deduction;
    public int deduction;
    public int total_pay;
    public int allowances;

    public ClaculationModel() {
    }


    public ClaculationModel(String employee, String designation, String spinner, int baisic_pay, int trade_tax,
                            int income_tax, int senior_post_allowance, int house_rent_allowance, int conveyance_allowance,
                            int qualification_allowance, int medical_allowance, int adhoc_relief_2016, int adhoc_relief_2017,
                            int adhoc_relief_2018, int adhoc_relief_2019, int adhoc_relief_2021, int social_security_benefit,
                            int leave_deduction, int deduction, int total_pay,int allowances) {
        this.employee = employee;
        this.designation = designation;
        this.spinner = spinner;
        this.baisic_pay = baisic_pay;
        this.trade_tax = trade_tax;
        this.income_tax = income_tax;
        this.senior_post_allowance = senior_post_allowance;
        this.house_rent_allowance = house_rent_allowance;
        this.conveyance_allowance = conveyance_allowance;
        this.qualification_allowance = qualification_allowance;
        this.medical_allowance = medical_allowance;
        this.adhoc_relief_2016 = adhoc_relief_2016;
        this.adhoc_relief_2017 = adhoc_relief_2017;
        this.adhoc_relief_2018 = adhoc_relief_2018;
        this.adhoc_relief_2019 = adhoc_relief_2019;
        this.adhoc_relief_2021 = adhoc_relief_2021;
        this.social_security_benefit = social_security_benefit;
        this.leave_deduction = leave_deduction;
        this.deduction = deduction;
        this.total_pay = total_pay;
        this.allowances =allowances;
    }
}
