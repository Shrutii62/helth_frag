package com.example.helth_frag;

import com.google.android.material.textfield.TextInputEditText;

public class Model_ambDriverdetail {

    String d_name, amb_number, alt_num, reg_phoned;

    public String getReg_phoned() {
        return reg_phoned;
    }

    public void setReg_phoned(String reg_phoned) {
        this.reg_phoned = reg_phoned;
    }

    public Model_ambDriverdetail() {
    }

    public Model_ambDriverdetail(String d_name, String amb_number, String alt_num, String reg_phoned) {
        this.d_name = d_name;
        this.amb_number = amb_number;
        this.alt_num = alt_num;
        this.reg_phoned = reg_phoned;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getAmb_number() {
        return amb_number;
    }

    public void setAmb_number(String amb_number) {
        this.amb_number = amb_number;
    }

    public String getAlt_num() {
        return alt_num;
    }

    public void setAlt_num(String alt_num) {
        this.alt_num = alt_num;
    }
}
