package com.kindergarten.gui;

import com.kindergarten.dao.GuardianDAO;
import com.kindergarten.model.Guardian;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class GuardianManagementGUI extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneNumberField;
    private JTextField emailField;
    private JTextField addressField;

    private JTable guardianTable;
    private DefaultTableModel tableModel;

    private final GuardianDAO guardianDAO;

    public GuardianManagementGUI() {

        guardianDAO = new GuardianDAO();

        setTitle("Guardian Management");
        setSize(950, 650);
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
                new JLabel("Guardian Management");

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
                        "Add, update and manage student guardians"
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
                                3,
                                4,
                                12,
                                12
                        )
                );

        firstNameField =
                new JTextField();

        lastNameField =
                new JTextField();

        phoneNumberField =
                new JTextField();

        emailField =
                new JTextField();

        addressField =
                new JTextField();

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
                new JLabel("Phone Number")
        );

        formPanel.add(
                phoneNumberField
        );

        formPanel.add(
                new JLabel("Email")
        );

        formPanel.add(
                emailField
        );

        formPanel.add(
                new JLabel("Address")
        );

        formPanel.add(
                addressField
        );

        formPanel.add(
                new JLabel("")
        );

        formPanel.add(
                new JLabel("")
        );

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
                new JButton("Add Guardian");

        JButton updateButton =
                new JButton("Update Guardian");

        JButton deleteButton =
                new JButton("Delete Guardian");

        JButton clearButton =
                new JButton("Clear");

        Dimension buttonSize =
                new Dimension(170, 35);

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
                "Guardian ID",
                "First Name",
                "Last Name",
                "Phone Number",
                "Email",
                "Address"
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

        guardianTable =
                new JTable(tableModel);

        guardianTable.setRowHeight(28);

        guardianTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        guardianTable
                );

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(0, 10)
                );

        JLabel tableTitle =
                new JLabel("Guardian Records");

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
                e -> addGuardian()
        );

        updateButton.addActionListener(
                e -> updateGuardian()
        );

        deleteButton.addActionListener(
                e -> deleteGuardian()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        guardianTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {
                                fillFieldsFromSelectedRow();
                            }
                        }
                );

        loadGuardians();

        setVisible(true);
    }

    // =========================
    // ADD GUARDIAN
    // =========================

    private void addGuardian() {

        try {

            String firstName =
                    firstNameField
                            .getText()
                            .trim();

            String lastName =
                    lastNameField
                            .getText()
                            .trim();

            String phoneNumber =
                    phoneNumberField
                            .getText()
                            .trim();

            String email =
                    emailField
                            .getText()
                            .trim();

            String address =
                    addressField
                            .getText()
                            .trim();

            // Required fields
            if (firstName.isEmpty()
                    || lastName.isEmpty()
                    || phoneNumber.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete First Name, Last Name and Phone Number."
                );

                return;
            }

            // First Name validation
            if (!isValidName(firstName)) {

                JOptionPane.showMessageDialog(
                        this,
                        "First Name can only contain letters, spaces, hyphens and apostrophes."
                );

                return;
            }

            // Last Name validation
            if (!isValidName(lastName)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Last Name can only contain letters, spaces, hyphens and apostrophes."
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

            // Email validation
            if (!isValidEmail(email)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid email address.\nExample: name@email.com"
                );

                return;
            }

            Guardian guardian =
                    new Guardian(
                            0,
                            firstName,
                            lastName,
                            phoneNumber,
                            email,
                            address
                    );

            boolean added =
                    guardianDAO.addGuardian(
                            guardian
                    );

            if (added) {

                JOptionPane.showMessageDialog(
                        this,
                        "Guardian added successfully!"
                );

                clearFields();
                loadGuardians();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Guardian could not be added."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the information you entered."
            );
        }
    }

    // =========================
    // UPDATE GUARDIAN
    // =========================

    private void updateGuardian() {

        int selectedRow =
                guardianTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a guardian record first."
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

            String phoneNumber =
                    phoneNumberField
                            .getText()
                            .trim();

            String email =
                    emailField
                            .getText()
                            .trim();

            String address =
                    addressField
                            .getText()
                            .trim();

            if (firstName.isEmpty()
                    || lastName.isEmpty()
                    || phoneNumber.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete First Name, Last Name and Phone Number."
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

            if (!isValidPhoneNumber(phoneNumber)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Phone Number can only contain numbers and common phone symbols.\nExample: +44 7123 456789"
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

            int guardianId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );

            Guardian guardian =
                    new Guardian(
                            guardianId,
                            firstName,
                            lastName,
                            phoneNumber,
                            email,
                            address
                    );

            boolean updated =
                    guardianDAO.updateGuardian(
                            guardian
                    );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Guardian updated successfully!"
                );

                clearFields();
                loadGuardians();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Guardian could not be updated."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the information you entered."
            );
        }
    }

    // =========================
    // DELETE GUARDIAN
    // =========================

    private void deleteGuardian() {

        int selectedRow =
                guardianTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a guardian record first."
            );

            return;
        }

        int guardianId =
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
                        "Are you sure you want to delete this guardian?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            boolean deleted =
                    guardianDAO.deleteGuardian(
                            guardianId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Guardian deleted successfully!"
                );

                clearFields();
                loadGuardians();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Guardian could not be deleted."
                );
            }
        }
    }

    // =========================
    // LOAD GUARDIANS
    // =========================

    private void loadGuardians() {

        tableModel.setRowCount(0);

        List<Guardian> guardianList =
                guardianDAO.getAllGuardians();

        for (Guardian guardian : guardianList) {

            Object[] row = {

                    guardian.getGuardianId(),

                    guardian.getFirstName(),

                    guardian.getLastName(),

                    guardian.getPhoneNumber(),

                    guardian.getEmail(),

                    guardian.getAddress()
            };

            tableModel.addRow(row);
        }
    }

    // =========================
    // SELECT TABLE ROW
    // =========================

    private void fillFieldsFromSelectedRow() {

        int selectedRow =
                guardianTable.getSelectedRow();

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

        phoneNumberField.setText(
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

        addressField.setText(
                valueToString(
                        tableModel.getValueAt(
                                selectedRow,
                                5
                        )
                )
        );
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
    // PHONE VALIDATION
    // =========================

    private boolean isValidPhoneNumber(
            String phoneNumber
    ) {

        return phoneNumber.matches(
                "^[0-9+() -]+$"
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
        phoneNumberField.setText("");
        emailField.setText("");
        addressField.setText("");

        guardianTable.clearSelection();
    }
}