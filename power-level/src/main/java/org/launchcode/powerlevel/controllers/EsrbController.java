package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.models.Developers;
import org.launchcode.powerlevel.models.Esrb;
import org.launchcode.powerlevel.models.data.DevelopersDao;
import org.launchcode.powerlevel.models.data.EsrbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by genew on 7/11/2017.
 */

/* this portion of code is not wired into the site.  it was to get the ESRB ratings set in the database
   the esrb's should probably not change that much  */
@Controller
@RequestMapping("esrb")
public class EsrbController {
    @Autowired
    EsrbDao esrbDao;

    // handles the add page
    @RequestMapping(value="add", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute("esrb", esrbDao.findAll());
        model.addAttribute(new Esrb());
        return "esrb/add";
    }

    // POST method updates the ESRB database
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Esrb esrb, Errors errors){
        if (errors.hasErrors()){

            model.addAttribute("title","Admin");

            return "esrb/add";
        }

        esrbDao.save(esrb);
        return "redirect:";
    }
}
