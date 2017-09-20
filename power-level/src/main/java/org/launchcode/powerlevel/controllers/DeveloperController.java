package org.launchcode.powerlevel.controllers;

import org.hibernate.Session;
import org.launchcode.powerlevel.models.Developers;
import org.launchcode.powerlevel.models.data.DevelopersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;


/**
 * Created by genew on 7/8/2017.
 */

@Controller
@RequestMapping("developer")
public class DeveloperController {

    @Autowired
    DevelopersDao developersDao;

    // handles the edit-game page
    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute("developers", developersDao.findAll());

        return "developer/index";
    }

    // passes the id to the edit-developer during POST
    @RequestMapping(value="", method = RequestMethod.POST)
    public String selectDeveloper(Model model, Developers developers) {

        model.addAttribute("title", "Admin");
        return "redirect:/developer/edit-developer/?id=" + developers.getId();
    }

    // handles the add-developer page
    @RequestMapping(value="add-developer", method = RequestMethod.GET)
    public String addDeveloper(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute(new Developers());

        return "developer/add-developer";
    }

    // POST method add the developer name and info to the database
    @RequestMapping (value = "add-developer", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Developers developers, Errors errors){

        // handles errors on the page
        if (errors.hasErrors()){
            model.addAttribute("title","Admin");

            return "developer/add-developer";
        }

        developersDao.save(developers);
        return "redirect:";
    }

    // handles the edit-developer page, displaying the indexed value from the database
    @RequestMapping (value = "edit-developer", method = RequestMethod.GET)
    public String editDevelopers(Model model, int id) {

        Developers developers = developersDao.findOne(id);

        model.addAttribute("title", "Admin");
        model.addAttribute("developers", developers);

        return "developer/edit-developer";
    }

    // updates the current indexed data
    @RequestMapping(value="edit-developer", method =  RequestMethod.POST)
    public String replaceDeveloper(Model model, int id, @ModelAttribute @Valid Developers developers,
                                   Errors errors) {

        // handles the errors in the input fields
        if (errors.hasErrors()) {
            model.addAttribute("title","Admin");
            return "developer/edit-developer";
        }

        model.addAttribute("title","Admin");
        model.addAttribute("developers", developersDao.findAll());

        Developers theDeveloper = developersDao.findOne(id);
        theDeveloper.setName(developers.getName());
        theDeveloper.setEmail(developers.getEmail());
        theDeveloper.setPhone(developers.getPhone());

        developersDao.save(theDeveloper);

        return "developer/index";

    }
}
