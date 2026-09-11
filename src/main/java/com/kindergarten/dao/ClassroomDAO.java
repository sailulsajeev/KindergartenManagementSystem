package com.kindergarten.dao;

import com.kindergarten.model.Classroom;
import com.kindergarten.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDAO {

    public boolean addClassroom(Classroom classroom) {

        String sql = "INSERT INTO classes " +
                "(class_name, room_number, capacity, lead_teacher_id) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, classroom.getClassName());
            statement.setString(2, classroom.getRoomNumber());
            statement.setInt(3, classroom.getCapacity());

            if (classroom.getLeadTeacherId() == null) {
                statement.setNull(4, Types.INTEGER);
            } else {
                statement.setInt(4, classroom.getLeadTeacherId());
            }

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println("Error adding classroom.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    public List<Classroom> getAllClassrooms() {

        List<Classroom> classrooms = new ArrayList<>();

        String sql = "SELECT * FROM classes";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Integer leadTeacherId = null;

                int teacherId =
                        resultSet.getInt("lead_teacher_id");

                if (!resultSet.wasNull()) {
                    leadTeacherId = teacherId;
                }

                Classroom classroom = new Classroom(
                        resultSet.getInt("class_id"),
                        resultSet.getString("class_name"),
                        resultSet.getString("room_number"),
                        resultSet.getInt("capacity"),
                        leadTeacherId
                );

                classrooms.add(classroom);
            }

        } catch (SQLException e) {

            System.out.println("Error retrieving classrooms.");
            System.out.println(e.getMessage());
        }

        return classrooms;
    }

    public boolean updateClassroom(Classroom classroom) {

        String sql = "UPDATE classes " +
                "SET class_name = ?, room_number = ?, " +
                "capacity = ?, lead_teacher_id = ? " +
                "WHERE class_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, classroom.getClassName());
            statement.setString(2, classroom.getRoomNumber());
            statement.setInt(3, classroom.getCapacity());

            if (classroom.getLeadTeacherId() == null) {
                statement.setNull(4, Types.INTEGER);
            } else {
                statement.setInt(4, classroom.getLeadTeacherId());
            }

            statement.setInt(5, classroom.getClassId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.out.println("Error updating classroom.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    public boolean deleteClassroom(int classId) {

        String sql =
                "DELETE FROM classes WHERE class_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, classId);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting classroom.");
            System.out.println(e.getMessage());

            return false;
        }
    }
}