package com.kindergarten.gui;

import com.kindergarten.dao.ClassroomDAO;
import com.kindergarten.dao.StaffDAO;
import com.kindergarten.model.Classroom;
import com.kindergarten.model.Staff;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class ClassroomManagementGUI extends JFrame {

    private JTextField classNameField;
    private JTextField roomNumberField;
    private JTextField capacityField;

    private JComboBox<String> leadTeacherComboBox;

    private JTable classroomTable;
    private DefaultTableModel tableModel;

    private final ClassroomDAO classroomDAO;
    private final StaffDAO staffDAO;

    private List<Staff> staffList;

    public ClassroomManagementGUI() {

        classroomDAO = new ClassroomDAO();
        staffDAO = new StaffDAO();

        setTitle("Classroom Management");
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
                new JLabel("Classroom Management");

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
                        "Add, update and manage kindergarten classes"
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

        classNameField =
                new JTextField();

        roomNumberField =
                new JTextField();

        capacityField =
                new JTextField();

        leadTeacherComboBox =
                new JComboBox<>();

        formPanel.add(
                new JLabel("Class Name")
        );

        formPanel.add(
                classNameField
        );

        formPanel.add(
                new JLabel("Room Number")
        );

        formPanel.add(
                roomNumberField
        );

        formPanel.add(
                new JLabel("Capacity")
        );

        formPanel.add(
                capacityField
        );

        formPanel.add(
                new JLabel("Lead Teacher")
        );

        formPanel.add(
                leadTeacherComboBox
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
                new JButton("Add Class");

        JButton updateButton =
                new JButton("Update Class");

        JButton deleteButton =
                new JButton("Delete Class");

        JButton clearButton =
                new JButton("Clear");

        Dimension buttonSize =
                new Dimension(150, 35);

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
                "Class ID",
                "Class Name",
                "Room Number",
                "Capacity",
                "Teacher ID",
                "Lead Teacher"
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

        classroomTable =
                new JTable(tableModel);

        classroomTable.setRowHeight(28);

        classroomTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        classroomTable
                );

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(0, 10)
                );

        JLabel tableTitle =
                new JLabel("Classroom Records");

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
                e -> addClassroom()
        );

        updateButton.addActionListener(
                e -> updateClassroom()
        );

        deleteButton.addActionListener(
                e -> deleteClassroom()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        classroomTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {
                                fillFieldsFromSelectedRow();
                            }
                        }
                );

        loadTeachers();
        loadClassrooms();

        setVisible(true);
    }

    // =========================
    // LOAD TEACHERS
    // =========================

    private void loadTeachers() {

        leadTeacherComboBox.removeAllItems();

        leadTeacherComboBox.addItem(
                "No Teacher"
        );

        staffList =
                staffDAO.getAllStaff();

        for (Staff staff : staffList) {

            leadTeacherComboBox.addItem(
                    staff.getStaffId()
                            + " - "
                            + staff.getFirstName()
                            + " "
                            + staff.getLastName()
            );
        }
    }

    // =========================
    // ADD CLASSROOM
    // =========================

    private void addClassroom() {

        try {

            String className =
                    classNameField
                            .getText()
                            .trim();

            String roomNumber =
                    roomNumberField
                            .getText()
                            .trim();

            String capacityText =
                    capacityField
                            .getText()
                            .trim();

            // Required fields
            if (className.isEmpty()
                    || roomNumber.isEmpty()
                    || capacityText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete Class Name, Room Number and Capacity."
                );

                return;
            }

            // Class Name validation
            if (!isValidClassName(className)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Class Name must contain letters.\nExample: Butterflies or Preschool 1"
                );

                return;
            }

            // Room Number validation
            if (!isValidRoomNumber(roomNumber)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Room Number must contain numbers only.\nExample: 1 or 205"
                );

                return;
            }

            int capacity =
                    Integer.parseInt(
                            capacityText
                    );

            // Capacity validation
            if (capacity <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Capacity must be greater than 0."
                );

                return;
            }

            Integer leadTeacherId =
                    getSelectedTeacherId();

            Classroom classroom =
                    new Classroom(
                            0,
                            className,
                            roomNumber,
                            capacity,
                            leadTeacherId
                    );

            boolean added =
                    classroomDAO.addClassroom(
                            classroom
                    );

            if (added) {

                JOptionPane.showMessageDialog(
                        this,
                        "Class added successfully!"
                );

                clearFields();
                loadClassrooms();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Class could not be added."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Capacity must be a whole number."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the information you entered."
            );
        }
    }

    // =========================
    // UPDATE CLASSROOM
    // =========================

    private void updateClassroom() {

        int selectedRow =
                classroomTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a class record first."
            );

            return;
        }

        try {

            String className =
                    classNameField
                            .getText()
                            .trim();

            String roomNumber =
                    roomNumberField
                            .getText()
                            .trim();

            String capacityText =
                    capacityField
                            .getText()
                            .trim();

            // Required fields
            if (className.isEmpty()
                    || roomNumber.isEmpty()
                    || capacityText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete Class Name, Room Number and Capacity."
                );

                return;
            }

            // Class Name validation
            if (!isValidClassName(className)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Class Name must contain letters.\nExample: Butterflies or Preschool 1"
                );

                return;
            }

            // Room Number validation
            if (!isValidRoomNumber(roomNumber)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Room Number must contain numbers only.\nExample: 1 or 205"
                );

                return;
            }

            int capacity =
                    Integer.parseInt(
                            capacityText
                    );

            // Capacity validation
            if (capacity <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Capacity must be greater than 0."
                );

                return;
            }

            int classId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );

            Integer leadTeacherId =
                    getSelectedTeacherId();

            Classroom classroom =
                    new Classroom(
                            classId,
                            className,
                            roomNumber,
                            capacity,
                            leadTeacherId
                    );

            boolean updated =
                    classroomDAO.updateClassroom(
                            classroom
                    );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Class updated successfully!"
                );

                clearFields();
                loadClassrooms();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Class could not be updated."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Capacity must be a whole number."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the information you entered."
            );
        }
    }

    // =========================
    // DELETE CLASSROOM
    // =========================

    private void deleteClassroom() {

        int selectedRow =
                classroomTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a class record first."
            );

            return;
        }

        int classId =
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
                        "Are you sure you want to delete this class?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            boolean deleted =
                    classroomDAO.deleteClassroom(
                            classId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Class deleted successfully!"
                );

                clearFields();
                loadClassrooms();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Class could not be deleted."
                );
            }
        }
    }

    // =========================
    // LOAD CLASSROOMS
    // =========================

    private void loadClassrooms() {

        tableModel.setRowCount(0);

        List<Classroom> classroomList =
                classroomDAO.getAllClassrooms();

        for (Classroom classroom : classroomList) {

            String teacherName =
                    getTeacherName(
                            classroom.getLeadTeacherId()
                    );

            Object[] row = {

                    classroom.getClassId(),

                    classroom.getClassName(),

                    classroom.getRoomNumber(),

                    classroom.getCapacity(),

                    classroom.getLeadTeacherId(),

                    teacherName
            };

            tableModel.addRow(row);
        }
    }

    // =========================
    // GET SELECTED TEACHER ID
    // =========================

    private Integer getSelectedTeacherId() {

        int selectedIndex =
                leadTeacherComboBox
                        .getSelectedIndex();

        if (selectedIndex <= 0) {
            return null;
        }

        String selectedItem =
                leadTeacherComboBox
                        .getSelectedItem()
                        .toString();

        String[] parts =
                selectedItem.split(
                        " - "
                );

        return Integer.parseInt(
                parts[0]
        );
    }

    // =========================
    // GET TEACHER NAME
    // =========================

    private String getTeacherName(
            Integer teacherId
    ) {

        if (teacherId == null) {
            return "No Teacher";
        }

        for (Staff staff : staffList) {

            if (staff.getStaffId() == teacherId) {

                return staff.getFirstName()
                        + " "
                        + staff.getLastName();
            }
        }

        return "Unknown";
    }

    // =========================
    // SELECT TABLE ROW
    // =========================

    private void fillFieldsFromSelectedRow() {

        int selectedRow =
                classroomTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        classNameField.setText(
                valueToString(
                        tableModel.getValueAt(
                                selectedRow,
                                1
                        )
                )
        );

        roomNumberField.setText(
                valueToString(
                        tableModel.getValueAt(
                                selectedRow,
                                2
                        )
                )
        );

        capacityField.setText(
                valueToString(
                        tableModel.getValueAt(
                                selectedRow,
                                3
                        )
                )
        );

        Object teacherIdObject =
                tableModel.getValueAt(
                        selectedRow,
                        4
                );

        if (teacherIdObject == null
                || teacherIdObject
                .toString()
                .isBlank()) {

            leadTeacherComboBox
                    .setSelectedIndex(0);

        } else {

            int teacherId =
                    Integer.parseInt(
                            teacherIdObject
                                    .toString()
                    );

            selectTeacherInComboBox(
                    teacherId
            );
        }
    }

    // =========================
    // CLASS NAME VALIDATION
    // =========================

    private boolean isValidClassName(
            String className
    ) {

        // Must contain at least one letter.
        // Allows letters, numbers, spaces, hyphens and apostrophes.
        return className.matches(
                "^(?=.*[A-Za-zÀ-ÿ])[A-Za-zÀ-ÿ0-9' -]+$"
        );
    }

    // =========================
    // ROOM NUMBER VALIDATION
    // =========================

    private boolean isValidRoomNumber(
            String roomNumber
    ) {

        // Numbers only
        return roomNumber.matches(
                "^[0-9]+$"
        );
    }

    // =========================
    // SELECT TEACHER
    // =========================

    private void selectTeacherInComboBox(
            int teacherId
    ) {

        for (int i = 1;
             i < leadTeacherComboBox.getItemCount();
             i++) {

            String item =
                    leadTeacherComboBox
                            .getItemAt(i);

            if (item.startsWith(
                    teacherId + " - "
            )) {

                leadTeacherComboBox
                        .setSelectedIndex(i);

                return;
            }
        }

        leadTeacherComboBox
                .setSelectedIndex(0);
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

        classNameField.setText("");
        roomNumberField.setText("");
        capacityField.setText("");

        leadTeacherComboBox
                .setSelectedIndex(0);

        classroomTable
                .clearSelection();
    }
}