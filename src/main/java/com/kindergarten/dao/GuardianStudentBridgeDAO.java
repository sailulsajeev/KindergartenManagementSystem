package com.kindergarten.dao;

import com.kindergarten.model.GuardianStudentBridge;
import com.kindergarten.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class GuardianStudentBridgeDAO {

    // =========================
    // ADD RELATIONSHIP
    // =========================

    public boolean addRelationship(GuardianStudentBridge relationship) {

        String sql = "INSERT INTO guardian_student_bridge " +
                "(guardian_id, student_id, relationship_type, emergency_priority) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    relationship.getGuardianId()
            );

            statement.setInt(
                    2,
                    relationship.getStudentId()
            );

            statement.setString(
                    3,
                    relationship.getRelationshipType()
            );

            if (relationship.getEmergencyPriority() != null) {

                statement.setInt(
                        4,
                        relationship.getEmergencyPriority()
                );

            } else {

                statement.setNull(
                        4,
                        Types.INTEGER
                );
            }

            int rowsInserted =
                    statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println("Error adding guardian-student relationship.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    // =========================
    // GET ALL RELATIONSHIPS
    // =========================

    public List<GuardianStudentBridge> getAllRelationships() {

        List<GuardianStudentBridge> relationships =
                new ArrayList<>();

        String sql =
                "SELECT * FROM guardian_student_bridge";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                int priority =
                        resultSet.getInt("emergency_priority");

                Integer emergencyPriority;

                if (resultSet.wasNull()) {
                    emergencyPriority = null;
                } else {
                    emergencyPriority = priority;
                }

                GuardianStudentBridge relationship =
                        new GuardianStudentBridge(
                                resultSet.getInt("guardian_id"),
                                resultSet.getInt("student_id"),
                                resultSet.getString("relationship_type"),
                                emergencyPriority
                        );

                relationships.add(relationship);
            }

        } catch (SQLException e) {

            System.out.println("Error retrieving guardian-student relationships.");
            System.out.println(e.getMessage());
        }

        return relationships;
    }

    // =========================
    // UPDATE RELATIONSHIP
    // =========================

    public boolean updateRelationship(GuardianStudentBridge relationship) {

        String sql = "UPDATE guardian_student_bridge " +
                "SET relationship_type = ?, emergency_priority = ? " +
                "WHERE guardian_id = ? AND student_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    relationship.getRelationshipType()
            );

            if (relationship.getEmergencyPriority() != null) {

                statement.setInt(
                        2,
                        relationship.getEmergencyPriority()
                );

            } else {

                statement.setNull(
                        2,
                        Types.INTEGER
                );
            }

            statement.setInt(
                    3,
                    relationship.getGuardianId()
            );

            statement.setInt(
                    4,
                    relationship.getStudentId()
            );

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.out.println("Error updating guardian-student relationship.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    // =========================
    // DELETE RELATIONSHIP
    // =========================

    public boolean deleteRelationship(int guardianId, int studentId) {

        String sql = "DELETE FROM guardian_student_bridge " +
                "WHERE guardian_id = ? AND student_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, guardianId);
            statement.setInt(2, studentId);

            int rowsDeleted =
                    statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting guardian-student relationship.");
            System.out.println(e.getMessage());

            return false;
        }
    }
}