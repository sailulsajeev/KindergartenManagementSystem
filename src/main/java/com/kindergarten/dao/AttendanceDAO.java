package com.kindergarten.dao;

import com.kindergarten.model.Attendance;
import com.kindergarten.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    // =========================
    // ADD ATTENDANCE
    // =========================

    public boolean addAttendance(Attendance attendance) {

        String sql = "INSERT INTO attendance " +
                "(student_id, attendance_date, status, notes) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    attendance.getStudentId()
            );

            statement.setDate(
                    2,
                    java.sql.Date.valueOf(
                            attendance.getAttendanceDate()
                    )
            );

            statement.setString(
                    3,
                    attendance.getStatus()
            );

            statement.setString(
                    4,
                    attendance.getNotes()
            );

            int rowsInserted =
                    statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println("Error adding attendance.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    // =========================
    // GET ALL ATTENDANCE
    // =========================

    public List<Attendance> getAllAttendance() {

        List<Attendance> attendanceList =
                new ArrayList<>();

        String sql =
                "SELECT * FROM attendance";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Attendance attendance =
                        new Attendance(
                                resultSet.getInt("attendance_id"),
                                resultSet.getInt("student_id"),
                                resultSet.getDate("attendance_date")
                                        .toLocalDate(),
                                resultSet.getString("status"),
                                resultSet.getString("notes")
                        );

                attendanceList.add(attendance);
            }

        } catch (SQLException e) {

            System.out.println("Error retrieving attendance.");
            System.out.println(e.getMessage());
        }

        return attendanceList;
    }

    // =========================
    // UPDATE ATTENDANCE
    // =========================

    public boolean updateAttendance(Attendance attendance) {

        String sql = "UPDATE attendance " +
                "SET student_id = ?, attendance_date = ?, " +
                "status = ?, notes = ? " +
                "WHERE attendance_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    attendance.getStudentId()
            );

            statement.setDate(
                    2,
                    java.sql.Date.valueOf(
                            attendance.getAttendanceDate()
                    )
            );

            statement.setString(
                    3,
                    attendance.getStatus()
            );

            statement.setString(
                    4,
                    attendance.getNotes()
            );

            statement.setInt(
                    5,
                    attendance.getAttendanceId()
            );

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.out.println("Error updating attendance.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    // =========================
    // DELETE ATTENDANCE
    // =========================

    public boolean deleteAttendance(int attendanceId) {

        String sql =
                "DELETE FROM attendance WHERE attendance_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    attendanceId
            );

            int rowsDeleted =
                    statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting attendance.");
            System.out.println(e.getMessage());

            return false;
        }
    }
}