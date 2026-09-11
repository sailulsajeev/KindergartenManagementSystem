package com.kindergarten.gui;

import com.kindergarten.dao.StaffDAO;
import com.kindergarten.model.Staff;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class StaffManagementGUI extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;

    private JComboBox<String> roleComboBox;

    private JTextField emailField;
    private JTextField phoneNumberField;
    private JTextField salaryField;

    private JDateChooser hireDateChooser;

    private JTable staffTable;
    private DefaultTableModel tableModel;

    private final StaffDAO staffDAO;

    public StaffManagementGUI() {

        staffDAO = new StaffDAO();

        setTitle("Staff Management");
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(0, 20));

        mainPanel.setBorder(
                new EmptyBorder(20, 25, 20, 25)
        );

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel();

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel("Staff Management");

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        titleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Add, update and manage kindergarten staff"
                );

        subtitleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        subtitleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // FORM
        // =========================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(0, 20)
                );

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                4,
                                12,
                                12
                        )
                );

        firstNameField =
                new JTextField();

        lastNameField =
                new JTextField();

        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "Teacher",
                                "Teaching Assistant",
                                "Manager",
                                "Administrator"
                        }
                );

        emailField =
                new JTextField();

        phoneNumberField =
                new JTextField();

        salaryField =
                new JTextField();

        hireDateChooser =
                new JDateChooser();

        hireDateChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        // Default Hire Date = today
        hireDateChooser.setDate(
                new Date()
        );

        formPanel.add(
                new JLabel("First Name")
        );

        formPanel.add(
                firstNameField
        );

        formPanel.add(
                new JLabel("Last Name")
        );

        formPanel.add(
                lastNameField
        );

        formPanel.add(
                new JLabel("Role")
        );

        formPanel.add(
                roleComboBox
        );

        formPanel.add(
                new JLabel("Email")
        );

        formPanel.add(
                emailField
        );

        formPanel.add(
                new JLabel("Phone Number")
        );

        formPanel.add(
                phoneNumberField
        );

        formPanel.add(
                new JLabel("Salary")
        );

        formPanel.add(
                salaryField
        );

        formPanel.add(
                new JLabel("Hire Date")
        );

        formPanel.add(
                hireDateChooser
        );

        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        centerPanel.add(
                formPanel,
                BorderLayout.NORTH
        );

        // =========================
        // BUTTONS
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                0
                        )
                );

        JButton addButton =
                new JButton("Add Staff");

        JButton updateButton =
                new JButton("Update Staff");

        JButton deleteButton =
                new JButton("Delete Staff");

        JButton clearButton =
                new JButton("Clear");

        Dimension buttonSize =
                new Dimension(160, 35);

        addButton.setPreferredSize(buttonSize);
        updateButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        clearButton.setPreferredSize(buttonSize);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // =========================
        // TABLE
        // =========================

        String[] columnNames = {
                "Staff ID",
                "First Name",
                "Last Name",
                "Role",
                "Email",
                "Phone Number",
                "Salary",
                "Hire Date"
        };

        tableModel =
                new DefaultTableModel(
                        columnNames,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        staffTable =
                new JTable(tableModel);

        staffTable.setRowHeight(28);

        staffTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        staffTable
                );

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(0, 10)
                );

        JLabel tableTitle =
                new JLabel("Staff Records");

        tableTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        tablePanel.add(
                tableTitle,
                BorderLayout.NORTH
        );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JPanel lowerPanel =
                new JPanel(
                        new BorderLayout(0, 15)
                );

        lowerPanel.add(
                buttonPanel,
                BorderLayout.NORTH
        );

        lowerPanel.add(
                tablePanel,
                BorderLayout.CENTER
        );

        centerPanel.add(
                lowerPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =========================
        // BUTTON ACTIONS
        // =========================

        addButton.addActionListener(
                e -> addStaff()
        );

        updateButton.addActionListener(
                e -> updateStaff()
        );

        deleteButton.addActionListener(
                e -> deleteStaff()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        staffTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {
                                fillFieldsFromSelectedRow();
                            }
                        }
                );

        loadStaff();

        setVisible(true);
    }

    // =========================
    // ADD STAFF
    // =========================

    private void addStaff() {

        try {

            String firstName =
                    firstNameField
                            .getText()
                            .trim();

            String lastName =
                    lastNameField
                            .getText()
                            .trim();

            String email =
                    emailField
                            .getText()
                            .trim();

            String phoneNumber =
                    phoneNumberField
                            .getText()
                            .trim();

            String salaryText =
                    salaryField
                            .getText()
                            .trim();

            Date selectedHireDate =
                    hireDateChooser.getDate();

            if (firstName.isEmpty()
                    || lastName.isEmpty()
                    || salaryText.isEmpty()
                    || selectedHireDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all required staff fields."
                );

                return;
            }

            // First name validation
            if (!isValidName(firstName)) {

                JOptionPane.showMessageDialog(
                        this,
                        "First Name can only contain letters, spaces, hyphens and apostrophes."
                );

                return;
            }

            // Last name validation
            if (!isValidName(lastName)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Last Name can only contain letters, spaces, hyphens and apostrophes."
                );

                return;
            }

            // Email validation
            if (!isValidEmail(email)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid email address.\nExample: name@email.com"
                );

                return;
            }

            // Phone validation
            if (!isValidPhoneNumber(phoneNumber)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Phone Number can only contain numbers and common phone symbols.\nExample: +44 7123 456789"
                );

                return;
            }

            double salary =
                    Double.parseDouble(
                            salaryText
                    );

            if (salary <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Salary must be greater than 0."
                );

                return;
            }

            LocalDate hireDate =
                    selectedHireDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            String role =
                    roleComboBox
                            .getSelectedItem()
                            .toString();

            Staff staff =
                    new Staff(
                            0,
                            firstName,
                            lastName,
                            role,
                            email,
                            phoneNumber,
                            salary,
                            hireDate
                    );

            boolean added =
                    staffDAO.addStaff(
                            staff
                    );

            if (added) {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff member added successfully!"
                );

                clearFields();
                loadStaff();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff member could not be added."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Salary must be a valid number."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the information you entered."
            );
        }
    }

    // =========================
    // UPDATE STAFF
    // =========================

    private void updateStaff() {

        int selectedRow =
                staffTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a staff record first."
            );

            return;
        }

        try {

            String firstName =
                    firstNameField
                            .getText()
                            .trim();

            String lastName =
                    lastNameField
                            .getText()
                            .trim();

            String email =
                    emailField
                            .getText()
                            .trim();

            String phoneNumber =
                    phoneNumberField
                            .getText()
                            .trim();

            String salaryText =
                    salaryField
                            .getText()
                            .trim();

            Date selectedHireDate =
                    hireDateChooser.getDate();

            if (firstName.isEmpty()
                    || lastName.isEmpty()
                    || salaryText.isEmpty()
                    || selectedHireDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all required staff fields."
                );

                return;
            }

            if (!isValidName(firstName)) {

                JOptionPane.showMessageDialog(
                        this,
                        "First Name can only contain letters, spaces, hyphens and apostrophes."
                );

                return;
            }

            if (!isValidName(lastName)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Last Name can only contain letters, spaces, hyphens and apostrophes."
                );

                return;
            }

            if (!isValidEmail(email)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid email address.\nExample: name@email.com"
                );

                return;
            }

            if (!isValidPhoneNumber(phoneNumber)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Phone Number can only contain numbers and common phone symbols.\nExample: +44 7123 456789"
                );

                return;
            }

            double salary =
                    Double.parseDouble(
                            salaryText
                    );

            if (salary <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Salary must be greater than 0."
                );

                return;
            }

            int staffId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );

            LocalDate hireDate =
                    selectedHireDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            String role =
                    roleComboBox
                            .getSelectedItem()
                            .toString();

            Staff staff =
                    new Staff(
                            staffId,
                            firstName,
                            lastName,
                            role,
                            email,
                            phoneNumber,
                            salary,
                            hireDate
                    );

            boolean updated =
                    staffDAO.updateStaff(
                            staff
                    );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff member updated successfully!"
                );

                clearFields();
                loadStaff();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff member could not be updated."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Salary must be a valid number."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the information you entered."
            );
        }
    }

    // =========================
    // DELETE STAFF
    // =========================

    private void deleteStaff() {

        int selectedRow =
                staffTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a staff record first."
            );

            return;
        }

        int staffId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        0
                                )
                                .toString()
                );

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this staff member?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            boolean deleted =
                    staffDAO.deleteStaff(
                            staffId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff member deleted successfully!"
                );

                clearFields();
                loadStaff();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff member could not be deleted."
                );
            }
        }
    }

    // =========================
    // LOAD STAFF
    // =========================

    private void loadStaff() {

        tableModel.setRowCount(0);

        List<Staff> staffList =
                staffDAO.getAllStaff();

        for (Staff staff : staffList) {

            Object[] row = {

                    staff.getStaffId(),

                    staff.getFirstName(),

                    staff.getLastName(),

                    staff.getRole(),

                    staff.getEmail(),

                    staff.getPhoneNumber(),

                    staff.getSalary(),

                    staff.getHireDate()
            };

            tableModel.addRow(row);
        }
    }

    // =========================
    // SELECT TABLE ROW
    // =========================

    private void fillFieldsFromSelectedRow() {

        int selectedRow =
                staffTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        firstNameField.setText(
                valueToString(
                        tableModel.getValueAt(
                                selectedRow,
                                1
                        )
                )
        );

        lastNameField.setText(
                valueToString(
                        tableModel.getValueAt(
                                selectedRow,
                                2
                        )
                )
        );

        roleComboBox.setSelectedItem(
                valueToString(
                        tableModel.getValueAt(
                                selectedRow,
                                3
                        )
                )
        );

        emailField.setText(
                valueToString(
                        tableModel.getValueAt(
                                selectedRow,
                                4
                        )
                )
        );

        phoneNumberField.setText(
                valueToString(
                        tableModel.getValueAt(
                                selectedRow,
                                5
                        )
                )
        );

        salaryField.setText(
                valueToString(
                        tableModel.getValueAt(
                                selectedRow,
                                6
                        )
                )
        );

        Object hireDateObject =
                tableModel.getValueAt(
                        selectedRow,
                        7
                );

        if (hireDateObject == null
                || hireDateObject.toString().isBlank()) {

            hireDateChooser.setDate(null);

        } else {

            LocalDate hireDate =
                    LocalDate.parse(
                            hireDateObject.toString()
                    );

            Date date =
                    Date.from(
                            hireDate
                                    .atStartOfDay(
                                            ZoneId.systemDefault()
                                    )
                                    .toInstant()
                    );

            hireDateChooser.setDate(
                    date
            );
        }
    }

    // =========================
    // NAME VALIDATION
    // =========================

    private boolean isValidName(String name) {

        return name.matches(
                "^[A-Za-zÀ-ÿ' -]+$"
        );
    }

    // =========================
    // EMAIL VALIDATION
    // =========================

    private boolean isValidEmail(String email) {

        // Email is optional
        if (email.isEmpty()) {
            return true;
        }

        return email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        );
    }

    // =========================
    // PHONE VALIDATION
    // =========================

    private boolean isValidPhoneNumber(
            String phoneNumber
    ) {

        // Phone is optional
        if (phoneNumber.isEmpty()) {
            return true;
        }

        return phoneNumber.matches(
                "^[0-9+() -]+$"
        );
    }

    // =========================
    // SAFE TEXT
    // =========================

    private String valueToString(
            Object value
    ) {

        if (value == null) {
            return "";
        }

        return value.toString();
    }

    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        firstNameField.setText("");

        lastNameField.setText("");

        roleComboBox.setSelectedIndex(0);

        emailField.setText("");

        phoneNumberField.setText("");

        salaryField.setText("");

        // Hire Date resets to today
        hireDateChooser.setDate(
                new Date()
        );

        staffTable.clearSelection();
    }
}