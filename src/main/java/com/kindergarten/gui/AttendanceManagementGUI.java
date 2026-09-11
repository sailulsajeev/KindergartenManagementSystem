package com.kindergarten.gui;

import com.kindergarten.dao.AttendanceDAO;
import com.kindergarten.dao.StudentDAO;
import com.kindergarten.model.Attendance;
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

public class AttendanceManagementGUI extends JFrame {

    private JComboBox<String> studentComboBox;

    private JDateChooser attendanceDateChooser;

    private JComboBox<String> statusComboBox;
    private JTextField notesField;

    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    private final AttendanceDAO attendanceDAO;
    private final StudentDAO studentDAO;

    private List<Student> students;

    public AttendanceManagementGUI() {

        attendanceDAO = new AttendanceDAO();
        studentDAO = new StudentDAO();

        setTitle("Attendance Management");
        setSize(950, 620);
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
                new JLabel("Attendance Management");

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
                        "Record and manage student attendance"
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
                                2,
                                4,
                                12,
                                12
                        )
                );

        studentComboBox =
                new JComboBox<>();

        attendanceDateChooser =
                new JDateChooser();

        attendanceDateChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        // Default to today
        attendanceDateChooser.setDate(
                new Date()
        );

        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "Present",
                                "Absent",
                                "Late"
                        }
                );

        notesField =
                new JTextField();

        formPanel.add(
                new JLabel("Student")
        );

        formPanel.add(
                studentComboBox
        );

        formPanel.add(
                new JLabel("Attendance Date")
        );

        formPanel.add(
                attendanceDateChooser
        );

        formPanel.add(
                new JLabel("Status")
        );

        formPanel.add(
                statusComboBox
        );

        formPanel.add(
                new JLabel("Notes")
        );

        formPanel.add(
                notesField
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
                new JButton("Add Attendance");

        JButton updateButton =
                new JButton("Update Attendance");

        JButton deleteButton =
                new JButton("Delete Attendance");

        JButton clearButton =
                new JButton("Clear");

        Dimension buttonSize =
                new Dimension(175, 35);

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
                "Attendance ID",
                "Student ID",
                "Student Name",
                "Attendance Date",
                "Status",
                "Notes"
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

        attendanceTable =
                new JTable(tableModel);

        attendanceTable.setRowHeight(28);

        attendanceTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        attendanceTable
                );

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(0, 10)
                );

        JLabel tableTitle =
                new JLabel(
                        "Attendance Records"
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
                e -> addAttendance()
        );

        updateButton.addActionListener(
                e -> updateAttendance()
        );

        deleteButton.addActionListener(
                e -> deleteAttendance()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        attendanceTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {
                                fillFieldsFromSelectedRow();
                            }
                        }
                );

        loadStudents();
        loadAttendance();

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
    // ADD ATTENDANCE
    // =========================

    private void addAttendance() {

        try {

            int studentIndex =
                    studentComboBox.getSelectedIndex();

            if (studentIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a student."
                );

                return;
            }

            Date selectedDate =
                    attendanceDateChooser.getDate();

            if (selectedDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an attendance date."
                );

                return;
            }

            Student selectedStudent =
                    students.get(studentIndex);

            LocalDate attendanceDate =
                    selectedDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            // =========================
            // FUTURE DATE VALIDATION
            // =========================

            if (attendanceDate.isAfter(
                    LocalDate.now()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance date cannot be in the future."
                );

                return;
            }

            // =========================
            // DUPLICATE VALIDATION
            // =========================

            if (attendanceAlreadyExists(
                    selectedStudent.getStudentId(),
                    attendanceDate,
                    0
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance already exists for this student on this date."
                );

                return;
            }

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            Attendance attendance =
                    new Attendance(
                            0,
                            selectedStudent.getStudentId(),
                            attendanceDate,
                            status,
                            notesField.getText().trim()
                    );

            boolean added =
                    attendanceDAO.addAttendance(
                            attendance
                    );

            if (added) {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance added successfully!"
                );

                clearFields();
                loadAttendance();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance could not be added."
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
    // UPDATE ATTENDANCE
    // =========================

    private void updateAttendance() {

        int selectedRow =
                attendanceTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an attendance record first."
            );

            return;
        }

        try {

            int studentIndex =
                    studentComboBox.getSelectedIndex();

            if (studentIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a student."
                );

                return;
            }

            Date selectedDate =
                    attendanceDateChooser.getDate();

            if (selectedDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an attendance date."
                );

                return;
            }

            int attendanceId =
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

            LocalDate attendanceDate =
                    selectedDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            // =========================
            // FUTURE DATE VALIDATION
            // =========================

            if (attendanceDate.isAfter(
                    LocalDate.now()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance date cannot be in the future."
                );

                return;
            }

            // =========================
            // DUPLICATE VALIDATION
            // =========================

            if (attendanceAlreadyExists(
                    selectedStudent.getStudentId(),
                    attendanceDate,
                    attendanceId
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Another attendance record already exists for this student on this date."
                );

                return;
            }

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            Attendance attendance =
                    new Attendance(
                            attendanceId,
                            selectedStudent.getStudentId(),
                            attendanceDate,
                            status,
                            notesField.getText().trim()
                    );

            boolean updated =
                    attendanceDAO.updateAttendance(
                            attendance
                    );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance updated successfully!"
                );

                clearFields();
                loadAttendance();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance could not be updated."
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
    // CHECK DUPLICATE ATTENDANCE
    // =========================

    private boolean attendanceAlreadyExists(
            int studentId,
            LocalDate attendanceDate,
            int attendanceIdToIgnore
    ) {

        List<Attendance> attendanceList =
                attendanceDAO.getAllAttendance();

        for (Attendance attendance : attendanceList) {

            boolean sameStudent =
                    attendance.getStudentId()
                            == studentId;

            boolean sameDate =
                    attendance
                            .getAttendanceDate()
                            .equals(attendanceDate);

            boolean differentRecord =
                    attendance.getAttendanceId()
                            != attendanceIdToIgnore;

            if (sameStudent
                    && sameDate
                    && differentRecord) {

                return true;
            }
        }

        return false;
    }

    // =========================
    // DELETE ATTENDANCE
    // =========================

    private void deleteAttendance() {

        int selectedRow =
                attendanceTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an attendance record first."
            );

            return;
        }

        int attendanceId =
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
                        "Are you sure you want to delete this attendance record?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            boolean deleted =
                    attendanceDAO.deleteAttendance(
                            attendanceId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance deleted successfully!"
                );

                clearFields();
                loadAttendance();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Attendance could not be deleted."
                );
            }
        }
    }

    // =========================
    // LOAD ATTENDANCE
    // =========================

    private void loadAttendance() {

        tableModel.setRowCount(0);

        List<Attendance> attendanceList =
                attendanceDAO.getAllAttendance();

        for (Attendance attendance : attendanceList) {

            String studentName =
                    getStudentName(
                            attendance.getStudentId()
                    );

            Object[] row = {

                    attendance.getAttendanceId(),

                    attendance.getStudentId(),

                    studentName,

                    attendance.getAttendanceDate(),

                    attendance.getStatus(),

                    attendance.getNotes()
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
    // SELECT TABLE ROW
    // =========================

    private void fillFieldsFromSelectedRow() {

        int selectedRow =
                attendanceTable.getSelectedRow();

        if (selectedRow != -1) {

            int studentId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            1
                                    )
                                    .toString()
                    );

            selectStudentById(
                    studentId
            );

            LocalDate attendanceDate =
                    LocalDate.parse(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            3
                                    )
                                    .toString()
                    );

            Date date =
                    Date.from(
                            attendanceDate
                                    .atStartOfDay(
                                            ZoneId.systemDefault()
                                    )
                                    .toInstant()
                    );

            attendanceDateChooser.setDate(
                    date
            );

            String status =
                    tableModel
                            .getValueAt(
                                    selectedRow,
                                    4
                            )
                            .toString();

            statusComboBox.setSelectedItem(
                    status
            );

            Object notes =
                    tableModel.getValueAt(
                            selectedRow,
                            5
                    );

            if (notes == null) {

                notesField.setText("");

            } else {

                notesField.setText(
                        notes.toString()
                );
            }
        }
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

        studentComboBox.setSelectedIndex(-1);

        attendanceDateChooser.setDate(
                new Date()
        );

        statusComboBox.setSelectedIndex(0);

        notesField.setText("");

        attendanceTable.clearSelection();
    }
}