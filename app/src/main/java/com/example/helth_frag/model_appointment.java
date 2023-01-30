package com.example.helth_frag;

public class model_appointment {
    String date;
    String time;
    String issue;
    String ppid;
    String d_id;
    String aptmt_id;
    String p_email;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getP_email() {
        return p_email;
    }

    public void setP_email(String p_email) {
        this.p_email = p_email;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    String p_name;

    public String getAptmt_id() {
        return aptmt_id;
    }

    public void setAptmt_id(String aptmt_id) {
        this.aptmt_id = aptmt_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public model_appointment(String date, String time, String issue, String ppid, String d_id, String aptmt_id, String p_email,String p_name,String status) {
        this.date = date;
        this.time = time;
        this.issue = issue;
        this.ppid = ppid;
        this.d_id = d_id;
        this.aptmt_id = aptmt_id;
        this.p_email = p_email;
        this.p_name = p_name;
        this.status = status;
    }

    public model_appointment() {
    }
}
