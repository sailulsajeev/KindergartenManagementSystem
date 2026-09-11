package com.kindergarten.model;

import java.time.LocalDate;

public class Attendance {

    private int attendanceId;
    private int studentId;
    private LocalDate attendanceDate;
    private String status;
    private String notes;

    public Attendance() {
    }

    public Attendance(int attendanceId,
                      int studentId,
                      LocalDate attendanceDate,
                      String status,
                      String notes) {

        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.notes = notes;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}