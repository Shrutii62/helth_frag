package com.example.helth_frag;

public class modelHD {
    String Hname ,address,  mngr_name, email, phoneno, passwd, h_id;

    public modelHD() {}

    public modelHD(String hname, String address, String mngr_name, String email, String phoneno, String passwd, String h_id) {
        Hname = hname;
        this.address = address;
        this.mngr_name = mngr_name;
        this.email = email;
        this.phoneno = phoneno;
        this.passwd = passwd;
        this.h_id = h_id;

    }

    public String getHname() {
        return Hname;
    }

    public void setHname(String hname,String h_id) {
        Hname = hname;
        this.h_id=h_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMngr_name() {
        return mngr_name;
    }

    public void setMngr_name(String mngr_name) {
        this.mngr_name = mngr_name;
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

    public String getpasswd() {
        return passwd;
    }

    public void setpasswd(String passwd) {
        this.passwd = passwd;
    }

     public String geth_id() {
            return h_id;
        }

        public void seth_id(String h_id) {
            this.h_id = h_id;
        }


}
