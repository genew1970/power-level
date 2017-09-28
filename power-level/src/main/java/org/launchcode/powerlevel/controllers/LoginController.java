package org.launchcode.powerlevel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by genew on 9/26/2017.
 */
@Controller
@RequestMapping("login")
public class LoginController {
    // handles the user login page
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String login(Model model) {

        model.addAttribute("title", "Admin");

        return "login/index";
    }
}
