package com.education.vidhyalaya.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MomentApi {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("file_type")
    @Expose
    private Object fileType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Object getFileType() {
        return fileType;
    }

    public void setFileType(Object fileType) {
        this.fileType = fileType;
    }

}
