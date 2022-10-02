package com.education.vidhyalayaaa.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feesdet {

    @SerializedName("Feehead")
    @Expose
    private String feehead;
    @SerializedName("Amt")
    @Expose
    private String amt;
    @SerializedName("Duedate")
    @Expose
    private String duedate;

    public String getFeehead() {
        return feehead;
    }

    public void setFeehead(String feehead) {
        this.feehead = feehead;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

}