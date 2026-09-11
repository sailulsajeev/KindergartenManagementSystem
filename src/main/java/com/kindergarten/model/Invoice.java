package com.kindergarten.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Invoice {

    private int invoiceId;
    private int studentId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal amountDue;
    private String status;

    public Invoice() {
    }

    public Invoice(int invoiceId,
                   int studentId,
                   LocalDate issueDate,
                   LocalDate dueDate,
                   BigDecimal amountDue,
                   String status) {

        this.invoiceId = invoiceId;
        this.studentId = studentId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.amountDue = amountDue;
        this.status = status;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}