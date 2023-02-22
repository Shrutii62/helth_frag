package com.example.helth_frag;

public class Model_hrequestfrm {
    String pname,  description, hid , h_addrs, h_name, statusact, key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatusact() {
        return statusact;
    }

    public void setStatusact(String statusact) {
        this.statusact = statusact;
    }

    public Model_hrequestfrm() {
    }

    public Model_hrequestfrm(String pname, String description, String hid, String h_addrs, String h_name, String statusact, String key) {
        this.pname = pname;
        this.description = description;
        this.hid = hid;
        this.h_addrs = h_addrs;
        this.h_name = h_name;
        this.statusact = statusact;
        this.key = key;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getH_addrs() {
        return h_addrs;
    }

    public void setH_addrs(String h_addrs) {
        this.h_addrs = h_addrs;
    }

    public String getH_name() {
        return h_name;
    }

    public void setH_name(String h_name) {
        this.h_name = h_name;
    }
}
