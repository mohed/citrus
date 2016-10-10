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


    //session.invalidate();
    //session.setAttribiute("name", value);
    //session.getAttribiute("name");


    @GetMapping("/")
    public ModelAndView newIndex(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return new ModelAndView("redirect:/revision");
        }
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

    @GetMapping("logout")
    public ModelAndView logout(HttpSession httpSession) {
        httpSession.invalidate();
        return new ModelAndView("redirect:/");
    }

    @GetMapping("adduser")
    public ModelAndView goToAddUserPage(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView("adduser");
        String message = (String) httpSession.getAttribute("addUserMessage");
        modelAndView.addObject("addUserMessage", message);
        return modelAndView;

    }

    @PostMapping("adduser")
    public String createUser(HttpSession httpSession, @RequestParam String username, @RequestParam String password) {

        int hashedPassword = generateHash(password);
        Users usersToAdd = new Users(username, hashedPassword);
        usersRepository.save(usersToAdd);
        httpSession.setAttribute("username", username);
        return "redirect:/index.html";
    }

    @GetMapping("addinvoice")
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
            for (Invoice invoice : invoices) {
                System.out.println(invoice.toStringFull());
                LocalDate date = invoice.getDuedate().toLocalDate();
                System.out.println(date.getMonth());
                if (date.getMonth() == LocalDate.now().getMonth() && date.getYear() == LocalDate.now().getYear()) {
                    dueInvoices.add(invoice);
                }
            }
            modelAndView.addObject("invoices", dueInvoices)
                    .addObject("invoicesToPay", new InvoicesToPay())
                    .addObject("userId", userId)
                    .addObject("dateToday", dt);
        }
        return modelAndView;
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

    @PostMapping("/markAsPaid")
    public ModelAndView markAsPaid(@ModelAttribute InvoicesToPay markAsPaid){

        for (Long invoiceid : markAsPaid.getInvoicesToPay()) {
            Invoice invoice = invoiceRepository.findByInvoiceid(invoiceid);
            //save invoice to paid invoices
            paidInvoiceDatesRepository.save(new Paidinvoicedates(invoiceid, invoiceRepository.findByInvoiceid(invoiceid).getDuedate(), Date.valueOf(LocalDate.now())));
            if(invoice.getInterval().getNumValue() != null){
                //set new DueDate and update LastPaid, then save invoice
                invoice.setDuedate(Date.valueOf(LocalDate.now().plusMonths(invoice.getInterval().getNumValue())));
            }
            invoice.setLastpaid(Date.valueOf(LocalDate.now()));
            invoiceRepository.save(invoice);
        }

        return new ModelAndView("redirect:/revision");
    }

    @GetMapping("/listAll")
    public ModelAndView listAll(HttpSession httpSession){
        String savedUsername = (String) httpSession.getAttribute("username");
        Long userId = usersRepository.findByUsername(savedUsername).getUserID();

        List<Invoice> invoices = invoiceRepository.findByUserIdOrderByDuedate(userId);
        Iterable<Paidinvoicedates> paidInvoicesAll = paidInvoiceDatesRepository.findAll();
        List<Paidinvoicedates> paidInvoices = new ArrayList<>();
        for (Invoice invoice: invoices) {
            for (Paidinvoicedates paid: paidInvoicesAll) {
                if(paid.getInvoiceId() == invoice.getInvoiceid()){
                    paidInvoices.add(paid);
                }
            }
        }

        return new ModelAndView("listAllInvoices")
                .addObject("paidInvoices", paidInvoices)
                .addObject("invoices", invoices);
    }

    private int generateHash(String string) {
        int hash = 7;
        for (int i = 0; i < string.length(); i++) {
            hash = hash * 31 + string.charAt(i);
        }
        return hash;
    }

    private boolean checkCredentials(Users user, String password) {
        int hash = generateHash(password);
        if (user.getPassword() == hash) {
            return true;
        }

        return false;
    }
}

