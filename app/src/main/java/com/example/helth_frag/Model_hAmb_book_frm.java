package com.example.helth_frag;

public class Model_hAmb_book_frm {

    String dateget;
    String timeget;
    String Address;
    String amb_number;
    String reg_phoned;
    String key;
    String get_Hid;

    public String getDateget() {
        return dateget;
    }

    public void setDateget(String dateget) {
        this.dateget = dateget;
    }

    public String getTimeget() {
        return timeget;
    }

    public void setTimeget(String timeget) {
        this.timeget = timeget;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAmb_number() {
        return amb_number;
    }

    public void setAmb_number(String amb_number) {
        this.amb_number = amb_number;
    }

    public String getReg_phoned() {
        return reg_phoned;
    }

    public void setReg_phoned(String reg_phoned) {
        this.reg_phoned = reg_phoned;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGet_Hid() {
        return get_Hid;
    }

    public void setGet_Hid(String get_Hid) {
        this.get_Hid = get_Hid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Model_hAmb_book_frm() {
    }

    String status;

    public Model_hAmb_book_frm(String dateget, String timeget, String address, String amb_number, String reg_phoned, String key, String get_Hid, String status) {
        this.dateget = dateget;
        this.timeget = timeget;
        Address = address;
        this.amb_number = amb_number;
        this.reg_phoned = reg_phoned;
        this.key = key;
        this.get_Hid = get_Hid;
        this.status = status;
    }
}
