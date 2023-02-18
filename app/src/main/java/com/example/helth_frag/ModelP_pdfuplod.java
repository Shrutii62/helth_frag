package com.example.helth_frag;

public class ModelP_pdfuplod {
    String pdfTitle,pdfURL,pid,did,hid;

    public ModelP_pdfuplod() {

    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
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

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public ModelP_pdfuplod(String pdfTitle, String pdfURL, String pid, String did, String hid) {
        this.pdfTitle = pdfTitle;
        this.pdfURL = pdfURL;
        this.pid = pid;
        this.did = did;
        this.hid = hid;
    }
}
