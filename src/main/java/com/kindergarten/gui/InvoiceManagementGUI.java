package com.kindergarten.gui;

import com.kindergarten.dao.InvoiceDAO;
import com.kindergarten.dao.StudentDAO;

import com.kindergarten.model.Invoice;
import com.kindergarten.model.Student;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class InvoiceManagementGUI extends JFrame {

    private JComboBox<String> studentComboBox;

    private JDateChooser issueDateChooser;
    private JDateChooser dueDateChooser;

    private JTextField amountDueField;

    private JComboBox<String> statusComboBox;

    private JTable invoiceTable;
    private DefaultTableModel tableModel;

    private final InvoiceDAO invoiceDAO;
    private final StudentDAO studentDAO;

    private List<Student> students;

    public InvoiceManagementGUI() {

        invoiceDAO = new InvoiceDAO();
        studentDAO = new StudentDAO();

        setTitle("Invoice Management");
        setSize(1000, 650);
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
                        "Invoice Management"
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
                        "Create and manage student invoices"
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
                                3,
                                4,
                                12,
                                12
                        )
                );

        studentComboBox =
                new JComboBox<>();

        issueDateChooser =
                new JDateChooser();

        issueDateChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        issueDateChooser.setDate(
                new Date()
        );

        dueDateChooser =
                new JDateChooser();

        dueDateChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        amountDueField =
                new JTextField();

        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "Unpaid",
                                "Paid",
                                "Overdue",
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
                new JLabel("Issue Date")
        );

        formPanel.add(
                issueDateChooser
        );

        formPanel.add(
                new JLabel("Due Date")
        );

        formPanel.add(
                dueDateChooser
        );

        formPanel.add(
                new JLabel("Amount Due")
        );

        formPanel.add(
                amountDueField
        );

        formPanel.add(
                new JLabel("Status")
        );

        formPanel.add(
                statusComboBox
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
                new JButton(
                        "Add Invoice"
                );

        JButton updateButton =
                new JButton(
                        "Update Invoice"
                );

        JButton deleteButton =
                new JButton(
                        "Delete Invoice"
                );

        JButton clearButton =
                new JButton(
                        "Clear"
                );

        Dimension buttonSize =
                new Dimension(
                        170,
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
                "Invoice ID",
                "Student ID",
                "Student Name",
                "Issue Date",
                "Due Date",
                "Amount Due",
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

        invoiceTable =
                new JTable(
                        tableModel
                );

        invoiceTable.setRowHeight(
                28
        );

        invoiceTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        invoiceTable
                );

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(0, 10)
                );

        JLabel tableTitle =
                new JLabel(
                        "Invoice Records"
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
                e -> addInvoice()
        );

        updateButton.addActionListener(
                e -> updateInvoice()
        );

        deleteButton.addActionListener(
                e -> deleteInvoice()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        invoiceTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                fillFieldsFromSelectedRow();
                            }
                        }
                );

        loadStudents();
        loadInvoices();

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

        studentComboBox.setSelectedIndex(
                -1
        );
    }

    // =========================
    // ADD INVOICE
    // =========================

    private void addInvoice() {

        try {

            int studentIndex =
                    studentComboBox
                            .getSelectedIndex();

            if (studentIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a student."
                );

                return;
            }

            Date selectedIssueDate =
                    issueDateChooser
                            .getDate();

            Date selectedDueDate =
                    dueDateChooser
                            .getDate();

            if (selectedIssueDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an issue date."
                );

                return;
            }

            if (selectedDueDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a due date."
                );

                return;
            }

            String amountText =
                    amountDueField
                            .getText()
                            .trim();

            if (amountText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the amount due."
                );

                return;
            }

            BigDecimal amountDue =
                    new BigDecimal(
                            amountText
                    );

            if (amountDue.compareTo(
                    BigDecimal.ZERO
            ) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount Due must be greater than 0."
                );

                return;
            }

            LocalDate issueDate =
                    selectedIssueDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            LocalDate dueDate =
                    selectedDueDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            // =========================
            // ISSUE DATE VALIDATION
            // =========================

            if (issueDate.isAfter(
                    LocalDate.now()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Issue Date cannot be in the future."
                );

                return;
            }

            // =========================
            // DUE DATE VALIDATION
            // =========================

            if (dueDate.isBefore(
                    issueDate
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Due Date cannot be before Issue Date."
                );

                return;
            }

            Student selectedStudent =
                    students.get(
                            studentIndex
                    );

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            // =========================
            // DUPLICATE INVOICE CHECK
            // =========================

            boolean duplicate =
                    hasDuplicateInvoice(
                            selectedStudent
                                    .getStudentId(),
                            issueDate,
                            dueDate,
                            amountDue,
                            status,
                            -1
                    );

            if (duplicate) {

                JOptionPane.showMessageDialog(
                        this,
                        "An identical invoice already exists for this student.",
                        "Duplicate Invoice",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Invoice invoice =
                    new Invoice(
                            0,
                            selectedStudent
                                    .getStudentId(),
                            issueDate,
                            dueDate,
                            amountDue,
                            status
                    );

            boolean added =
                    invoiceDAO
                            .addInvoice(
                                    invoice
                            );

            if (added) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invoice added successfully!"
                );

                clearFields();
                loadInvoices();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invoice could not be added."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Amount Due must be a valid number."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the information you entered."
            );
        }
    }

    // =========================
    // UPDATE INVOICE
    // =========================

    private void updateInvoice() {

        int selectedRow =
                invoiceTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an invoice record first."
            );

            return;
        }

        try {

            int studentIndex =
                    studentComboBox
                            .getSelectedIndex();

            if (studentIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a student."
                );

                return;
            }

            Date selectedIssueDate =
                    issueDateChooser
                            .getDate();

            Date selectedDueDate =
                    dueDateChooser
                            .getDate();

            if (selectedIssueDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an issue date."
                );

                return;
            }

            if (selectedDueDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a due date."
                );

                return;
            }

            String amountText =
                    amountDueField
                            .getText()
                            .trim();

            if (amountText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the amount due."
                );

                return;
            }

            BigDecimal amountDue =
                    new BigDecimal(
                            amountText
                    );

            if (amountDue.compareTo(
                    BigDecimal.ZERO
            ) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount Due must be greater than 0."
                );

                return;
            }

            LocalDate issueDate =
                    selectedIssueDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            LocalDate dueDate =
                    selectedDueDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            // =========================
            // ISSUE DATE VALIDATION
            // =========================

            if (issueDate.isAfter(
                    LocalDate.now()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Issue Date cannot be in the future."
                );

                return;
            }

            // =========================
            // DUE DATE VALIDATION
            // =========================

            if (dueDate.isBefore(
                    issueDate
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Due Date cannot be before Issue Date."
                );

                return;
            }

            int invoiceId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );

            Student selectedStudent =
                    students.get(
                            studentIndex
                    );

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            // =========================
            // DUPLICATE INVOICE CHECK
            // =========================

            boolean duplicate =
                    hasDuplicateInvoice(
                            selectedStudent
                                    .getStudentId(),
                            issueDate,
                            dueDate,
                            amountDue,
                            status,
                            invoiceId
                    );

            if (duplicate) {

                JOptionPane.showMessageDialog(
                        this,
                        "An identical invoice already exists for this student.",
                        "Duplicate Invoice",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Invoice invoice =
                    new Invoice(
                            invoiceId,
                            selectedStudent
                                    .getStudentId(),
                            issueDate,
                            dueDate,
                            amountDue,
                            status
                    );

            boolean updated =
                    invoiceDAO
                            .updateInvoice(
                                    invoice
                            );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invoice updated successfully!"
                );

                clearFields();
                loadInvoices();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invoice could not be updated."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Amount Due must be a valid number."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the information you entered."
            );
        }
    }

    // =========================
    // DUPLICATE INVOICE CHECK
    // =========================

    private boolean hasDuplicateInvoice(
            int studentId,
            LocalDate issueDate,
            LocalDate dueDate,
            BigDecimal amountDue,
            String status,
            int invoiceIdToIgnore
    ) {

        List<Invoice> invoices =
                invoiceDAO
                        .getAllInvoices();

        for (Invoice invoice : invoices) {

            boolean differentRecord =
                    invoice.getInvoiceId()
                            != invoiceIdToIgnore;

            boolean sameStudent =
                    invoice.getStudentId()
                            == studentId;

            boolean sameIssueDate =
                    invoice.getIssueDate()
                            .equals(issueDate);

            boolean sameDueDate =
                    invoice.getDueDate()
                            .equals(dueDate);

            boolean sameAmount =
                    invoice.getAmountDue()
                            .compareTo(amountDue)
                            == 0;

            boolean sameStatus =
                    invoice.getStatus()
                            != null
                            && invoice
                            .getStatus()
                            .equalsIgnoreCase(
                                    status
                            );

            if (differentRecord
                    && sameStudent
                    && sameIssueDate
                    && sameDueDate
                    && sameAmount
                    && sameStatus) {

                return true;
            }
        }

        return false;
    }

    // =========================
    // DELETE INVOICE
    // =========================

    private void deleteInvoice() {

        int selectedRow =
                invoiceTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an invoice record first."
            );

            return;
        }

        int invoiceId =
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
                        "Are you sure you want to delete this invoice?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            boolean deleted =
                    invoiceDAO
                            .deleteInvoice(
                                    invoiceId
                            );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invoice deleted successfully!"
                );

                clearFields();
                loadInvoices();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invoice could not be deleted."
                );
            }
        }
    }

    // =========================
    // LOAD INVOICES
    // =========================

    private void loadInvoices() {

        tableModel.setRowCount(
                0
        );

        List<Invoice> invoices =
                invoiceDAO
                        .getAllInvoices();

        for (Invoice invoice : invoices) {

            String studentName =
                    getStudentName(
                            invoice
                                    .getStudentId()
                    );

            Object[] row = {

                    invoice
                            .getInvoiceId(),

                    invoice
                            .getStudentId(),

                    studentName,

                    invoice
                            .getIssueDate(),

                    invoice
                            .getDueDate(),

                    invoice
                            .getAmountDue(),

                    invoice
                            .getStatus()
            };

            tableModel.addRow(
                    row
            );
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
                invoiceTable
                        .getSelectedRow();

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

        selectStudentById(
                studentId
        );

        LocalDate issueDate =
                LocalDate.parse(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        3
                                )
                                .toString()
                );

        Date issueDateValue =
                Date.from(
                        issueDate
                                .atStartOfDay(
                                        ZoneId.systemDefault()
                                )
                                .toInstant()
                );

        issueDateChooser.setDate(
                issueDateValue
        );

        LocalDate dueDate =
                LocalDate.parse(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        4
                                )
                                .toString()
                );

        Date dueDateValue =
                Date.from(
                        dueDate
                                .atStartOfDay(
                                        ZoneId.systemDefault()
                                )
                                .toInstant()
                );

        dueDateChooser.setDate(
                dueDateValue
        );

        amountDueField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString()
        );

        String status =
                tableModel
                        .getValueAt(
                                selectedRow,
                                6
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
                        .setSelectedIndex(
                                i
                        );

                return;
            }
        }
    }

    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        studentComboBox.setSelectedIndex(
                -1
        );

        // Issue Date resets to today
        issueDateChooser.setDate(
                new Date()
        );

        // Due Date becomes blank
        dueDateChooser.setDate(
                null
        );

        amountDueField.setText(
                ""
        );

        statusComboBox.setSelectedIndex(
                0
        );

        invoiceTable.clearSelection();
    }
}