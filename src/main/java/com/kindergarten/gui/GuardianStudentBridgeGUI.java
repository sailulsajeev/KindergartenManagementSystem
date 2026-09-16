package com.kindergarten.gui;

import com.kindergarten.dao.GuardianDAO;
import com.kindergarten.dao.GuardianStudentBridgeDAO;
import com.kindergarten.dao.StudentDAO;

import com.kindergarten.model.Guardian;
import com.kindergarten.model.GuardianStudentBridge;
import com.kindergarten.model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class GuardianStudentBridgeGUI extends JFrame {

    private JComboBox<String> guardianComboBox;
    private JComboBox<String> studentComboBox;

    private JComboBox<String> relationshipTypeComboBox;

    private JTextField emergencyPriorityField;

    private JTable relationshipTable;
    private DefaultTableModel tableModel;

    private final GuardianStudentBridgeDAO bridgeDAO;
    private final GuardianDAO guardianDAO;
    private final StudentDAO studentDAO;

    private List<Guardian> guardians;
    private List<Student> students;

    public GuardianStudentBridgeGUI() {

        bridgeDAO = new GuardianStudentBridgeDAO();
        guardianDAO = new GuardianDAO();
        studentDAO = new StudentDAO();

        setTitle("Guardian-Student Relationships");
        setSize(950, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(0, 20)
                );

        mainPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
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
                        "Guardian-Student Relationships"
                );

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
                        "Link guardians to students and manage relationship details"
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

        headerPanel.add(
                Box.createVerticalStrut(5)
        );

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
                                2,
                                4,
                                12,
                                12
                        )
                );

        guardianComboBox =
                new JComboBox<>();

        studentComboBox =
                new JComboBox<>();

        relationshipTypeComboBox =
                new JComboBox<>(
                        new String[]{
                                "Mother",
                                "Father",
                                "Guardian",
                                "Grandparent",
                                "Other"
                        }
                );

        emergencyPriorityField =
                new JTextField();

        formPanel.add(
                new JLabel("Guardian")
        );

        formPanel.add(
                guardianComboBox
        );

        formPanel.add(
                new JLabel("Student")
        );

        formPanel.add(
                studentComboBox
        );

        formPanel.add(
                new JLabel("Relationship Type")
        );

        formPanel.add(
                relationshipTypeComboBox
        );

        formPanel.add(
                new JLabel("Emergency Priority")
        );

        formPanel.add(
                emergencyPriorityField
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
                new JButton(
                        "Add Relationship"
                );

        JButton updateButton =
                new JButton(
                        "Update Relationship"
                );

        JButton deleteButton =
                new JButton(
                        "Delete Relationship"
                );

        JButton clearButton =
                new JButton(
                        "Clear"
                );

        Dimension buttonSize =
                new Dimension(
                        180,
                        35
                );

        addButton.setPreferredSize(
                buttonSize
        );

        updateButton.setPreferredSize(
                buttonSize
        );

        deleteButton.setPreferredSize(
                buttonSize
        );

        clearButton.setPreferredSize(
                buttonSize
        );

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // =========================
        // TABLE
        // =========================

        String[] columnNames = {
                "Guardian ID",
                "Guardian Name",
                "Student ID",
                "Student Name",
                "Relationship Type",
                "Emergency Priority"
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

        relationshipTable =
                new JTable(
                        tableModel
                );

        relationshipTable.setRowHeight(
                28
        );

        relationshipTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        relationshipTable
                );

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(0, 10)
                );

        JLabel tableTitle =
                new JLabel(
                        "Relationship Records"
                );

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
                e -> addRelationship()
        );

        updateButton.addActionListener(
                e -> updateRelationship()
        );

        deleteButton.addActionListener(
                e -> deleteRelationship()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        relationshipTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                fillFieldsFromSelectedRow();
                            }
                        }
                );

        loadGuardians();
        loadStudents();
        loadRelationships();

        setVisible(true);
    }

    // =========================
    // LOAD GUARDIANS
    // =========================

    private void loadGuardians() {

        guardianComboBox.removeAllItems();

        guardians =
                guardianDAO.getAllGuardians();

        for (Guardian guardian : guardians) {

            String fullName =
                    guardian.getFirstName()
                            + " "
                            + guardian.getLastName();

            guardianComboBox.addItem(
                    fullName
            );
        }

        guardianComboBox.setSelectedIndex(
                -1
        );
    }

    // =========================
    // LOAD STUDENTS
    // =========================

    private void loadStudents() {

        studentComboBox.removeAllItems();

        students =
                studentDAO.getAllStudents();

        for (Student student : students) {

            String fullName =
                    student.getFirstName()
                            + " "
                            + student.getLastName();

            studentComboBox.addItem(
                    fullName
            );
        }

        studentComboBox.setSelectedIndex(
                -1
        );
    }

    // =========================
    // ADD
    // =========================

    private void addRelationship() {

        try {

            int guardianIndex =
                    guardianComboBox
                            .getSelectedIndex();

            int studentIndex =
                    studentComboBox
                            .getSelectedIndex();

            if (guardianIndex == -1
                    || studentIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a guardian and a student."
                );

                return;
            }

            Guardian selectedGuardian =
                    guardians.get(
                            guardianIndex
                    );

            Student selectedStudent =
                    students.get(
                            studentIndex
                    );

            Integer emergencyPriority =
                    getValidatedEmergencyPriority();

            // If invalid, stop
            if (emergencyPriority == null
                    && !emergencyPriorityField
                    .getText()
                    .trim()
                    .isEmpty()) {

                return;
            }

            String relationshipType =
                    relationshipTypeComboBox
                            .getSelectedItem()
                            .toString();

            // =========================
            // DUPLICATE RELATIONSHIP CHECK
            // =========================

            if (relationshipAlreadyExists(
                    selectedGuardian.getGuardianId(),
                    selectedStudent.getStudentId()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "This guardian is already linked to this student.\n" +
                                "Select the existing relationship and use Update Relationship instead.",
                        "Duplicate Relationship",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            GuardianStudentBridge relationship =
                    new GuardianStudentBridge(
                            selectedGuardian
                                    .getGuardianId(),

                            selectedStudent
                                    .getStudentId(),

                            relationshipType,

                            emergencyPriority
                    );

            boolean added =
                    bridgeDAO.addRelationship(
                            relationship
                    );

            if (added) {

                JOptionPane.showMessageDialog(
                        this,
                        "Relationship added successfully!"
                );

                clearFields();
                loadRelationships();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Relationship could not be added."
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
    // UPDATE
    // =========================

    private void updateRelationship() {

        int selectedRow =
                relationshipTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a relationship first."
            );

            return;
        }

        try {

            int guardianId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );

            int studentId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            2
                                    )
                                    .toString()
                    );

            Integer emergencyPriority =
                    getValidatedEmergencyPriority();

            if (emergencyPriority == null
                    && !emergencyPriorityField
                    .getText()
                    .trim()
                    .isEmpty()) {

                return;
            }

            String relationshipType =
                    relationshipTypeComboBox
                            .getSelectedItem()
                            .toString();

            GuardianStudentBridge relationship =
                    new GuardianStudentBridge(
                            guardianId,
                            studentId,
                            relationshipType,
                            emergencyPriority
                    );

            boolean updated =
                    bridgeDAO.updateRelationship(
                            relationship
                    );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Relationship updated successfully!"
                );

                clearFields();
                loadRelationships();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Relationship could not be updated."
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
    // DELETE
    // =========================

    private void deleteRelationship() {

        int selectedRow =
                relationshipTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a relationship first."
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

        int studentId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        2
                                )
                                .toString()
                );

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this relationship?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            boolean deleted =
                    bridgeDAO.deleteRelationship(
                            guardianId,
                            studentId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Relationship deleted successfully!"
                );

                clearFields();
                loadRelationships();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Relationship could not be deleted."
                );
            }
        }
    }

    // =========================
    // LOAD RELATIONSHIPS
    // =========================

    private void loadRelationships() {

        tableModel.setRowCount(
                0
        );

        List<GuardianStudentBridge> relationships =
                bridgeDAO.getAllRelationships();

        for (GuardianStudentBridge relationship
                : relationships) {

            String guardianName =
                    getGuardianName(
                            relationship
                                    .getGuardianId()
                    );

            String studentName =
                    getStudentName(
                            relationship
                                    .getStudentId()
                    );

            Object[] row = {

                    relationship
                            .getGuardianId(),

                    guardianName,

                    relationship
                            .getStudentId(),

                    studentName,

                    relationship
                            .getRelationshipType(),

                    relationship
                            .getEmergencyPriority()
            };

            tableModel.addRow(
                    row
            );
        }
    }

    // =========================
    // GET GUARDIAN NAME
    // =========================

    private String getGuardianName(
            int guardianId
    ) {

        for (Guardian guardian : guardians) {

            if (guardian.getGuardianId()
                    == guardianId) {

                return guardian
                        .getFirstName()
                        + " "
                        + guardian
                        .getLastName();
            }
        }

        return "Unknown";
    }

    // =========================
    // GET STUDENT NAME
    // =========================

    private String getStudentName(
            int studentId
    ) {

        for (Student student : students) {

            if (student.getStudentId()
                    == studentId) {

                return student
                        .getFirstName()
                        + " "
                        + student
                        .getLastName();
            }
        }

        return "Unknown";
    }

    // =========================
    // SELECT TABLE ROW
    // =========================

    private void fillFieldsFromSelectedRow() {

        int selectedRow =
                relationshipTable
                        .getSelectedRow();

        if (selectedRow != -1) {

            int guardianId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );

            int studentId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            2
                                    )
                                    .toString()
                    );

            selectGuardianById(
                    guardianId
            );

            selectStudentById(
                    studentId
            );

            relationshipTypeComboBox
                    .setSelectedItem(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            4
                                    )
                                    .toString()
                    );

            Object priority =
                    tableModel
                            .getValueAt(
                                    selectedRow,
                                    5
                            );

            if (priority == null) {

                emergencyPriorityField
                        .setText("");

            } else {

                emergencyPriorityField
                        .setText(
                                priority.toString()
                        );
            }
        }
    }

    // =========================
    // DUPLICATE RELATIONSHIP CHECK
    // =========================

    private boolean relationshipAlreadyExists(
            int guardianId,
            int studentId
    ) {

        List<GuardianStudentBridge> relationships =
                bridgeDAO.getAllRelationships();

        for (GuardianStudentBridge relationship
                : relationships) {

            boolean sameGuardian =
                    relationship.getGuardianId()
                            == guardianId;

            boolean sameStudent =
                    relationship.getStudentId()
                            == studentId;

            if (sameGuardian && sameStudent) {

                return true;
            }
        }

        return false;
    }

    // =========================
    // EMERGENCY PRIORITY VALIDATION
    // =========================

    private Integer getValidatedEmergencyPriority() {

        String priorityText =
                emergencyPriorityField
                        .getText()
                        .trim();

        // Optional field
        if (priorityText.isEmpty()) {

            return null;
        }

        try {

            int priority =
                    Integer.parseInt(
                            priorityText
                    );

            if (priority <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Emergency Priority must be greater than 0."
                );

                return null;
            }

            return priority;

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Emergency Priority must be a whole number.\n" +
                            "Example: 1, 2 or 3"
            );

            return null;
        }
    }

    // =========================
    // SELECT GUARDIAN BY ID
    // =========================

    private void selectGuardianById(
            int guardianId
    ) {

        for (
                int i = 0;
                i < guardians.size();
                i++
        ) {

            if (guardians
                    .get(i)
                    .getGuardianId()
                    == guardianId) {

                guardianComboBox
                        .setSelectedIndex(i);

                return;
            }
        }
    }

    // =========================
    // SELECT STUDENT BY ID
    // =========================

    private void selectStudentById(
            int studentId
    ) {

        for (
                int i = 0;
                i < students.size();
                i++
        ) {

            if (students
                    .get(i)
                    .getStudentId()
                    == studentId) {

                studentComboBox
                        .setSelectedIndex(i);

                return;
            }
        }
    }

    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        guardianComboBox.setSelectedIndex(
                -1
        );

        studentComboBox.setSelectedIndex(
                -1
        );

        relationshipTypeComboBox.setSelectedIndex(
                0
        );

        emergencyPriorityField.setText(
                ""
        );

        relationshipTable.clearSelection();
    }
}