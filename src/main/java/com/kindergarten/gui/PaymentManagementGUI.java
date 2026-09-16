package com.kindergarten.gui;

import com.kindergarten.dao.InvoiceDAO;
import com.kindergarten.dao.PaymentDAO;
import com.kindergarten.dao.StudentDAO;

import com.kindergarten.model.Invoice;
import com.kindergarten.model.Payment;
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

public class PaymentManagementGUI extends JFrame {

    private JComboBox<String> invoiceComboBox;

    private JDateChooser paymentDateChooser;

    private JTextField amountPaidField;

    private JComboBox<String> paymentMethodComboBox;
    private JComboBox<String> statusComboBox;

    private JTable paymentTable;
    private DefaultTableModel tableModel;

    private final PaymentDAO paymentDAO;
    private final InvoiceDAO invoiceDAO;
    private final StudentDAO studentDAO;

    private List<Invoice> invoices;
    private List<Student> students;

    public PaymentManagementGUI() {

        paymentDAO = new PaymentDAO();
        invoiceDAO = new InvoiceDAO();
        studentDAO = new StudentDAO();

        setTitle("Payment Management");
        setSize(1050, 650);
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
                        "Payment Management"
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
                        "Record and manage invoice payments"
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

        invoiceComboBox =
                new JComboBox<>();

        paymentDateChooser =
                new JDateChooser();

        paymentDateChooser.setDateFormatString(
                "yyyy-MM-dd"
        );

        paymentDateChooser.setDate(
                new Date()
        );

        amountPaidField =
                new JTextField();

        paymentMethodComboBox =
                new JComboBox<>(
                        new String[]{
                                "Card",
                                "Cash",
                                "Bank Transfer"
                        }
                );

        statusComboBox =
                new JComboBox<>(
                        new String[]{
                                "Completed",
                                "Pending",
                                "Failed",
                                "Refunded"
                        }
                );

        formPanel.add(
                new JLabel("Invoice")
        );

        formPanel.add(
                invoiceComboBox
        );

        formPanel.add(
                new JLabel("Payment Date")
        );

        formPanel.add(
                paymentDateChooser
        );

        formPanel.add(
                new JLabel("Amount Paid")
        );

        formPanel.add(
                amountPaidField
        );

        formPanel.add(
                new JLabel("Payment Method")
        );

        formPanel.add(
                paymentMethodComboBox
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
                        "Add Payment"
                );

        JButton updateButton =
                new JButton(
                        "Update Payment"
                );

        JButton deleteButton =
                new JButton(
                        "Delete Payment"
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
                "Payment ID",
                "Invoice ID",
                "Student Name",
                "Payment Date",
                "Amount Paid",
                "Payment Method",
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

        paymentTable =
                new JTable(
                        tableModel
                );

        paymentTable.setRowHeight(
                28
        );

        paymentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        paymentTable
                );

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(0, 10)
                );

        JLabel tableTitle =
                new JLabel(
                        "Payment Records"
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
                e -> addPayment()
        );

        updateButton.addActionListener(
                e -> updatePayment()
        );

        deleteButton.addActionListener(
                e -> deletePayment()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        paymentTable
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
        loadPayments();

        setVisible(true);
    }

    // =========================
    // LOAD STUDENTS
    // =========================

    private void loadStudents() {

        students =
                studentDAO.getAllStudents();
    }

    // =========================
    // LOAD INVOICES
    // =========================

    private void loadInvoices() {

        invoiceComboBox.removeAllItems();

        invoices =
                invoiceDAO.getAllInvoices();

        for (Invoice invoice : invoices) {

            String studentName =
                    getStudentName(
                            invoice.getStudentId()
                    );

            String displayText =
                    "Invoice "
                            + invoice.getInvoiceId()
                            + " - "
                            + studentName
                            + " - "
                            + invoice.getAmountDue();

            invoiceComboBox.addItem(
                    displayText
            );
        }

        invoiceComboBox.setSelectedIndex(
                -1
        );
    }

    // =========================
    // ADD PAYMENT
    // =========================

    private void addPayment() {

        try {

            int invoiceIndex =
                    invoiceComboBox
                            .getSelectedIndex();

            if (invoiceIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an invoice."
                );

                return;
            }

            Date selectedPaymentDate =
                    paymentDateChooser
                            .getDate();

            if (selectedPaymentDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a payment date."
                );

                return;
            }

            String amountText =
                    amountPaidField
                            .getText()
                            .trim();

            if (amountText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the amount paid."
                );

