package com.kindergarten.dao;

import com.kindergarten.model.Payment;
import com.kindergarten.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // =========================
    // ADD PAYMENT
    // =========================

    public boolean addPayment(Payment payment) {

        String sql = "INSERT INTO payments " +
                "(invoice_id, payment_date, amount_paid, payment_method, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    payment.getInvoiceId()
            );

            statement.setDate(
                    2,
                    java.sql.Date.valueOf(payment.getPaymentDate())
            );

            statement.setBigDecimal(
                    3,
                    payment.getAmountPaid()
            );

            statement.setString(
                    4,
                    payment.getPaymentMethod()
            );

            statement.setString(
                    5,
                    payment.getStatus()
            );

            int rowsInserted =
                    statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println("Error adding payment.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    // =========================
    // GET ALL PAYMENTS
    // =========================

    public List<Payment> getAllPayments() {

        List<Payment> payments =
                new ArrayList<>();

        String sql =
                "SELECT * FROM payments";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Payment payment =
                        new Payment(
                                resultSet.getInt("payment_id"),
                                resultSet.getInt("invoice_id"),
                                resultSet.getDate("payment_date").toLocalDate(),
                                resultSet.getBigDecimal("amount_paid"),
                                resultSet.getString("payment_method"),
                                resultSet.getString("status")
                        );

                payments.add(payment);
            }

        } catch (SQLException e) {

            System.out.println("Error retrieving payments.");
            System.out.println(e.getMessage());
        }

        return payments;
    }

    // =========================
    // UPDATE PAYMENT
    // =========================

    public boolean updatePayment(Payment payment) {

        String sql = "UPDATE payments " +
                "SET invoice_id = ?, payment_date = ?, amount_paid = ?, " +
                "payment_method = ?, status = ? " +
                "WHERE payment_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    payment.getInvoiceId()
            );

            statement.setDate(
                    2,
                    java.sql.Date.valueOf(payment.getPaymentDate())
            );

            statement.setBigDecimal(
                    3,
                    payment.getAmountPaid()
            );

            statement.setString(
                    4,
                    payment.getPaymentMethod()
            );

            statement.setString(
                    5,
                    payment.getStatus()
            );

            statement.setInt(
                    6,
                    payment.getPaymentId()
            );

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.out.println("Error updating payment.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    // =========================
    // DELETE PAYMENT
    // =========================

    public boolean deletePayment(int paymentId) {

        String sql =
                "DELETE FROM payments WHERE payment_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    paymentId
            );

            int rowsDeleted =
                    statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting payment.");
            System.out.println(e.getMessage());

            return false;
        }
    }
}