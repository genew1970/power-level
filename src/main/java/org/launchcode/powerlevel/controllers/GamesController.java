package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.models.Developers;
import org.launchcode.powerlevel.models.Esrb;
import org.launchcode.powerlevel.models.Games;
import org.launchcode.powerlevel.models.Platforms;
import org.launchcode.powerlevel.models.data.DevelopersDao;
import org.launchcode.powerlevel.models.data.EsrbDao;
import org.launchcode.powerlevel.models.data.GamesDao;
import org.launchcode.powerlevel.models.data.PlatformsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by genew on 7/10/2017.
 */

@Controller
@RequestMapping("games")
public class GamesController {
    // wiring the databases to the appropriate class objects
    @Autowired
    private GamesDao gamesDao;

    @Autowired
    private PlatformsDao platformsDao;

    @Autowired
    private DevelopersDao developersDao;

    @Autowired
    private EsrbDao esrbDao;

    // views the information on one game
    @RequestMapping(value = "view-game", method = RequestMethod.GET)
    public String index(Model model, int id) {

        Games games = gamesDao.findOne(id);

        // formats the percentage     .decFormat formats decimals to two places
        double profit = ((games.getPrice() - games.getCost())/games.getPrice()) * 100;

        model.addAttribute("title", "Power Level - Admin");
        model.addAttribute("games", games);
        model.addAttribute("cost", games.decFormat(games.getCost()));
        model.addAttribute("price", games.decFormat(games.getPrice()));
        model.addAttribute("platforms", games.getPlatforms().getName());
        model.addAttribute("developers", games.getDevelopers().getName());
        model.addAttribute("esrb", games.getEsrb().getName());
        model.addAttribute("profit", games.decFormat(profit));

        return "games/view-game";
    }

    // handles user request to edit the information
    @RequestMapping(value = "view-game", method = RequestMethod.POST)
    public String addAnother(Model model, int id, Games games) {

        games = gamesDao.findOne(id);

        model.addAttribute("title", "Admin");
        model.addAttribute(new Games());
        model.addAttribute("developers", developersDao.findAll());
        model.addAttribute("platforms", platformsDao.findAll());
        model.addAttribute("esrb", esrbDao.findAll());

        return "games/edit-game/?id=" + games.getId();
    }

    // handles the add-game page
    @RequestMapping(value = "add-game", method = RequestMethod.GET)
    public String addGame(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute(new Games());
        model.addAttribute("developers", developersDao.findAll());
        model.addAttribute("platforms", platformsDao.findAll());
        model.addAttribute("esrb", esrbDao.findAll());

        return "games/add-game";
    }

    // add-game POST method saves the information to the database
    @RequestMapping(value = "add-game", method = RequestMethod.POST)
    public String addGame(Model model, @ModelAttribute @Valid Games games, Errors errors,
                          @RequestParam int platformsId, @RequestParam int developersId,
                          @RequestParam int esrbId){

        // displays error messages
        if(errors.hasErrors()){
            model.addAttribute("platforms", platformsDao.findAll());
            model.addAttribute("developers", developersDao.findAll());
            model.addAttribute("esrb", esrbDao.findAll());
            model.addAttribute("title", "Admin");

            return "games/add-game";
        }

        games.setDevelopers(developersDao.findOne(developersId));
        games.setPlatforms(platformsDao.findOne(platformsId));
        games.setEsrb(esrbDao.findOne(esrbId));
        gamesDao.save(games);

        model.addAttribute("platforms", platformsDao.findAll());
        model.addAttribute("developers", developersDao.findAll());
        model.addAttribute("esrb", esrbDao.findAll());
        model.addAttribute("title", "Admin");

        return"games/list-games";
    }

    // list-games lists links to individual games
    @RequestMapping(value = "list-games", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute(new Games());
        model.addAttribute("title", "Admin");
        model.addAttribute("games", gamesDao.findAll());

        return "games/list-games";
    }

    // displays the individual game by taking in the id for the game
    @RequestMapping(value = "list-games", method = RequestMethod.POST)
    public String listGame(Model model, int id, Games games){
        Games theGame = gamesDao.findOne(id);

        model.addAttribute("game", theGame);
        model.addAttribute("platforms", games.getPlatforms());
        model.addAttribute("developers", games.getDevelopers());
        model.addAttribute("esrb", esrbDao.findAll());
        model.addAttribute("title", "Admin");
        model.addAttribute(new Games());

        return "redirect:/games/view-game/?id=" + games.getId();
    }

    // displays the information within each field to edit the game
    @RequestMapping(value = "edit-game", method = RequestMethod.GET)
    public String editGame(Model model, int id, Games games) {
        Games theGame = gamesDao.findOne(id);

        model.addAttribute("game", theGame);
        model.addAttribute("platforms", platformsDao.findAll());
        model.addAttribute("developers", developersDao.findAll());
        model.addAttribute("esrb", esrbDao.findAll());
        model.addAttribute("title", "Admin");
        model.addAttribute(new Games());

        return "games/edit-game";
    }

    // the POST request updates the current indexed value in the database
    @RequestMapping(value = "edit-game", method = RequestMethod.POST)
    public String updateGame(Model model, int id, Games games,
                             @RequestParam int platformsId,
                             @RequestParam int developersId,
                             @RequestParam int esrbId) {
        Games theGame = gamesDao.findOne(id);
        theGame.setPrice(games.getPrice());
        theGame.setCost(games.getCost());
        theGame.setPlayers(games.getPlayers());
        theGame.setName(games.getName());
        theGame.setQuantity(games.getQuantity());
        theGame.setDescription(games.getDescription());

        theGame.setPlatforms(platformsDao.findOne(platformsId));
        theGame.setDevelopers(developersDao.findOne(developersId));
        theGame.setEsrb(esrbDao.findOne(esrbId));

        gamesDao.save(theGame);

        return "redirect:/games/list-games";
    }

    // search is not fully implemented
    @RequestMapping(value = "search-game", method = RequestMethod.GET)
    public String searchGame(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute(new Games());

        return "games/search-game";
    }

    // search is not fully implemented
    @RequestMapping(value = "search-game", method = RequestMethod.POST)
    public String resultGame(Model model) {

        Iterable<Games> theGame = gamesDao.findAll();
        /*for(Games game : theGame){
            if(game.getName().equals(searchTerm))
        }*/

        model.addAttribute("title", "Admin");
        model.addAttribute(new Games());

        return "games/search-game";
    }
}
