package com.kindergarten.dao;

import com.kindergarten.model.Guardian;
import com.kindergarten.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuardianDAO {

    // CREATE - Add a guardian
    public boolean addGuardian(Guardian guardian) {

        String sql = "INSERT INTO guardians " +
                "(first_name, last_name, phone_number, email, address) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, guardian.getFirstName());
            statement.setString(2, guardian.getLastName());
            statement.setString(3, guardian.getPhoneNumber());
            statement.setString(4, guardian.getEmail());
            statement.setString(5, guardian.getAddress());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error adding guardian.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    // READ - Get all guardians
    public List<Guardian> getAllGuardians() {

        List<Guardian> guardians = new ArrayList<>();

        String sql = "SELECT * FROM guardians";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Guardian guardian = new Guardian(
                        resultSet.getInt("guardian_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getString("address")
                );

                guardians.add(guardian);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving guardians.");
            System.out.println(e.getMessage());
        }

        return guardians;
    }

    // UPDATE - Update guardian
    public boolean updateGuardian(Guardian guardian) {

        String sql = "UPDATE guardians " +
                "SET first_name = ?, last_name = ?, phone_number = ?, " +
                "email = ?, address = ? " +
                "WHERE guardian_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, guardian.getFirstName());
            statement.setString(2, guardian.getLastName());
            statement.setString(3, guardian.getPhoneNumber());
            statement.setString(4, guardian.getEmail());
            statement.setString(5, guardian.getAddress());
            statement.setInt(6, guardian.getGuardianId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating guardian.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    // DELETE - Delete guardian by ID
    public boolean deleteGuardian(int guardianId) {

        String sql = "DELETE FROM guardians WHERE guardian_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, guardianId);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting guardian.");
            System.out.println(e.getMessage());
            return false;
        }
    }
}