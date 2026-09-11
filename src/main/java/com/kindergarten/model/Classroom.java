package com.kindergarten.model;

public class Classroom {

    private int classId;
    private String className;
    private String roomNumber;
    private int capacity;
    private Integer leadTeacherId;

    public Classroom() {
    }

    public Classroom(int classId, String className, String roomNumber,
                     int capacity, Integer leadTeacherId) {

        this.classId = classId;
        this.className = className;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.leadTeacherId = leadTeacherId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Integer getLeadTeacherId() {
        return leadTeacherId;
    }

    public void setLeadTeacherId(Integer leadTeacherId) {
        this.leadTeacherId = leadTeacherId;
    }
}