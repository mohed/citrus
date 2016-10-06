package com.ecocitrus;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * Created by Administrator on 2016-10-05.
 */
@Controller
public class WebController {

   @GetMapping("addinvoice")
    public ModelAndView userStartPage(){
       return new ModelAndView("addInvoice")
               .addObject("invoice", new Invoice())
               .addObject("paymentTypes", PaymentType.values());

   }

   @PostMapping("addinvoice")
    public ModelAndView startAndPost(@Valid Invoice invoice, BindingResult bindingResult){
       if (bindingResult.hasErrors()) {
           return new ModelAndView("addInvoice")
                   .addObject("invoice", invoice)
                   .addObject("paymentTypes", PaymentType.values());

       }

       return new ModelAndView("addInvoice")
               .addObject("invoice", invoice)
               .addObject("paymentTypes", PaymentType.values());

   }



}
