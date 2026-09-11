package com.kindergarten.dao;

import com.kindergarten.model.Staff;
import com.kindergarten.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    // CREATE - Add staff
    public boolean addStaff(Staff staff) {

        String sql = "INSERT INTO staff " +
                "(first_name, last_name, role, email, phone_number, salary, hire_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getLastName());
            statement.setString(3, staff.getRole());
            statement.setString(4, staff.getEmail());
            statement.setString(5, staff.getPhoneNumber());
            statement.setDouble(6, staff.getSalary());
            statement.setDate(7, java.sql.Date.valueOf(staff.getHireDate()));

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error adding staff.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    // READ - Get all staff
    public List<Staff> getAllStaff() {

        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Staff staff = new Staff(
                        resultSet.getInt("staff_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("role"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getDouble("salary"),
                        resultSet.getDate("hire_date").toLocalDate()
                );

                staffList.add(staff);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving staff.");
            System.out.println(e.getMessage());
        }

        return staffList;
    }

    // UPDATE - Update staff
    public boolean updateStaff(Staff staff) {

        String sql = "UPDATE staff " +
                "SET first_name = ?, last_name = ?, role = ?, email = ?, " +
                "phone_number = ?, salary = ?, hire_date = ? " +
                "WHERE staff_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getLastName());
            statement.setString(3, staff.getRole());
            statement.setString(4, staff.getEmail());
            statement.setString(5, staff.getPhoneNumber());
            statement.setDouble(6, staff.getSalary());
            statement.setDate(7, java.sql.Date.valueOf(staff.getHireDate()));
            statement.setInt(8, staff.getStaffId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating staff.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    // DELETE - Delete staff by ID
    public boolean deleteStaff(int staffId) {

        String sql = "DELETE FROM staff WHERE staff_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, staffId);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting staff.");
            System.out.println(e.getMessage());
            return false;
        }
    }
}