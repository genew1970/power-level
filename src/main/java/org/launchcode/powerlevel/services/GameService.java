package org.launchcode.powerlevel.services;

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
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GamesDao gamesDao;

    @Autowired
    private PlatformsDao platformsDao;

    @Autowired
    private DevelopersDao developersDao;

    @Autowired
    private EsrbDao esrbDao;

    public Iterable<Games> findAll() {
        return gamesDao.findAll();
    }

    public Games findById(int id) {
        Games game = gamesDao.findOne(id);
        if (game == null) {
            logger.warn("Game not found with id: {}", id);
            throw new ResourceNotFoundException("Game not found with id: " + id);
        }
        return game;
    }

    public Games save(Games game) {
        logger.info("Saving game: {}", game.getName());
        return gamesDao.save(game);
    }

    public Games saveWithRelations(Games game, int platformsId, int developersId, int esrbId) {
        game.setDevelopers(developersDao.findOne(developersId));
        game.setPlatforms(platformsDao.findOne(platformsId));
        game.setEsrb(esrbDao.findOne(esrbId));
        logger.info("Saving game with relations: {}", game.getName());
        return gamesDao.save(game);
    }

    public void updateGame(int id, Games updatedGame, int platformsId, int developersId, int esrbId) {
        Games existing = findById(id);
        existing.setPrice(updatedGame.getPrice());
        existing.setCost(updatedGame.getCost());
        existing.setPlayers(updatedGame.getPlayers());
        existing.setName(updatedGame.getName());
        existing.setQuantity(updatedGame.getQuantity());
        existing.setDescription(updatedGame.getDescription());
        existing.setPlatforms(platformsDao.findOne(platformsId));
        existing.setDevelopers(developersDao.findOne(developersId));
        existing.setEsrb(esrbDao.findOne(esrbId));
        gamesDao.save(existing);
        logger.info("Updated game with id: {}", id);
    }

    public double calculateProfit(Games game) {
        if (game.getPrice() == 0) {
            return 0;
        }
        return ((game.getPrice() - game.getCost()) / game.getPrice()) * 100;
    }

    public String formatDecimal(double value) {
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
        return twoPlaces.format(value);
    }

    public List<Games> findByName(String name) {
        logger.info("Searching games by exact name: {}", name);
        return gamesDao.findByName(name);
    }

    public List<Games> searchByNameContaining(String searchTerm) {
        logger.info("Searching games containing: {}", searchTerm);
        List<Games> results = gamesDao.findByNameContainingIgnoreCase(searchTerm);
        logger.info("Found {} games matching '{}'", results.size(), searchTerm);
        return results;
    }
}
