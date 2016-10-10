package com.ecocitrus;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Administrator on 2016-10-07.
 */
@Entity
@Table
public class Paidinvoicedates {

    @Id
    @Column(name="invoice_id")
    private long invoiceId;
    private Date duedate;
    private Date paiddate;

    protected Paidinvoicedates (){

    }

    public Paidinvoicedates(long invoiceId, Date duedate, Date paiddate) {
        this.invoiceId = invoiceId;
        this.duedate = duedate;
        this.paiddate = paiddate;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public Date getDuedate() {
        return duedate;
    }

    public Date getPaiddate() {
        return paiddate;
    }
}

