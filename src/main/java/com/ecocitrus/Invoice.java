package com.ecocitrus;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String name;
    private int amount;
    private LocalDateTime dueDate;
    private int interval;
    private PaymentType paymentType;

    protected Invoice() {
    }

    public Invoice(long userId, String name, int amount, LocalDateTime dueDate, int interval, PaymentType paymentType) {
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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
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
