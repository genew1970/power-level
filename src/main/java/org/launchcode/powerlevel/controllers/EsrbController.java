package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.models.Esrb;
import org.launchcode.powerlevel.services.EsrbService;
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
@RequestMapping("esrb")
public class EsrbController {

    private static final Logger logger = LoggerFactory.getLogger(EsrbController.class);

    @Autowired
    private EsrbService esrbService;

    // handles the add page
    @RequestMapping(value="add", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute("esrb", esrbService.findAll());
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

        esrbService.save(esrb);
        logger.info("New ESRB rating added: {}", esrb.getName());
        return "redirect:/esrb/add";
    }
}
