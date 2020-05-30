package com.education.schoolapp.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profileupdateapi {
    public String getEmail() {
        return status;
    }

    public void setEmail(String email) {
        this.status = status;
    }

    @SerializedName("status")
    @Expose
    private String status;
}