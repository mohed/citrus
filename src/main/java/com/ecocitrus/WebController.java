package com.ecocitrus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Created by Administrator on 2016-10-05.
 */

@RestController
public class WebController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    PaidInvoiceDatesRepository paidInvoiceDatesRepository;


    //session.invalidate();
    //session.setAttribiute("name", value);
    //session.getAttribiute("name");


    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @PostMapping("login")
    public ModelAndView login(HttpSession httpSession, @RequestParam String username) {
        Users users = usersRepository.findByUsername(username);
        System.out.println(users.toString());
        if (users != null) {
            httpSession.setAttribute("username", users.getUsername());
            httpSession.setAttribute("loginMessage", "Logged in");
            return new ModelAndView("redirect:./revision");
        }
        return new ModelAndView("redirect:/").addObject("loginMessage", "User not found.");
    }

    @GetMapping("adduser")
    public ModelAndView goToAddUserPage(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView("adduser");
        String message = (String) httpSession.getAttribute("addUserMessage");
        modelAndView.addObject("addUserMessage", message);
        return modelAndView;

    }

    @PostMapping("adduser")
    public String createUser(HttpSession httpSession, @RequestParam String username) {
                             // @RequestParam String name, @RequestParam String password1, @RequestParam String password2, ) {

        Users usersToAdd = new Users(username);
        usersRepository.save(usersToAdd);
        //User user = dBRepository.getUser(username);
        httpSession.setAttribute("user", username);
        return "redirect:/index.html";
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
            System.out.println(invoice.toString()
            );
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

    @RequestMapping(value="/revision", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView revisionPage(HttpSession httpSession) {
       // if (httpSession. == null)
        ModelAndView modelAndView = new ModelAndView("revision");
//        httpSession.setAttribute("username", username);
        String savedUsername = (String)httpSession.getAttribute("username");
        Long userId = usersRepository.findByUsername(savedUsername).getUserID();

//        Long userId = usersRepository.findByUsername(username).getUserID();
        System.out.println(userId.toString());
        if (userId != null) {
            Iterable<Invoice> invoices = invoiceRepository.findByUserId(userId);
            modelAndView.addObject("invoices", invoices)
                    .addObject("userId", userId)
                    .addObject("invoicesToPay", new InvoicesToPay());
        }
        return modelAndView;
    }
    @GetMapping("/main")
    public ModelAndView main() {
        return  new ModelAndView("redirect:/");
    }
    @GetMapping("/headerIndex")
    public ModelAndView hi (HttpSession session) {
        if (session.getAttribute("username") != null) {
            ModelAndView modelAndView = new ModelAndView("header");
            modelAndView.addObject("logged", session.getAttribute("username"));
            return modelAndView;
        }
        return new ModelAndView("headerIndex");
    }

    @PostMapping("/markAsPaid")
    public ModelAndView markAsPaid(@ModelAttribute InvoicesToPay markAsPaid){

        for (Long invoiceid : markAsPaid.getInvoicesToPay()) {
            Invoice invoice = invoiceRepository.findByInvoiceid(invoiceid);
            paidInvoiceDatesRepository.save(new Paidinvoicedates(invoiceid, invoiceRepository.findByInvoiceid(invoiceid).getDuedate(), Date.valueOf(LocalDate.now())));
            invoice.setDuedate(Date.valueOf(LocalDate.now().plusMonths(invoice.getInterval().getNumValue())));
            invoiceRepository.save(invoice);
        }

        return new ModelAndView("/revision");
    }
}

//login middle page
//if no session check login credentials
//create sesion
//
//else if session
//normal revision logic