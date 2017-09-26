package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.models.data.GamesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by genew on 6/28/2017.
 */

@Controller
@RequestMapping("")
public class HomeController {

    // handles the user login page
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("title", "Admin");

        return "index";
    }

    /*@RequestMapping(value = "", method = RequestMethod.POST)
    public String login(Model model) {

        model.addAttribute("title", "Admin");

        return "admin";
    }*/
}
