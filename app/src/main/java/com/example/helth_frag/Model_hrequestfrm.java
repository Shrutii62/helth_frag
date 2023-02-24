package com.example.helth_frag;

public class Model_hrequestfrm {
    String pname,  description , h_addrs, h_name, statusact, key, hid_recivedRHos,hid_sendig_h_me;

    public String getHid_recivedRHos() {
        return hid_recivedRHos;
    }

    public void setHid_recivedRHos(String hid_recivedRHos) {
        this.hid_recivedRHos = hid_recivedRHos;
    }

    public String getHid_sendig_h_me() {
        return hid_sendig_h_me;
    }

    public void setHid_sendig_h_me(String hid_sendig_h_me) {
        this.hid_sendig_h_me = hid_sendig_h_me;
    }

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

    public Model_hrequestfrm(String pname, String description,  String h_addrs, String h_name, String statusact, String key, String hid_recivedRHos, String hid_sendig_h_me) {
        this.pname = pname;
        this.description = description;
        this.h_addrs = h_addrs;
        this.h_name = h_name;
        this.statusact = statusact;
        this.key = key;
        this.hid_recivedRHos = hid_recivedRHos;
        this.hid_sendig_h_me = hid_sendig_h_me;
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
