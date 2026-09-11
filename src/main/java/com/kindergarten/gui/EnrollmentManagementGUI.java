package com.kindergarten.gui;

import com.kindergarten.dao.ClassroomDAO;
import com.kindergarten.dao.EnrollmentDAO;
import com.kindergarten.dao.StudentDAO;

import com.kindergarten.model.Classroom;
import com.kindergarten.model.Enrollment;
import com.kindergarten.model.Student;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class EnrollmentManagementGUI extends JFrame {

    private JComboBox<String> studentComboBox;
    private JComboBox<String> classComboBox;

    private JDateChooser enrollmentDateChooser;
    private JDateChooser endDateChooser;

    private JComboBox<String> statusComboBox;

    private JTable enrollmentTable;
    private DefaultTableModel tableModel;

    private final EnrollmentDAO enrollmentDAO;
    private final StudentDAO studentDAO;
    private final ClassroomDAO classroomDAO;

    private List<Student> students;
    private List<Classroom> classrooms;

    public EnrollmentManagementGUI() {

        enrollmentDAO = new EnrollmentDAO();
        studentDAO = new StudentDAO();
        classroomDAO = new ClassroomDAO();

        setTitle("Enrollment Management");
        setSize(1050, 650);
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
                new JLabel("Enrollment Management");

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
                        "Manage student class enrollments"
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

        studentComboBox =
                new JComboBox<>();

        classComboBox =
                new JComboBox<>();

        // Enrollment Date calendar
        enrollmentDateChooser =
                new JDateChooser();

        enrollmentDateChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        // Default Enrollment Date = today
        enrollmentDateChooser.setDate(
                new Date()
        );

        // End Date calendar
        endDateChooser =
                new JDateChooser();

        endDateChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        // End Date starts empty
        endDateChooser.setDate(null);

        // Status dropdown
        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "Active",
                                "Completed",
                                "Cancelled"
                        }
                );

        formPanel.add(
                new JLabel("Student")
        );

        formPanel.add(
                studentComboBox
        );

        formPanel.add(
                new JLabel("Class")
        );

        formPanel.add(
                classComboBox
        );

        formPanel.add(
                new JLabel("Enrollment Date")
        );

        formPanel.add(
                enrollmentDateChooser
        );

        formPanel.add(
                new JLabel("End Date")
        );

        formPanel.add(
                endDateChooser
        );

        formPanel.add(
                new JLabel("Status")
        );

        formPanel.add(
                statusComboBox
        );

        // Empty spaces just to keep layout neat
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
                new JButton("Add Enrollment");

        JButton updateButton =
                new JButton("Update Enrollment");

        JButton deleteButton =
                new JButton("Delete Enrollment");

        JButton clearButton =
                new JButton("Clear");

        Dimension buttonSize =
                new Dimension(180, 35);

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
                "Enrollment ID",
                "Student ID",
                "Student Name",
                "Class ID",
                "Class Name",
                "Enrollment Date",
                "End Date",
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

        enrollmentTable =
                new JTable(tableModel);

        enrollmentTable.setRowHeight(28);

        enrollmentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        enrollmentTable
                );

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(0, 10)
                );

        JLabel tableTitle =
                new JLabel("Enrollment Records");

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
                e -> addEnrollment()
        );

        updateButton.addActionListener(
                e -> updateEnrollment()
        );

        deleteButton.addActionListener(
                e -> deleteEnrollment()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        enrollmentTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {
                                fillFieldsFromSelectedRow();
                            }
                        }
                );

        loadStudents();
        loadClasses();
        loadEnrollments();

        setVisible(true);
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

        studentComboBox.setSelectedIndex(-1);
    }

    // =========================
    // LOAD CLASSES
    // =========================

    private void loadClasses() {

        classComboBox.removeAllItems();

        classrooms =
                classroomDAO.getAllClassrooms();

        for (Classroom classroom : classrooms) {

            classComboBox.addItem(
                    classroom.getClassName()
            );
        }

        classComboBox.setSelectedIndex(-1);
    }

    // =========================
    // ADD ENROLLMENT
    // =========================

    private void addEnrollment() {

        try {

            int studentIndex =
                    studentComboBox.getSelectedIndex();

            int classIndex =
                    classComboBox.getSelectedIndex();

            if (studentIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a student."
                );

                return;
            }

            if (classIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a class."
                );

                return;
            }

            Date selectedEnrollmentDate =
                    enrollmentDateChooser.getDate();

            if (selectedEnrollmentDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an enrollment date."
                );

                return;
            }

            Student selectedStudent =
                    students.get(studentIndex);

            Classroom selectedClass =
                    classrooms.get(classIndex);

            LocalDate enrollmentDate =
                    selectedEnrollmentDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            // End date is allowed to be empty
            LocalDate endDate = null;

            Date selectedEndDate =
                    endDateChooser.getDate();

            if (selectedEndDate != null) {

                endDate =
                        selectedEndDate
                                .toInstant()
                                .atZone(
                                        ZoneId.systemDefault()
                                )
                                .toLocalDate();
            }

            // Check that End Date is not before Enrollment Date
            if (endDate != null
                    && endDate.isBefore(enrollmentDate)) {

                JOptionPane.showMessageDialog(
                        this,
                        "End Date cannot be before Enrollment Date."
                );

                return;
            }

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            Enrollment enrollment =
                    new Enrollment(
                            0,
                            selectedStudent.getStudentId(),
                            selectedClass.getClassId(),
                            enrollmentDate,
                            endDate,
                            status
                    );

            boolean added =
                    enrollmentDAO.addEnrollment(
                            enrollment
                    );

            if (added) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enrollment added successfully!"
                );

                clearFields();
                loadEnrollments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Enrollment could not be added."
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
    // UPDATE ENROLLMENT
    // =========================

    private void updateEnrollment() {

        int selectedRow =
                enrollmentTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an enrollment record first."
            );

            return;
        }

        try {

            int studentIndex =
                    studentComboBox.getSelectedIndex();

            int classIndex =
                    classComboBox.getSelectedIndex();

            if (studentIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a student."
                );

                return;
            }

            if (classIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a class."
                );

                return;
            }

            Date selectedEnrollmentDate =
                    enrollmentDateChooser.getDate();

            if (selectedEnrollmentDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an enrollment date."
                );

                return;
            }

            int enrollmentId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );

            Student selectedStudent =
                    students.get(studentIndex);

            Classroom selectedClass =
                    classrooms.get(classIndex);

            LocalDate enrollmentDate =
                    selectedEnrollmentDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            LocalDate endDate = null;

            Date selectedEndDate =
                    endDateChooser.getDate();

            if (selectedEndDate != null) {

                endDate =
                        selectedEndDate
                                .toInstant()
                                .atZone(
                                        ZoneId.systemDefault()
                                )
                                .toLocalDate();
            }

            if (endDate != null
                    && endDate.isBefore(enrollmentDate)) {

                JOptionPane.showMessageDialog(
                        this,
                        "End Date cannot be before Enrollment Date."
                );

                return;
            }

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            Enrollment enrollment =
                    new Enrollment(
                            enrollmentId,
                            selectedStudent.getStudentId(),
                            selectedClass.getClassId(),
                            enrollmentDate,
                            endDate,
                            status
                    );

            boolean updated =
                    enrollmentDAO.updateEnrollment(
                            enrollment
                    );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enrollment updated successfully!"
                );

                clearFields();
                loadEnrollments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Enrollment could not be updated."
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
    // DELETE ENROLLMENT
    // =========================

    private void deleteEnrollment() {

        int selectedRow =
                enrollmentTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an enrollment record first."
            );

            return;
        }

        int enrollmentId =
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
                        "Are you sure you want to delete this enrollment?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            boolean deleted =
                    enrollmentDAO.deleteEnrollment(
                            enrollmentId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enrollment deleted successfully!"
                );

                clearFields();
                loadEnrollments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Enrollment could not be deleted."
                );
            }
        }
    }

    // =========================
    // LOAD ENROLLMENTS
    // =========================

    private void loadEnrollments() {

        tableModel.setRowCount(0);

        List<Enrollment> enrollmentList =
                enrollmentDAO.getAllEnrollments();

        for (Enrollment enrollment : enrollmentList) {

            String studentName =
                    getStudentName(
                            enrollment.getStudentId()
                    );

            String className =
                    getClassName(
                            enrollment.getClassId()
                    );

            Object endDateValue;

            if (enrollment.getEndDate() == null) {
                endDateValue = "";
            } else {
                endDateValue = enrollment.getEndDate();
            }

            Object[] row = {

                    enrollment.getEnrollmentId(),

                    enrollment.getStudentId(),

                    studentName,

                    enrollment.getClassId(),

                    className,

                    enrollment.getEnrollmentDate(),

                    endDateValue,

                    enrollment.getStatus()
            };

            tableModel.addRow(row);
        }
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

                return student.getFirstName()
                        + " "
                        + student.getLastName();
            }
        }

        return "Unknown";
    }

    // =========================
    // GET CLASS NAME
    // =========================

    private String getClassName(
            int classId
    ) {

        for (Classroom classroom : classrooms) {

            if (classroom.getClassId()
                    == classId) {

                return classroom.getClassName();
            }
        }

        return "Unknown";
    }

    // =========================
    // SELECT TABLE ROW
    // =========================

    private void fillFieldsFromSelectedRow() {

        int selectedRow =
                enrollmentTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        int studentId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        1
                                )
                                .toString()
                );

        int classId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        3
                                )
                                .toString()
                );

        selectStudentById(studentId);
        selectClassById(classId);

        // Enrollment Date
        LocalDate enrollmentDate =
                LocalDate.parse(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        5
                                )
                                .toString()
                );

        Date enrollmentDateValue =
                Date.from(
                        enrollmentDate
                                .atStartOfDay(
                                        ZoneId.systemDefault()
                                )
                                .toInstant()
                );

        enrollmentDateChooser.setDate(
                enrollmentDateValue
        );

        // End Date
        Object endDateObject =
                tableModel.getValueAt(
                        selectedRow,
                        6
                );

        if (endDateObject == null
                || endDateObject.toString().isBlank()) {

            endDateChooser.setDate(null);

        } else {

            LocalDate endDate =
                    LocalDate.parse(
                            endDateObject.toString()
                    );

            Date endDateValue =
                    Date.from(
                            endDate
                                    .atStartOfDay(
                                            ZoneId.systemDefault()
                                    )
                                    .toInstant()
                    );

            endDateChooser.setDate(
                    endDateValue
            );
        }

        String status =
                tableModel
                        .getValueAt(
                                selectedRow,
                                7
                        )
                        .toString();

        statusComboBox.setSelectedItem(
                status
        );
    }

    // =========================
    // SELECT STUDENT BY ID
    // =========================

    private void selectStudentById(
            int studentId
    ) {

        for (int i = 0;
             i < students.size();
             i++) {

            if (students
                    .get(i)
                    .getStudentId()
                    == studentId) {

                studentComboBox.setSelectedIndex(i);

                return;
            }
        }
    }

    // =========================
    // SELECT CLASS BY ID
    // =========================

    private void selectClassById(
            int classId
    ) {

        for (int i = 0;
             i < classrooms.size();
             i++) {

            if (classrooms
                    .get(i)
                    .getClassId()
                    == classId) {

                classComboBox.setSelectedIndex(i);

                return;
            }
        }
    }

    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        studentComboBox.setSelectedIndex(-1);

        classComboBox.setSelectedIndex(-1);

        // Reset Enrollment Date to today
        enrollmentDateChooser.setDate(
                new Date()
        );

        // End Date stays empty
        endDateChooser.setDate(null);

        statusComboBox.setSelectedIndex(0);

        enrollmentTable.clearSelection();
    }
}