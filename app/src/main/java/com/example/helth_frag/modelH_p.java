package com.example.helth_frag;

public class modelH_p {
    String pname,addrs, email, phoneno, passwd, h_id, p_id;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getAddrs() {
        return addrs;
    }

    public void setAddrs(String addrs) {
        this.addrs = addrs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getH_id() {
        return h_id;
    }

    public void setH_id(String h_id) {
        this.h_id = h_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public modelH_p(String pname, String addrs, String email, String phoneno, String passwd, String h_id, String p_id) {
        this.pname = pname;
        this.addrs = addrs;
        this.email = email;
        this.phoneno = phoneno;
        this.passwd = passwd;
        this.h_id = h_id;
        this.p_id = p_id;
    }

    public modelH_p() {
    }
}
