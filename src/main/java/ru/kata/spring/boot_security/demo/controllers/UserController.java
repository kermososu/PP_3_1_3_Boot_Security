package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;



@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/registration")
    public String newUser(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping()
    public String registration(@ModelAttribute("user") @Valid User user) {
        if (!userService.createUser(user)) {
            return "registration";
        }
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String user(ModelMap model, Principal principal) {
        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
        return "user";
    }
}
