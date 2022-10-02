package com.education.vidhyalayaaa.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkSheetModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("school_id")
    @Expose
    private String schoolId;
    @SerializedName("classid")
    @Expose
    private String classid;
    @SerializedName("sectionid")
    @Expose
    private String sectionid;
    @SerializedName("subjectid")
    @Expose
    private String subjectid;
    @SerializedName("studentid")
    @Expose
    private String studentid;
    @SerializedName("exam_id")
    @Expose
    private String examId;
    @SerializedName("maxmarks")
    @Expose
    private String maxmarks;
    @SerializedName("minmarks")
    @Expose
    private String minmarks;
    @SerializedName("obtain_marks")
    @Expose
    private String obtainMarks;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("school_session")
    @Expose
    private String schoolSession;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("exam_name")
    @Expose
    private String examName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getMaxmarks() {
        return maxmarks;
    }

    public void setMaxmarks(String maxmarks) {
        this.maxmarks = maxmarks;
    }

    public String getMinmarks() {
        return minmarks;
    }

    public void setMinmarks(String minmarks) {
        this.minmarks = minmarks;
    }

    public String getObtainMarks() {
        return obtainMarks;
    }

    public void setObtainMarks(String obtainMarks) {
        this.obtainMarks = obtainMarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSchoolSession() {
        return schoolSession;
    }

    public void setSchoolSession(String schoolSession) {
        this.schoolSession = schoolSession;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

}

