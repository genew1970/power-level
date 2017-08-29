package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.models.Developers;
import org.launchcode.powerlevel.models.Esrb;
import org.launchcode.powerlevel.models.Games;
import org.launchcode.powerlevel.models.Platforms;
import org.launchcode.powerlevel.models.data.DevelopersDao;
import org.launchcode.powerlevel.models.data.EsrbDao;
import org.launchcode.powerlevel.models.data.GamesDao;
import org.launchcode.powerlevel.models.data.PlatformsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by genew on 7/6/2017.
 */


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
