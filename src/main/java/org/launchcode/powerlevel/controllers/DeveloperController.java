package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.models.Developers;
import org.launchcode.powerlevel.services.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("developer")
public class DeveloperController {

    private static final Logger logger = LoggerFactory.getLogger(DeveloperController.class);

    @Autowired
    private DeveloperService developerService;

    // handles the developer index page
    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute("developers", developerService.findAll());

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

        if (errors.hasErrors()){
            model.addAttribute("title","Admin");
            return "developer/add-developer";
        }

        developerService.save(developers);
        logger.info("New developer added: {}", developers.getName());
        return "redirect:/developer";
    }

    // handles the edit-developer page, displaying the indexed value from the database
    @RequestMapping (value = "edit-developer", method = RequestMethod.GET)
    public String editDevelopers(Model model, int id) {

        Developers developers = developerService.findById(id);

        model.addAttribute("title", "Admin");
        model.addAttribute("developers", developers);

        return "developer/edit-developer";
    }

    // updates the current indexed data
    @RequestMapping(value="edit-developer", method =  RequestMethod.POST)
    public String replaceDeveloper(Model model, int id, @ModelAttribute @Valid Developers developers,
                                   Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title","Admin");
            return "developer/edit-developer";
        }

        developerService.updateDeveloper(id, developers);
        logger.info("Developer updated with id: {}", id);

        model.addAttribute("title","Admin");
        model.addAttribute("developers", developerService.findAll());

        return "developer/index";
    }
}
