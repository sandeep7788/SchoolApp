package com.education.schoolapp.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLogin {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("school_session")
    @Expose
    private String schoolSession;
    @SerializedName("login")
    @Expose
    private String login;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @SerializedName("error")
    @Expose
    private String error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolSession() {
        return schoolSession;
    }

    public void setSchoolSession(String schoolSession) {
        this.schoolSession = schoolSession;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
