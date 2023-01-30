package com.example.helth_frag;

public class model_d_addPrescriptn {
    String pid;
    String did;
    String hid;

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTest_recomd() {
        return test_recomd;
    }

    public void setTest_recomd(String test_recomd) {
        this.test_recomd = test_recomd;
    }

    public model_d_addPrescriptn() {
    }

    public model_d_addPrescriptn(String pid, String did, String description, String test_recomd, String hid) {
        this.pid = pid;
        this.did = did;
        this.hid = hid;
        this.description = description;
        this.test_recomd = test_recomd;
    }

    String description;
    String test_recomd;



}
