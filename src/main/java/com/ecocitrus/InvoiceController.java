package com.ecocitrus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-10-05.
 */

@RestController
public class InvoiceController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    PaidInvoiceDatesRepository paidInvoiceDatesRepository;

    @GetMapping("addInvoice")
    public ModelAndView userStartPage(HttpSession httpSession) {
        return new ModelAndView("addInvoice")
                .addObject("invoice", new Invoice())
                .addObject("paymentTypes", PaymentType.values())
                .addObject("intervals", Interval.values())
                .addObject("userId", usersRepository.findByUsername((String)httpSession.getAttribute("username")).getUserID());
    }

    @PostMapping("addinvoice")
    public ModelAndView startAndPost(HttpSession httpSession, @Valid Invoice invoice, BindingResult bindingResult) {
        Long userId = usersRepository.findByUsername((String)httpSession.getAttribute("username")).getUserID();
        if (bindingResult.hasErrors()) {
            return new ModelAndView("addInvoice")
                    .addObject("invoice", invoice)
                    .addObject("paymentTypes", PaymentType.values())
                    .addObject("intervals", Interval.values())
                    .addObject("userId", userId);
        }
        invoice.setUserId(userId);
        invoiceRepository.save(invoice);
        return new ModelAndView("addInvoice")
                .addObject("invoice", new Invoice())
                .addObject("paymentTypes", PaymentType.values())
                .addObject("intervals", Interval.values())
                .addObject("userId", userId);
    }

    @RequestMapping(value = "/revision", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView revisionPage(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView("revision");
        String savedUsername = (String) httpSession.getAttribute("username");
        Long userId = usersRepository.findByUsername(savedUsername).getUserID();
        System.out.println(userId.toString());
        Date dt = Date.valueOf(LocalDate.now());

        if (userId != null) {
            Iterable<Invoice> invoices = invoiceRepository.findByUserIdOrderByDuedate(userId);
            List<Invoice> dueInvoices = new ArrayList<>();
            List<Invoice> paidInvoices = new ArrayList<>();
            for (Invoice invoice : invoices) {
                System.out.println(invoice.toStringFull());
                LocalDate date = invoice.getDuedate().toLocalDate();
                if (date.getMonth() == LocalDate.now().getMonth() && date.getYear() == LocalDate.now().getYear()) {
                    if (invoice.getLastpaid() == null || invoice.getLastpaid().toLocalDate().getMonthValue() != LocalDate.now().getMonthValue()) {
                        dueInvoices.add(invoice);
                    }
                    else {
                        paidInvoices.add(invoice);
                    }
                }
            }
            modelAndView.addObject("invoices", dueInvoices)
                    .addObject("invoicesToPay", new InvoicesToPay())
                    .addObject("userId", userId)
                    .addObject("dateToday", dt);
            modelAndView.addObject("paidInvoices", paidInvoices)
                    .addObject("invoicesToUnPay", new InvoicesToUnPay())
                    .addObject("userId", userId)
                    .addObject("dateToday", dt);
        }
        return modelAndView;
    }

    @PostMapping("/markAsPaid")
    public ModelAndView markAsPaid(@ModelAttribute InvoicesToPay markAsPaid){

        for (Long invoiceid : markAsPaid.getInvoicesToPay()) {
            Invoice invoice = invoiceRepository.findByInvoiceid(invoiceid);
            //save invoice to paid invoices
            paidInvoiceDatesRepository.save(new Paidinvoicedates(invoiceid, invoiceRepository.findByInvoiceid(invoiceid).getDuedate(), Date.valueOf(LocalDate.now())));
            if(invoice.getInterval().getNumValue() != null){
                invoice.setDuedate(Date.valueOf(invoice.getDuedate().toLocalDate().plusMonths(invoice.getInterval().getNumValue())));
            }
            invoice.setLastpaid(Date.valueOf(LocalDate.now()));
            invoiceRepository.save(invoice);
        }

        return new ModelAndView("redirect:/revision");
    }
    @PostMapping("/markAsUnPaid")
    public ModelAndView markAsUnPaid(@ModelAttribute InvoicesToUnPay markAsUnPaid){

        for (Long invoiceid : markAsUnPaid.getInvoicesToUnPay()) {
            Invoice invoice = invoiceRepository.findByInvoiceid(invoiceid);
//            paidInvoiceDatesRepository.delete(paidInvoiceDatesRepository.findByPaiddateAndInvoiceid())
            if(invoice.getInterval().getNumValue() != null){
                invoice.setLastpaid(Date.valueOf(LocalDate.now().minusMonths(invoice.getInterval().getNumValue())));
            }
            else {
                invoice.setLastpaid(null);
            }
            invoiceRepository.save(invoice);
        }

        return new ModelAndView("redirect:/revision");
    }

    @GetMapping("/listAll")
    public ModelAndView listAll(HttpSession httpSession){
        String savedUsername = (String) httpSession.getAttribute("username");
        Long userId = usersRepository.findByUsername(savedUsername).getUserID();
        List<Invoice> invoices = invoiceRepository.findByUserIdOrderByDuedate(userId);
        /*Iterable <Paidinvoicedates> paidInvoicesAll = paidInvoiceDatesRepository.findAll();*/
       /* Iterable<Paidinvoicedates> paidInvoicesAll = paidInvoiceDatesRepository.findByInvoiceIdOrderByDuedateDesc(31L);*/
        List<Paidinvoicedates> paidInvoices = new ArrayList<>();
        for (Invoice invoice: invoices) {
            paidInvoices.addAll(paidInvoiceDatesRepository.findByInvoiceIdOrderByDuedateDesc(invoice.getInvoiceid()));
        }
        return new ModelAndView("listAllInvoices")
                .addObject("paidInvoices", paidInvoices)
                .addObject("invoices", invoices);
    }
}

