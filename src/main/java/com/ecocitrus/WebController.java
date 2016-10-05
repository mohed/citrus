package com.ecocitrus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2016-10-05.
 */
@Controller
public class WebController {

   @GetMapping("addinvoice")
    public ModelAndView userStartPage(){
       return new ModelAndView("addInvoice")
               .addObject("invoice", new Invoice());
   }
}
