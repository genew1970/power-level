package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.WebSecurityConfig;
import org.launchcode.powerlevel.models.data.GamesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by genew on 6/28/2017.
 */

@Controller
@RequestMapping("")
public class AdminController {
    // handles the Admin index
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {

        return "index";
    }

}
