package com.kindergarten.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {

    private int paymentId;
    private int invoiceId;
    private LocalDate paymentDate;
    private BigDecimal amountPaid;
    private String paymentMethod;
    private String status;

    public Payment() {
    }

    public Payment(int paymentId,
                   int invoiceId,
                   LocalDate paymentDate,
                   BigDecimal amountPaid,
                   String paymentMethod,
                   String status) {

        this.paymentId = paymentId;
        this.invoiceId = invoiceId;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}