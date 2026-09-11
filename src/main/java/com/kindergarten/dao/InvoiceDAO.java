package com.kindergarten.dao;

import com.kindergarten.model.Invoice;
import com.kindergarten.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    // =========================
    // ADD INVOICE
    // =========================

    public boolean addInvoice(Invoice invoice) {

        String sql = "INSERT INTO invoices " +
                "(student_id, issue_date, due_date, amount_due, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    invoice.getStudentId()
            );

            statement.setDate(
                    2,
                    java.sql.Date.valueOf(invoice.getIssueDate())
            );

            statement.setDate(
                    3,
                    java.sql.Date.valueOf(invoice.getDueDate())
            );

            statement.setBigDecimal(
                    4,
                    invoice.getAmountDue()
            );

            statement.setString(
                    5,
                    invoice.getStatus()
            );

            int rowsInserted =
                    statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println("Error adding invoice.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    // =========================
    // GET ALL INVOICES
    // =========================

    public List<Invoice> getAllInvoices() {

        List<Invoice> invoices =
                new ArrayList<>();

        String sql =
                "SELECT * FROM invoices";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Invoice invoice =
                        new Invoice(
                                resultSet.getInt("invoice_id"),
                                resultSet.getInt("student_id"),
                                resultSet.getDate("issue_date").toLocalDate(),
                                resultSet.getDate("due_date").toLocalDate(),
                                resultSet.getBigDecimal("amount_due"),
                                resultSet.getString("status")
                        );

                invoices.add(invoice);
            }

        } catch (SQLException e) {

            System.out.println("Error retrieving invoices.");
            System.out.println(e.getMessage());
        }

        return invoices;
    }

    // =========================
    // UPDATE INVOICE
    // =========================

    public boolean updateInvoice(Invoice invoice) {

        String sql = "UPDATE invoices " +
                "SET student_id = ?, issue_date = ?, due_date = ?, " +
                "amount_due = ?, status = ? " +
                "WHERE invoice_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    invoice.getStudentId()
            );

            statement.setDate(
                    2,
                    java.sql.Date.valueOf(invoice.getIssueDate())
            );

            statement.setDate(
                    3,
                    java.sql.Date.valueOf(invoice.getDueDate())
            );

            statement.setBigDecimal(
                    4,
                    invoice.getAmountDue()
            );

            statement.setString(
                    5,
                    invoice.getStatus()
            );

            statement.setInt(
                    6,
                    invoice.getInvoiceId()
            );

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.out.println("Error updating invoice.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    // =========================
    // DELETE INVOICE
    // =========================

    public boolean deleteInvoice(int invoiceId) {

        String sql =
                "DELETE FROM invoices WHERE invoice_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    invoiceId
            );

            int rowsDeleted =
                    statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting invoice.");
            System.out.println(e.getMessage());

            return false;
        }
    }
}