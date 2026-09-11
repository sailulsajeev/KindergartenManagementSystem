package com.kindergarten.model;

import java.time.LocalDate;

public class Enrollment {

    private int enrollmentId;
    private int studentId;
    private int classId;
    private LocalDate enrollmentDate;
    private LocalDate endDate;
    private String status;

    public Enrollment() {
    }

    public Enrollment(int enrollmentId,
                      int studentId,
                      int classId,
                      LocalDate enrollmentDate,
                      LocalDate endDate,
                      String status) {

        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.classId = classId;
        this.enrollmentDate = enrollmentDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}