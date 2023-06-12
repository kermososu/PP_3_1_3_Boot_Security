package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("admin/new")
    public String newUser(ModelMap model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/allUsers")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin/allUsers";
    }

    @GetMapping("admin/edit/{id}")
    public String update(ModelMap model, @PathVariable("id") long id) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("admin/{id}")
    public String edit(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/allUsers";
    }

    @GetMapping("/admin/allUsers")
    public String showAllUsers(ModelMap model) {
        model.addAttribute("users", userService.readAllUsers());
        return "users";
    }

    @GetMapping("/admin/remove/{id}")
    public String remove(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/allUsers";
    }

}
