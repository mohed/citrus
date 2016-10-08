package com.ecocitrus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by Administrator on 2016-10-05.
 */

@RestController
public class InvoiceController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @RequestMapping(value = "/revision", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView revisionPage(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView("revision");
        String savedUsername = (String) httpSession.getAttribute("username");
        Long userId = usersRepository.findByUsername(savedUsername).getUserID();
        if (userId != null) {
            Iterable<Invoice> invoices = invoiceRepository.findByUserId(userId);
            modelAndView.addObject("invoices", invoices);
        }
        return modelAndView;
    }

    @GetMapping("addinvoice")
    public ModelAndView userStartPage(@RequestParam Long userId) {
        return new ModelAndView("addInvoice")
                .addObject("invoice", new Invoice())
                .addObject("paymentTypes", PaymentType.values())
                .addObject("intervals", Interval.values())
                .addObject("userId", userId);
    }

    @PostMapping("addinvoice")
    public ModelAndView startAndPost(@Valid Invoice invoice, BindingResult bindingResult, @RequestParam Long userId) {

        if (bindingResult.hasErrors()) {
            System.out.println(invoice.toString());
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

    @GetMapping("/main")
    public ModelAndView main() {
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/headerIndex")
    public ModelAndView hi(HttpSession session) {
        if (session.getAttribute("username") != null) {
            ModelAndView modelAndView = new ModelAndView("header");
            modelAndView.addObject("logged", session.getAttribute("username"));
            return modelAndView;
        }
        return new ModelAndView("headerIndex");
    }
}