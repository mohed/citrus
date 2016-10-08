package com.ecocitrus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


/**
 * Created by Administrator on 2016-10-05.
 */

@RestController
public class UserController {

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/")
    public ModelAndView index(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return new ModelAndView("redirect:/revision");
        }
        return new ModelAndView("index");
    }

    @PostMapping("login")
    public ModelAndView login(HttpSession httpSession, @RequestParam String username) {
        Users user = usersRepository.findByUsername(username);
        if (user != null) {
            httpSession.setAttribute("username", username);
            httpSession.setAttribute("userId", user.getUserID());
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
        Users userToAdd = new Users(username, hashedPassword);
        usersRepository.save(userToAdd);
        httpSession.setAttribute("username", username);
        return "redirect:/index.html";
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