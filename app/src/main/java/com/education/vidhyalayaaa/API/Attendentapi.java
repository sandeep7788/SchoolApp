package com.education.vidhyalayaaa.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendentapi {

    @SerializedName("total_meetings")
    @Expose
    private String totalDays;
    @SerializedName("total_present")
    @Expose
    private String totalAttendance;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("attendance_list")
    @Expose
    private List<AttendanceList> attendanceList = null;

    @SerializedName("total_absent")
    @Expose
    private String total_absent;

    @SerializedName("total_leave")
    @Expose
    private String total_leave;

    public String getTotal_absent() {
        return total_absent;
    }

    public void setTotal_absent(String total_absent) {
        this.total_absent = total_absent;
    }

    public String getTotal_leave() {
        return total_leave;
    }

    public void setTotal_leave(String total_leave) {
        this.total_leave = total_leave;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public String getTotalAttendance() {
        return totalAttendance;
    }

    public void setTotalAttendance(String totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<AttendanceList> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceList> attendanceList) {
        this.attendanceList = attendanceList;
    }

}
