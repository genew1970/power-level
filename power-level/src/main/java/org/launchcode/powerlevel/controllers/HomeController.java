package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.models.data.GamesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by genew on 6/28/2017.
 */

@Controller
@RequestMapping("")
public class HomeController {

    // handles the user login page
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("title", "Admin");

        return "index";
    }
}
