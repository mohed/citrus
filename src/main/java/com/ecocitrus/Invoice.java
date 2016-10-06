package com.ecocitrus;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Created by Administrator on 2016-10-05.
 */

@Entity
@Table
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="invoiceid")
    private long invoiceid;

    @NotNull
    private int paymenttypeId;
    private long userId;

    @NotNull
    private Date duedate;

    @Min(1)
    @Max(1000000)
    @NotNull
    private int amount;


    @NotNull
    @Min(1)
    private int interval;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    protected Invoice() {
    }

    public Invoice(long userId, String name, int amount, Date duedate, int interval, PaymentType paymentType_Id) {
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.duedate = duedate;
        this.interval = interval;
        this.paymenttypeId = paymentType_Id.ordinal();
    }

    public long getInvoiceid() {
        return invoiceid;
    }

    public PaymentType getPaymenttypeId() {
        return PaymentType.values()[paymenttypeId];
    }

    public void setPaymenttypeId(PaymentType paymenttypeId) {
        this.paymenttypeId = paymenttypeId.ordinal();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceid=" + invoiceid +
                ", paymenttypeId=" + paymenttypeId +
                ", userId=" + userId +
                ", duedate=" + duedate +
                ", amount=" + amount +
                ", interval=" + interval +
                ", name='" + name + '\'' +
                '}';
    }
}