package org.launchcode.powerlevel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

    // handles the home page in the admin section
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute("username", "Gene");

        return "admin/index";
    }
}
