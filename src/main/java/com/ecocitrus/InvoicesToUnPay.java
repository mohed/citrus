package com.ecocitrus;

import java.util.List;

/**
 * Created by Administrator on 10/10/2016.
 */
public class InvoicesToUnPay {
    List<Long> invoicesToUnPay;

    public InvoicesToUnPay() {
    }

    public List<Long> getInvoicesToUnPay() {
        return invoicesToUnPay;
    }

    public void setInvoicesToUnPay(List<Long> invoicesToUnPay) {
        this.invoicesToUnPay = invoicesToUnPay;
    }
}
