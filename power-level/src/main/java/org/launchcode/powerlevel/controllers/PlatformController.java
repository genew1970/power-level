package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.models.Developers;
import org.launchcode.powerlevel.models.Platforms;
import org.launchcode.powerlevel.models.data.GamesDao;
import org.launchcode.powerlevel.models.data.PlatformsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by genew on 7/8/2017.
 */

@Controller
@RequestMapping(value="platform")
public class PlatformController {

    @Autowired
    private PlatformsDao platformsDao;

    // handles the  platform index page
    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("title", "Platforms");
        model.addAttribute("platforms", platformsDao.findAll());

        return "platform/index";
    }

    // handles the add-platform page
    @RequestMapping(value="add-platform", method = RequestMethod.GET)
    public String addPlatform(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute(new Platforms());

        return "platform/add-platform";
    }

    // POST updates the database with the new info
    @RequestMapping (value = "add-platform", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Platforms platforms, Errors errors){

        if (errors.hasErrors()){
            model.addAttribute("title","Admin");

            return "platform/add-platform";
        }

        platformsDao.save(platforms);
        return "redirect:";
    }
}
