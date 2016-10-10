package com.ecocitrus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016-10-05.
 */

@RestController
public class UserController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @GetMapping("/")
    public ModelAndView newIndex(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return new ModelAndView("redirect:/revision");
        }
        return new ModelAndView("index").addObject("users", new Users());
    }

    @PostMapping("login")
    public ModelAndView login(HttpSession httpSession, @RequestParam String username, @RequestParam String password) {
        Users users = usersRepository.findByUsername(username);

        if (users == null) {
            httpSession.setAttribute("loginMessage", "No user found for the provided username");
            return new ModelAndView("redirect:/").addObject("loginMessage", "No user found for the provided username");

        } else if (users != null) {

            if (checkCredentials(users, password)) {
                httpSession.setAttribute("username", users.getUsername());
                httpSession.setAttribute("loginMessage", "Logged in");
                return new ModelAndView("redirect:./revision");
            } else {
                httpSession.setAttribute("loginMessage", "Wrong password");
                return new ModelAndView("redirect:/").addObject("loginMessage", "Wrong password");
            }
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
        modelAndView.addObject("addUserMessage", message)
                .addObject("users", new Users());
        return modelAndView;

    }

    @PostMapping("adduser")

    public ModelAndView createUser(HttpSession httpSession, @Valid Users user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("adduser")
                    .addObject("users", user);
        }
        usersRepository.save(user);
        httpSession.setAttribute("username", user.getUsername());
        return new ModelAndView("redirect:/");
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

    private boolean checkCredentials(Users user, String password) {
        int hash = user.generateHash(password);
        if (user.getPassword() == hash) {
            return true;
        }

        return false;
    }
}

