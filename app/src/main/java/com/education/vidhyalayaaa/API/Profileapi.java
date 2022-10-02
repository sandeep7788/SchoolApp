package com.education.vidhyalayaaa.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profileapi {

    @SerializedName("status")
    @Expose
    private String status;

    private String profileimage;

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

}
