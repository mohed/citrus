package com.ecocitrus;

import javax.persistence.*;
import java.sql.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2016-10-05.
 */
@Entity
@Table
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long InvoiceID;
    @NotNull
    private PaymentType paymenttypeId;
    private long userId;
    @NotNull
    private Date duedate;
    @Min(1)
    @Max(1000000)
    @NotNull
    @Column(name = "Amount")
    private int amount;
    @NotNull
    @Min(1)
    @Column(name = "Interval")
    private int interval;
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    protected Invoice() {
    }

    public Invoice(long userID, String name, int amount, Date duedate, int interval, PaymentType paymentType_ID) {
        this.userId = userID;
        this.name = name;
        this.amount = amount;
        this.duedate = duedate;
        this.interval = interval;
        this.paymenttypeId = paymentType_ID;
    }

    public long getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(long invoiceID) {
        this.InvoiceID = invoiceID;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userID) {
        this.userId = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public PaymentType getPaymentType_ID() {
        return paymenttypeId;
    }

    public void setPaymentType_ID(PaymentType paymentType_ID) {
        this.paymenttypeId = paymentType_ID;
    }
}
