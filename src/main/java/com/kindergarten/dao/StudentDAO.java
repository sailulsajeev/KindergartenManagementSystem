package com.kindergarten.dao;

import com.kindergarten.model.Student;
import com.kindergarten.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // CREATE - Add a new student
    public boolean addStudent(Student student) {

        String sql = "INSERT INTO students " +
                "(first_name, last_name, date_of_birth, status) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setDate(3, java.sql.Date.valueOf(student.getDateOfBirth()));
            statement.setString(4, student.getStatus());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error adding student.");
            System.out.println(e.getMessage());

            return false;
        }
    }


    // READ - Get all students
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Student student = new Student(
                        resultSet.getInt("student_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDate("date_of_birth").toLocalDate(),
                        resultSet.getString("status")
                );

                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving students.");
            System.out.println(e.getMessage());
        }

        return students;
    }


    // UPDATE - Update an existing student
    public boolean updateStudent(Student student) {

        String sql = "UPDATE students " +
                "SET first_name = ?, last_name = ?, date_of_birth = ?, status = ? " +
                "WHERE student_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setDate(3, java.sql.Date.valueOf(student.getDateOfBirth()));
            statement.setString(4, student.getStatus());
            statement.setInt(5, student.getStudentId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating student.");
            System.out.println(e.getMessage());

            return false;
        }
    }


    // DELETE - Delete a student by ID
    public boolean deleteStudent(int studentId) {

        String sql = "DELETE FROM students WHERE student_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, studentId);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting student.");
            System.out.println(e.getMessage());

            return false;
        }
    }
}