package com.kindergarten.dao;

import com.kindergarten.model.Enrollment;
import com.kindergarten.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    public boolean addEnrollment(Enrollment enrollment) {

        String sql = "INSERT INTO enrollments " +
                "(student_id, class_id, enrollment_date, end_date, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, enrollment.getStudentId());
            statement.setInt(2, enrollment.getClassId());

            statement.setDate(
                    3,
                    java.sql.Date.valueOf(enrollment.getEnrollmentDate())
            );

            if (enrollment.getEndDate() == null) {
                statement.setNull(4, Types.DATE);
            } else {
                statement.setDate(
                        4,
                        java.sql.Date.valueOf(enrollment.getEndDate())
                );
            }

            statement.setString(5, enrollment.getStatus());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println("Error adding enrollment.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    public List<Enrollment> getAllEnrollments() {

        List<Enrollment> enrollments = new ArrayList<>();

        String sql = "SELECT * FROM enrollments";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                java.sql.Date endDateFromDatabase =
                        resultSet.getDate("end_date");

                Enrollment enrollment = new Enrollment(
                        resultSet.getInt("enrollment_id"),
                        resultSet.getInt("student_id"),
                        resultSet.getInt("class_id"),
                        resultSet.getDate("enrollment_date").toLocalDate(),
                        endDateFromDatabase == null
                                ? null
                                : endDateFromDatabase.toLocalDate(),
                        resultSet.getString("status")
                );

                enrollments.add(enrollment);
            }

        } catch (SQLException e) {

            System.out.println("Error retrieving enrollments.");
            System.out.println(e.getMessage());
        }

        return enrollments;
    }

    public boolean updateEnrollment(Enrollment enrollment) {

        String sql = "UPDATE enrollments " +
                "SET student_id = ?, class_id = ?, " +
                "enrollment_date = ?, end_date = ?, status = ? " +
                "WHERE enrollment_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, enrollment.getStudentId());
            statement.setInt(2, enrollment.getClassId());

            statement.setDate(
                    3,
                    java.sql.Date.valueOf(enrollment.getEnrollmentDate())
            );

            if (enrollment.getEndDate() == null) {
                statement.setNull(4, Types.DATE);
            } else {
                statement.setDate(
                        4,
                        java.sql.Date.valueOf(enrollment.getEndDate())
                );
            }

            statement.setString(5, enrollment.getStatus());
            statement.setInt(6, enrollment.getEnrollmentId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.out.println("Error updating enrollment.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    public boolean deleteEnrollment(int enrollmentId) {

        String sql =
                "DELETE FROM enrollments WHERE enrollment_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, enrollmentId);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting enrollment.");
            System.out.println(e.getMessage());

            return false;
        }
    }
}