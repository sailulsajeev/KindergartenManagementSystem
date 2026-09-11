package com.kindergarten.gui;

import com.kindergarten.dao.StudentDAO;
import com.kindergarten.model.Student;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class StudentManagementGUI extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;

    private JTextField searchField;

    private JDateChooser dateOfBirthChooser;

    private JComboBox<String> statusComboBox;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    private final StudentDAO studentDAO;

    public StudentManagementGUI() {

        studentDAO = new StudentDAO();

        setTitle("Student Management");
        setSize(900, 650);
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
                        "Student Management"
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
                        "Add, update and manage kindergarten students"
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

        firstNameField =
                new JTextField();

        lastNameField =
                new JTextField();

        dateOfBirthChooser =
                new JDateChooser();

        dateOfBirthChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "Active",
                                "Inactive"
                        }
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
                new JLabel("Date of Birth")
        );

        formPanel.add(
                dateOfBirthChooser
        );

        formPanel.add(
                new JLabel("Status")
        );

        formPanel.add(
                statusComboBox
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
                new JButton("Add Student");

        JButton updateButton =
                new JButton("Update Student");

        JButton deleteButton =
                new JButton("Delete Student");

        JButton clearButton =
                new JButton("Clear");

        Dimension buttonSize =
                new Dimension(
                        160,
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
                "Student ID",
                "First Name",
                "Last Name",
                "Date of Birth",
                "Status"
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

        studentTable =
                new JTable(
                        tableModel
                );

        studentTable.setRowHeight(
                28
        );

        studentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        studentTable
                );

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                10
                        )
                );

        // =========================
        // TABLE TITLE + SEARCH
        // =========================

        JPanel tableHeaderPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                0
                        )
                );

        JLabel tableTitle =
                new JLabel(
                        "Student Records"
                );

        tableTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        JLabel searchLabel =
                new JLabel(
                        "Search:"
                );

        searchField =
                new JTextField(15);

        searchPanel.add(
                searchLabel
        );

        searchPanel.add(
                searchField
        );

        tableHeaderPanel.add(
                tableTitle,
                BorderLayout.WEST
        );

        tableHeaderPanel.add(
                searchPanel,
                BorderLayout.EAST
        );

        tablePanel.add(
                tableHeaderPanel,
                BorderLayout.NORTH
        );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JPanel lowerPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
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
                e -> addStudent()
        );

        updateButton.addActionListener(
                e -> updateStudent()
        );

        deleteButton.addActionListener(
                e -> deleteStudent()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        // =========================
        // LIVE SEARCH
        // =========================

        searchField
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e
                            ) {
                                searchStudents();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e
                            ) {
                                searchStudents();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e
                            ) {
                                searchStudents();
                            }
                        }
                );

        // =========================
        // TABLE SELECTION
        // =========================

        studentTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                fillFieldsFromSelectedRow();
                            }
                        }
                );

        loadStudents();

        setVisible(true);
    }

    // =========================
    // ADD STUDENT
    // =========================

    private void addStudent() {

        try {

            String firstName =
                    firstNameField
                            .getText()
                            .trim();

            String lastName =
                    lastNameField
                            .getText()
                            .trim();

            Date selectedDate =
                    dateOfBirthChooser
                            .getDate();

            if (firstName.isEmpty()
                    || lastName.isEmpty()
                    || selectedDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete First Name, Last Name and Date of Birth."
                );

                return;
            }

            // First Name validation
            if (!isValidName(
                    firstName
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "First Name can only contain letters, spaces, hyphens and apostrophes."
                );

                return;
            }

            // Last Name validation
            if (!isValidName(
                    lastName
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Last Name can only contain letters, spaces, hyphens and apostrophes."
                );

                return;
            }

            LocalDate dateOfBirth =
                    selectedDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            // Future DOB validation
            if (dateOfBirth.isAfter(
                    LocalDate.now()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Date of Birth cannot be in the future."
                );

                return;
            }

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            Student student =
                    new Student(
                            0,
                            firstName,
                            lastName,
                            dateOfBirth,
                            status
                    );

            boolean added =
                    studentDAO.addStudent(
                            student
                    );

            if (added) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student added successfully!"
                );

                clearFields();
                loadStudents();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Student could not be added."
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
    // UPDATE STUDENT
    // =========================

    private void updateStudent() {

        int selectedRow =
                studentTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student record first."
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

            Date selectedDate =
                    dateOfBirthChooser
                            .getDate();

            if (firstName.isEmpty()
                    || lastName.isEmpty()
                    || selectedDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete First Name, Last Name and Date of Birth."
                );

                return;
            }

            if (!isValidName(
                    firstName
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "First Name can only contain letters, spaces, hyphens and apostrophes."
                );

                return;
            }

            if (!isValidName(
                    lastName
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Last Name can only contain letters, spaces, hyphens and apostrophes."
                );

                return;
            }

            LocalDate dateOfBirth =
                    selectedDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            if (dateOfBirth.isAfter(
                    LocalDate.now()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Date of Birth cannot be in the future."
                );

                return;
            }

            int studentId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            Student student =
                    new Student(
                            studentId,
                            firstName,
                            lastName,
                            dateOfBirth,
                            status
                    );

            boolean updated =
                    studentDAO.updateStudent(
                            student
                    );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student updated successfully!"
                );

                clearFields();
                loadStudents();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Student could not be updated."
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
    // DELETE STUDENT
    // =========================

    private void deleteStudent() {

        int selectedRow =
                studentTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student record first."
            );

            return;
        }

        int studentId =
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
                        "Are you sure you want to delete this student?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            boolean deleted =
                    studentDAO.deleteStudent(
                            studentId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student deleted successfully!"
                );

                clearFields();
                loadStudents();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Student could not be deleted."
                );
            }
        }
    }

    // =========================
    // LOAD STUDENTS
    // =========================

    private void loadStudents() {

        tableModel.setRowCount(0);

        List<Student> studentList =
                studentDAO
                        .getAllStudents();

        for (Student student : studentList) {

            Object[] row = {

                    student.getStudentId(),

                    student.getFirstName(),

                    student.getLastName(),

                    student.getDateOfBirth(),

                    student.getStatus()
            };

            tableModel.addRow(
                    row
            );
        }
    }

    // =========================
    // SEARCH STUDENTS
    // =========================

    private void searchStudents() {

        String searchText =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        tableModel.setRowCount(0);

        List<Student> studentList =
                studentDAO
                        .getAllStudents();

        for (Student student : studentList) {

            String firstName =
                    student
                            .getFirstName()
                            .toLowerCase();

            String lastName =
                    student
                            .getLastName()
                            .toLowerCase();

            if (firstName.contains(
                    searchText
            )
                    || lastName.contains(
                    searchText
            )) {

                Object[] row = {

                        student.getStudentId(),

                        student.getFirstName(),

                        student.getLastName(),

                        student.getDateOfBirth(),

                        student.getStatus()
                };

                tableModel.addRow(
                        row
                );
            }
        }
    }

    // =========================
    // SELECT TABLE ROW
    // =========================

    private void fillFieldsFromSelectedRow() {

        int selectedRow =
                studentTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            return;
        }

        firstNameField.setText(
                valueToString(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        1
                                )
                )
        );

        lastNameField.setText(
                valueToString(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        2
                                )
                )
        );

        Object dateObject =
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        );

        if (dateObject != null) {

            LocalDate dateOfBirth =
                    LocalDate.parse(
                            dateObject
                                    .toString()
                    );

            Date date =
                    Date.from(
                            dateOfBirth
                                    .atStartOfDay(
                                            ZoneId.systemDefault()
                                    )
                                    .toInstant()
                    );

            dateOfBirthChooser.setDate(
                    date
            );

        } else {

            dateOfBirthChooser.setDate(
                    null
            );
        }

        statusComboBox.setSelectedItem(
                valueToString(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        4
                                )
                )
        );
    }

    // =========================
    // NAME VALIDATION
    // =========================

    private boolean isValidName(
            String name
    ) {

        return name.matches(
                "^[A-Za-zÀ-ÿ' -]+$"
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

        dateOfBirthChooser.setDate(
                null
        );

        statusComboBox.setSelectedItem(
                "Active"
        );

        studentTable.clearSelection();
    }
}