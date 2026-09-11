package com.kindergarten.model;

public class GuardianStudentBridge {

    private int guardianId;
    private int studentId;
    private String relationshipType;
    private Integer emergencyPriority;

    public GuardianStudentBridge() {
    }

    public GuardianStudentBridge(int guardianId,
                                 int studentId,
                                 String relationshipType,
                                 Integer emergencyPriority) {

        this.guardianId = guardianId;
        this.studentId = studentId;
        this.relationshipType = relationshipType;
        this.emergencyPriority = emergencyPriority;
    }

    public int getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(int guardianId) {
        this.guardianId = guardianId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public Integer getEmergencyPriority() {
        return emergencyPriority;
    }

    public void setEmergencyPriority(Integer emergencyPriority) {
        this.emergencyPriority = emergencyPriority;
    }
}