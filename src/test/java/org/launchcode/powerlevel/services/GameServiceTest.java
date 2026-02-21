package org.launchcode.powerlevel.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.launchcode.powerlevel.models.Developers;
import org.launchcode.powerlevel.models.Esrb;
import org.launchcode.powerlevel.models.Games;
import org.launchcode.powerlevel.models.Platforms;
import org.launchcode.powerlevel.models.data.DevelopersDao;
import org.launchcode.powerlevel.models.data.EsrbDao;
import org.launchcode.powerlevel.models.data.PlatformsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private EsrbService esrbService;

    @Autowired
    private DevelopersDao developersDao;

    @Autowired
    private PlatformsDao platformsDao;

    @Autowired
    private EsrbDao esrbDao;

    @Test
    public void testSaveAndFindGame() {
        Games game = new Games("Test Game", 29.99, 59.99, "A test game");
        game.setPlayers("1-4");
        game.setQuantity(10);

        Games saved = gameService.save(game);
        assertNotNull(saved);
        assertTrue(saved.getId() > 0);

        Games found = gameService.findById(saved.getId());
        assertEquals("Test Game", found.getName());
        assertEquals(59.99, found.getPrice(), 0.01);
    }

    @Test
    public void testCalculateProfit() {
        Games game = new Games("Profit Game", 30.0, 60.0, "Test");
        game.setPlayers("1");
        game.setQuantity(5);

        double profit = gameService.calculateProfit(game);
        assertEquals(50.0, profit, 0.01);
    }

    @Test
    public void testCalculateProfitZeroPrice() {
        Games game = new Games("Free Game", 0.0, 0.0, "Test");
        game.setPlayers("1");
        game.setQuantity(5);

        double profit = gameService.calculateProfit(game);
        assertEquals(0.0, profit, 0.01);
    }

    @Test
    public void testFormatDecimal() {
        String formatted = gameService.formatDecimal(59.999);
        assertEquals("60.00", formatted);

        formatted = gameService.formatDecimal(29.5);
        assertEquals("29.50", formatted);
    }

    @Test
    public void testSearchByNameContaining() {
        Games game1 = new Games("Super Mario Bros", 20.0, 49.99, "Classic game");
        game1.setPlayers("1-2");
        game1.setQuantity(5);
        gameService.save(game1);

        Games game2 = new Games("Mario Kart", 25.0, 59.99, "Racing game");
        game2.setPlayers("1-4");
        game2.setQuantity(3);
        gameService.save(game2);

        Games game3 = new Games("Zelda", 30.0, 59.99, "Adventure game");
        game3.setPlayers("1");
        game3.setQuantity(7);
        gameService.save(game3);

        List<Games> results = gameService.searchByNameContaining("Mario");
        assertEquals(2, results.size());

        List<Games> noResults = gameService.searchByNameContaining("Halo");
        assertEquals(0, noResults.size());
    }

    @Test
    public void testSaveWithRelations() {
        Developers dev = new Developers();
        dev.setName("Nintendo");
        dev.setEmail("info@nintendo.com");
        dev.setPhone("555-0001");
        developersDao.save(dev);

        Platforms platform = new Platforms();
        platform.setName("Switch");
        platformsDao.save(platform);

        Esrb esrb = new Esrb();
        esrb.setName("E");
        esrbDao.save(esrb);

        Games game = new Games("Animal Crossing", 25.0, 59.99, "Simulation game");
        game.setPlayers("1-8");
        game.setQuantity(15);

        Games saved = gameService.saveWithRelations(game, platform.getId(), dev.getId(), esrb.getId());
        assertNotNull(saved.getDevelopers());
        assertNotNull(saved.getPlatforms());
        assertNotNull(saved.getEsrb());
        assertEquals("Nintendo", saved.getDevelopers().getName());
        assertEquals("Switch", saved.getPlatforms().getName());
        assertEquals("E", saved.getEsrb().getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindByIdNotFound() {
        gameService.findById(99999);
    }

    @Test
    public void testFindAll() {
        Iterable<Games> games = gameService.findAll();
        assertNotNull(games);
    }
}
