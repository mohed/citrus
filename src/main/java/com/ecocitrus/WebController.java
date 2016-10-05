package com.ecocitrus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2016-10-05.
 */
@RestController
public class WebController {

    @Autowired
    UsersRepository usersRepository;
    InvoiceRepository invoiceRepository;

    @GetMapping("addinvoice")
    public ModelAndView userStartPage() {
        return new ModelAndView("addInvoice")
                .addObject("invoice", new Invoice())
                .addObject("paymentTypes", PaymentType.values());
    }

    @PostMapping("addinvoice")
    public ModelAndView startAndPost(@Valid Invoice invoice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("addInvoice")
                    .addObject("invoice", invoice);
        }
        return new ModelAndView("addInvoice")
                .addObject("invoice", invoice)
                .addObject("paymentTypes", PaymentType.values());
    }

    @PostMapping("revision")
    public ModelAndView revisionPage(@RequestParam String username) {
        Long userId = usersRepository.findByUsername(username).getUserID();
        List<Invoice> invoices = invoiceRepository.findByUserId(userId);
        return new ModelAndView("revision").addObject("invoices", invoices);
    }
}
