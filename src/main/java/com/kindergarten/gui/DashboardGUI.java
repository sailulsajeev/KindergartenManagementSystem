package com.kindergarten.gui;

import com.kindergarten.dao.ClassroomDAO;
import com.kindergarten.dao.InvoiceDAO;
import com.kindergarten.dao.StaffDAO;
import com.kindergarten.dao.StudentDAO;

import com.kindergarten.model.Invoice;
import com.kindergarten.util.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class DashboardGUI extends JFrame {

    private JLabel studentsCountLabel;
    private JLabel staffCountLabel;
    private JLabel classesCountLabel;
    private JLabel unpaidInvoicesCountLabel;

    private final StudentDAO studentDAO;
    private final StaffDAO staffDAO;
    private final ClassroomDAO classroomDAO;
    private final InvoiceDAO invoiceDAO;

    public DashboardGUI() {

        studentDAO = new StudentDAO();
        staffDAO = new StaffDAO();
        classroomDAO = new ClassroomDAO();
        invoiceDAO = new InvoiceDAO();

        setTitle("Kindergarten Management System");
        setSize(900, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(0, 20)
                );

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel =
                new JPanel();

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "Kindergarten Management System"
                );

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Dashboard Overview"
                );

        subtitleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        headerPanel.add(titleLabel);

        headerPanel.add(
                Box.createVerticalStrut(8)
        );

        headerPanel.add(subtitleLabel);

        // =========================
        // SUMMARY CARDS
        // =========================

        JPanel summaryPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                15
                        )
                );

        studentsCountLabel =
                new JLabel("0");

        staffCountLabel =
                new JLabel("0");

        classesCountLabel =
                new JLabel("0");

        unpaidInvoicesCountLabel =
                new JLabel("0");

        JPanel studentsCard =
                createSummaryCard(
                        "Students",
                        studentsCountLabel
                );

        JPanel staffCard =
                createSummaryCard(
                        "Staff",
                        staffCountLabel
                );

        JPanel classesCard =
                createSummaryCard(
                        "Classes",
                        classesCountLabel
                );

        JPanel invoicesCard =
                createSummaryCard(
                        "Unpaid Invoices",
                        unpaidInvoicesCountLabel
                );

        summaryPanel.add(studentsCard);
        summaryPanel.add(staffCard);
        summaryPanel.add(classesCard);
        summaryPanel.add(invoicesCard);

        headerPanel.add(
                Box.createVerticalStrut(20)
        );

        headerPanel.add(summaryPanel);

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // BUTTON GRID
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                20,
                                20
                        )
                );

        JButton studentsButton =
                new JButton("Students");

        JButton guardiansButton =
                new JButton("Guardians");

        JButton staffButton =
                new JButton("Staff");

        JButton classesButton =
                new JButton("Classes");

        JButton enrollmentsButton =
                new JButton("Enrollments");

        JButton attendanceButton =
                new JButton("Attendance");

        JButton invoicesButton =
                new JButton("Invoices");

        JButton paymentsButton =
                new JButton("Payments");

        JButton guardianStudentButton =
                new JButton(
                        "Guardian-Student Links"
                );

        JButton exitButton =
                new JButton("Exit");

        Font buttonFont =
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                );

        studentsButton.setFont(buttonFont);
        guardiansButton.setFont(buttonFont);
        staffButton.setFont(buttonFont);
        classesButton.setFont(buttonFont);
        enrollmentsButton.setFont(buttonFont);
        attendanceButton.setFont(buttonFont);
        invoicesButton.setFont(buttonFont);
        paymentsButton.setFont(buttonFont);
        guardianStudentButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        buttonPanel.add(studentsButton);
        buttonPanel.add(guardiansButton);
        buttonPanel.add(staffButton);
        buttonPanel.add(classesButton);
        buttonPanel.add(enrollmentsButton);
        buttonPanel.add(attendanceButton);
        buttonPanel.add(invoicesButton);
        buttonPanel.add(paymentsButton);
        buttonPanel.add(guardianStudentButton);
        buttonPanel.add(exitButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        studentsButton.addActionListener(
                e ->
                        new StudentManagementGUI()
        );

        guardiansButton.addActionListener(
                e ->
                        new GuardianManagementGUI()
        );

        staffButton.addActionListener(
                e ->
                        new StaffManagementGUI()
        );

        classesButton.addActionListener(
                e ->
                        new ClassroomManagementGUI()
        );

        enrollmentsButton.addActionListener(
                e ->
                        new EnrollmentManagementGUI()
        );

        attendanceButton.addActionListener(
                e ->
                        new AttendanceManagementGUI()
        );

        invoicesButton.addActionListener(
                e ->
                        new InvoiceManagementGUI()
        );

        paymentsButton.addActionListener(
                e ->
                        new PaymentManagementGUI()
        );

        guardianStudentButton.addActionListener(
                e ->
                        new GuardianStudentBridgeGUI()
        );

        exitButton.addActionListener(
                e ->
                        System.exit(0)
        );

        add(mainPanel);

        // Load dashboard numbers
        loadDashboardCounts();

        setVisible(true);
    }

    // =========================
    // CREATE SUMMARY CARD
    // =========================

    private JPanel createSummaryCard(
            String title,
            JLabel countLabel
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        countLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        countLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(countLabel);

        return card;
    }

    // =========================
    // LOAD DASHBOARD COUNTS
    // =========================

    private void loadDashboardCounts() {

        /*
         * First check whether the application can
         * connect to the MySQL database.
         *
         * This prevents a database connection failure
         * from being displayed incorrectly as 0 records.
         */
        try (
                Connection connection =
                        DBConnection.getConnection()
        ) {

            // Connection successful.
            // Continue loading dashboard information.

        } catch (Exception e) {

            showDatabaseUnavailable();

            return;
        }

        try {

            int totalStudents =
                    studentDAO
                            .getAllStudents()
                            .size();

            int totalStaff =
                    staffDAO
                            .getAllStaff()
                            .size();

            int totalClasses =
                    classroomDAO
                            .getAllClassrooms()
                            .size();

            List<Invoice> invoices =
                    invoiceDAO
                            .getAllInvoices();

            int unpaidInvoices = 0;

            for (Invoice invoice : invoices) {

                if (invoice.getStatus()
                        .equalsIgnoreCase(
                                "Unpaid"
                        )) {

                    unpaidInvoices++;
                }
            }

            studentsCountLabel.setText(
                    String.valueOf(
                            totalStudents
                    )
            );

            staffCountLabel.setText(
                    String.valueOf(
                            totalStaff
                    )
            );

            classesCountLabel.setText(
                    String.valueOf(
                            totalClasses
                    )
            );

            unpaidInvoicesCountLabel.setText(
                    String.valueOf(
                            unpaidInvoices
                    )
            );

        } catch (Exception e) {

            showDatabaseUnavailable();
        }
    }

    // =========================
    // DATABASE FALLBACK
    // =========================

    private void showDatabaseUnavailable() {

        studentsCountLabel.setText("?");
        staffCountLabel.setText("?");
        classesCountLabel.setText("?");
        unpaidInvoicesCountLabel.setText("?");

        JOptionPane.showMessageDialog(
                this,
                "Database connection is currently unavailable.\n" +
                        "Dashboard statistics could not be loaded.\n" +
                        "Please check the database connection and try again.",
                "Database Connection Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}