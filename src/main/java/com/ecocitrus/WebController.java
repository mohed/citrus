package com.ecocitrus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 2016-10-05.
 */
@RestController
public class WebController {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("addinvoice")
    public ModelAndView userStartPage() {
        return new ModelAndView("addInvoice")
                .addObject("invoice", new Invoice());
    }

    @PostMapping("revision")
    public ModelAndView revisionPage(@RequestParam String username) {
        Long userId = usersRepository.findByUsername(username).getUserID();
        List<Invoice> invoices = invoiceRepository.findByUserId(userId);
        return new ModelAndView("revision").addObject("invoices", invoices);
    }
}