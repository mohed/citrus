package com.ecocitrus;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by Administrator on 2016-10-05.
 */
@Entity
@Table
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userId;
    @NotNull
    @Size(min=2, max=50)
    private String name;
    @Min(1)
    @Max(1000000)
    @NotNull
    private int amount;
    @NotNull
    private LocalDate dueDate;
    @NotNull
    @Min(1)
    private int interval;
    @NotNull
    private PaymentType paymentType;

    protected Invoice() {
    }

    public Invoice(long userId, String name, int amount, LocalDate dueDate, int interval, PaymentType paymentType) {
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.dueDate = dueDate;
        this.interval = interval;
        this.paymentType = paymentType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
