package com.education.vidhyalayaaa.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamTypeModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("exam_name")
    @Expose
    private String exam_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }
}