                return;
            }

            BigDecimal amountPaid =
                    new BigDecimal(
                            amountText
                    );

            if (amountPaid.compareTo(
                    BigDecimal.ZERO
            ) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount Paid must be greater than 0."
                );

                return;
            }

            LocalDate paymentDate =
                    selectedPaymentDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            Invoice selectedInvoice =
                    invoices.get(
                            invoiceIndex
                    );

            // =========================
            // FUTURE DATE VALIDATION
            // =========================

            if (paymentDate.isAfter(
                    LocalDate.now()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment date cannot be in the future."
                );

                return;
            }

            // =========================
            // INDIVIDUAL PAYMENT VALIDATION
            // =========================

            if (amountPaid.compareTo(
                    selectedInvoice.getAmountDue()
            ) > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment cannot be greater than the invoice amount.\n"
                                + "Invoice amount: "
                                + selectedInvoice.getAmountDue()
                );

                return;
            }

            String paymentMethod =
                    paymentMethodComboBox
                            .getSelectedItem()
                            .toString();

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            // =========================
            // TOTAL COMPLETED PAYMENT VALIDATION
            // =========================

            if (status.equalsIgnoreCase(
                    "Completed"
            )) {

                BigDecimal existingTotal =
                        getCompletedPaymentTotal(
                                selectedInvoice.getInvoiceId(),
                                -1
                        );

                BigDecimal newTotal =
                        existingTotal.add(
                                amountPaid
                        );

                if (newTotal.compareTo(
                        selectedInvoice.getAmountDue()
                ) > 0) {

                    BigDecimal remainingAmount =
                            selectedInvoice
                                    .getAmountDue()
                                    .subtract(
                                            existingTotal
                                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "This payment would make the total payments greater than the invoice amount.\n"
                                    + "Invoice amount: "
                                    + selectedInvoice.getAmountDue()
                                    + "\nCompleted payments already recorded: "
                                    + existingTotal
                                    + "\nRemaining amount: "
                                    + remainingAmount,
                            "Payment Amount Exceeded",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }
            }

            Payment payment =
                    new Payment(
                            0,
                            selectedInvoice.getInvoiceId(),
                            paymentDate,
                            amountPaid,
                            paymentMethod,
                            status
                    );

            boolean added =
                    paymentDAO.addPayment(
                            payment
                    );

            if (added) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment added successfully!"
                );

                clearFields();
                loadPayments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment could not be added."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Amount Paid must be a valid number."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the information you entered."
            );
        }
    }

    // =========================
    // UPDATE PAYMENT
    // =========================

    private void updatePayment() {

        int selectedRow =
                paymentTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a payment record first."
            );

            return;
        }

        try {

            int invoiceIndex =
                    invoiceComboBox
                            .getSelectedIndex();

            if (invoiceIndex == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an invoice."
                );

                return;
            }

            Date selectedPaymentDate =
                    paymentDateChooser
                            .getDate();

            if (selectedPaymentDate == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a payment date."
                );

                return;
            }

            String amountText =
                    amountPaidField
                            .getText()
                            .trim();

            if (amountText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the amount paid."
                );

                return;
            }

            BigDecimal amountPaid =
                    new BigDecimal(
                            amountText
                    );

            if (amountPaid.compareTo(
                    BigDecimal.ZERO
            ) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount Paid must be greater than 0."
                );

                return;
            }

            int paymentId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );

            LocalDate paymentDate =
                    selectedPaymentDate
                            .toInstant()
                            .atZone(
                                    ZoneId.systemDefault()
                            )
                            .toLocalDate();

            Invoice selectedInvoice =
                    invoices.get(
                            invoiceIndex
                    );

            // =========================
            // FUTURE DATE VALIDATION
            // =========================

            if (paymentDate.isAfter(
                    LocalDate.now()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment date cannot be in the future."
                );

                return;
            }

            // =========================
            // INDIVIDUAL PAYMENT VALIDATION
            // =========================

            if (amountPaid.compareTo(
                    selectedInvoice.getAmountDue()
            ) > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment cannot be greater than the invoice amount.\n"
                                + "Invoice amount: "
                                + selectedInvoice.getAmountDue()
                );

                return;
            }

            String paymentMethod =
                    paymentMethodComboBox
                            .getSelectedItem()
                            .toString();

            String status =
                    statusComboBox
                            .getSelectedItem()
                            .toString();

            // =========================
            // TOTAL COMPLETED PAYMENT VALIDATION
            // =========================

            if (status.equalsIgnoreCase(
                    "Completed"
            )) {

                BigDecimal existingTotal =
                        getCompletedPaymentTotal(
                                selectedInvoice.getInvoiceId(),
                                paymentId
                        );

                BigDecimal newTotal =
                        existingTotal.add(
                                amountPaid
                        );

                if (newTotal.compareTo(
                        selectedInvoice.getAmountDue()
                ) > 0) {

                    BigDecimal remainingAmount =
                            selectedInvoice
                                    .getAmountDue()
                                    .subtract(
                                            existingTotal
                                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "This payment would make the total payments greater than the invoice amount.\n"
                                    + "Invoice amount: "
                                    + selectedInvoice.getAmountDue()
                                    + "\nCompleted payments already recorded: "
                                    + existingTotal
                                    + "\nRemaining amount: "
                                    + remainingAmount,
                            "Payment Amount Exceeded",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }
            }

            Payment payment =
                    new Payment(
                            paymentId,
                            selectedInvoice.getInvoiceId(),
                            paymentDate,
                            amountPaid,
                            paymentMethod,
                            status
                    );

            boolean updated =
                    paymentDAO.updatePayment(
                            payment
                    );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment updated successfully!"
                );

                clearFields();
                loadPayments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment could not be updated."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Amount Paid must be a valid number."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the information you entered."
            );
        }
    }

    // =========================
    // COMPLETED PAYMENT TOTAL
    // =========================

    private BigDecimal getCompletedPaymentTotal(
            int invoiceId,
            int paymentIdToIgnore
    ) {

        BigDecimal total =
                BigDecimal.ZERO;

        List<Payment> payments =
                paymentDAO.getAllPayments();

        for (Payment payment : payments) {

            boolean sameInvoice =
                    payment.getInvoiceId()
                            == invoiceId;

            boolean differentPayment =
                    payment.getPaymentId()
                            != paymentIdToIgnore;

            boolean completed =
                    payment.getStatus()
                            != null
                            && payment
                            .getStatus()
                            .equalsIgnoreCase(
                                    "Completed"
                            );

            if (sameInvoice
                    && differentPayment
                    && completed) {

                total =
                        total.add(
                                payment.getAmountPaid()
                        );
            }
        }

        return total;
    }

    // =========================
    // DELETE PAYMENT
    // =========================

    private void deletePayment() {

        int selectedRow =
                paymentTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a payment record first."
            );

            return;
        }

        int paymentId =
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
                        "Are you sure you want to delete this payment?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            boolean deleted =
                    paymentDAO.deletePayment(
                            paymentId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment deleted successfully!"
                );

                clearFields();
                loadPayments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment could not be deleted."
                );
            }
        }
    }

    // =========================
    // LOAD PAYMENTS
    // =========================

    private void loadPayments() {

        tableModel.setRowCount(
                0
        );

        List<Payment> payments =
                paymentDAO.getAllPayments();

        for (Payment payment : payments) {

            String studentName =
                    getStudentNameFromInvoice(
                            payment.getInvoiceId()
                    );

            Object[] row = {

                    payment.getPaymentId(),

                    payment.getInvoiceId(),

                    studentName,

                    payment.getPaymentDate(),

                    payment.getAmountPaid(),

                    payment.getPaymentMethod(),

                    payment.getStatus()
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

                return student.getFirstName()
                        + " "
                        + student.getLastName();
            }
        }

        return "Unknown";
    }

    // =========================
    // GET STUDENT FROM INVOICE
    // =========================

    private String getStudentNameFromInvoice(
            int invoiceId
    ) {

        for (Invoice invoice : invoices) {

            if (invoice.getInvoiceId()
                    == invoiceId) {

                return getStudentName(
                        invoice.getStudentId()
                );
            }
        }

        return "Unknown";
    }

    // =========================
    // SELECT TABLE ROW
    // =========================

    private void fillFieldsFromSelectedRow() {

        int selectedRow =
                paymentTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            return;
        }

        int invoiceId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        1
                                )
                                .toString()
                );

        selectInvoiceById(
                invoiceId
        );

        LocalDate paymentDate =
                LocalDate.parse(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        3
                                )
                                .toString()
                );

        Date paymentDateValue =
                Date.from(
                        paymentDate
                                .atStartOfDay(
                                        ZoneId.systemDefault()
                                )
                                .toInstant()
                );

        paymentDateChooser.setDate(
                paymentDateValue
        );

        amountPaidField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString()
        );

        String paymentMethod =
                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString();

        paymentMethodComboBox.setSelectedItem(
                paymentMethod
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
    // SELECT INVOICE BY ID
    // =========================

    private void selectInvoiceById(
            int invoiceId
    ) {

        for (
                int i = 0;
                i < invoices.size();
                i++
        ) {

            if (invoices
                    .get(i)
                    .getInvoiceId()
                    == invoiceId) {

                invoiceComboBox
                        .setSelectedIndex(i);

                return;
            }
        }
    }

    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        invoiceComboBox.setSelectedIndex(
                -1
        );

        paymentDateChooser.setDate(
                new Date()
        );

        amountPaidField.setText(
                ""
        );

        paymentMethodComboBox.setSelectedIndex(
                0
        );

        statusComboBox.setSelectedIndex(
                0
        );

        paymentTable.clearSelection();
    }
}