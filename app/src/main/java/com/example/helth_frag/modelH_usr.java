package com.example.helth_frag;

public class modelH_usr {
    String Uname;
    String email;
    String phoneno;
    String passwd;
    String h_id;
    String userTypedd;

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    String u_id;



    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
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

    public String getUserTypedd() {
        return userTypedd;
    }

    public void setUserTypedd(String userTypedd) {
        this.userTypedd = userTypedd;
    }

    public modelH_usr(String uname, String email, String phoneno, String passwd, String h_id, int userTypedd, String u_id) {
        Uname = uname;
        this.email = email;
        this.phoneno = phoneno;
        this.passwd = passwd;
        this.h_id = h_id;
        this.u_id = u_id;
        this.userTypedd = String.valueOf(userTypedd);
    }

    public modelH_usr() {
    }
}
