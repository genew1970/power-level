package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.models.Games;
import org.launchcode.powerlevel.services.DeveloperService;
import org.launchcode.powerlevel.services.EsrbService;
import org.launchcode.powerlevel.services.GameService;
import org.launchcode.powerlevel.services.PlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("games")
public class GamesController {

    private static final Logger logger = LoggerFactory.getLogger(GamesController.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private EsrbService esrbService;

    // views the information on one game
    @RequestMapping(value = "view-game", method = RequestMethod.GET)
    public String index(Model model, int id) {

        Games games = gameService.findById(id);
        double profit = gameService.calculateProfit(games);

        model.addAttribute("title", "Power Level - Admin");
        model.addAttribute("games", games);
        model.addAttribute("cost", gameService.formatDecimal(games.getCost()));
        model.addAttribute("price", gameService.formatDecimal(games.getPrice()));
        model.addAttribute("platforms", games.getPlatforms().getName());
        model.addAttribute("developers", games.getDevelopers().getName());
        model.addAttribute("esrb", games.getEsrb().getName());
        model.addAttribute("profit", gameService.formatDecimal(profit));

        return "games/view-game";
    }

    // handles user request to edit the information
    @RequestMapping(value = "view-game", method = RequestMethod.POST)
    public String addAnother(Model model, int id, Games games) {

        games = gameService.findById(id);

        model.addAttribute("title", "Admin");
        model.addAttribute(new Games());
        model.addAttribute("developers", developerService.findAll());
        model.addAttribute("platforms", platformService.findAll());
        model.addAttribute("esrb", esrbService.findAll());

        return "redirect:/games/edit-game/?id=" + games.getId();
    }

    // handles the add-game page
    @RequestMapping(value = "add-game", method = RequestMethod.GET)
    public String addGame(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute(new Games());
        model.addAttribute("developers", developerService.findAll());
        model.addAttribute("platforms", platformService.findAll());
        model.addAttribute("esrb", esrbService.findAll());

        return "games/add-game";
    }

    // add-game POST method saves the information to the database
    @RequestMapping(value = "add-game", method = RequestMethod.POST)
    public String addGame(Model model, @ModelAttribute @Valid Games games, Errors errors,
                          @RequestParam int platformsId, @RequestParam int developersId,
                          @RequestParam int esrbId){

        if(errors.hasErrors()){
            model.addAttribute("platforms", platformService.findAll());
            model.addAttribute("developers", developerService.findAll());
            model.addAttribute("esrb", esrbService.findAll());
            model.addAttribute("title", "Admin");
            return "games/add-game";
        }

        gameService.saveWithRelations(games, platformsId, developersId, esrbId);
        logger.info("New game added: {}", games.getName());

        model.addAttribute("platforms", platformService.findAll());
        model.addAttribute("developers", developerService.findAll());
        model.addAttribute("esrb", esrbService.findAll());
        model.addAttribute("title", "Admin");

        return "games/list-games";
    }

    // list-games lists links to individual games
    @RequestMapping(value = "list-games", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute(new Games());
        model.addAttribute("title", "Admin");
        model.addAttribute("games", gameService.findAll());

        return "games/list-games";
    }

    // displays the individual game by taking in the id for the game
    @RequestMapping(value = "list-games", method = RequestMethod.POST)
    public String listGame(Model model, int id, Games games){
        Games theGame = gameService.findById(id);

        model.addAttribute("game", theGame);
        model.addAttribute("platforms", games.getPlatforms());
        model.addAttribute("developers", games.getDevelopers());
        model.addAttribute("esrb", esrbService.findAll());
        model.addAttribute("title", "Admin");
        model.addAttribute(new Games());

        return "redirect:/games/view-game/?id=" + games.getId();
    }

    // displays the information within each field to edit the game
    @RequestMapping(value = "edit-game", method = RequestMethod.GET)
    public String editGame(Model model, int id, Games games) {
        Games theGame = gameService.findById(id);

        model.addAttribute("game", theGame);
        model.addAttribute("platforms", platformService.findAll());
        model.addAttribute("developers", developerService.findAll());
        model.addAttribute("esrb", esrbService.findAll());
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

        gameService.updateGame(id, games, platformsId, developersId, esrbId);
        logger.info("Game updated with id: {}", id);

        return "redirect:/games/list-games";
    }

    // search games by name
    @RequestMapping(value = "search-game", method = RequestMethod.GET)
    public String searchGame(Model model) {

        model.addAttribute("title", "Admin");
        model.addAttribute(new Games());

        return "games/search-game";
    }

    // search results
    @RequestMapping(value = "search-game", method = RequestMethod.POST)
    public String resultGame(Model model, @RequestParam String searchTerm) {

        List<Games> results = gameService.searchByNameContaining(searchTerm);

        model.addAttribute("title", "Admin");
        model.addAttribute("games", results);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute(new Games());

        return "games/search-game";
    }
}
