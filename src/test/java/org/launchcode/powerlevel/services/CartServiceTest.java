package org.launchcode.powerlevel.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.launchcode.powerlevel.models.Cart;
import org.launchcode.powerlevel.models.Games;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private GameService gameService;

    @Test
    public void testAddToCart() {
        Games game = new Games("Cart Test Game", 20.0, 49.99, "Test game for cart");
        game.setPlayers("1");
        game.setQuantity(10);
        Games saved = gameService.save(game);

        Cart cartItem = cartService.addToCart(saved.getId(), 2);
        assertNotNull(cartItem);
        assertEquals(2, cartItem.getQuantity());
        assertEquals("Cart Test Game", cartItem.getGame().getName());
    }

    @Test
    public void testGetCartTotal() {
        Games game1 = new Games("Total Game 1", 10.0, 20.0, "Test");
        game1.setPlayers("1");
        game1.setQuantity(5);
        gameService.save(game1);

        Games game2 = new Games("Total Game 2", 15.0, 30.0, "Test");
        game2.setPlayers("1");
        game2.setQuantity(5);
        gameService.save(game2);

        cartService.addToCart(game1.getId(), 1);
        cartService.addToCart(game2.getId(), 2);

        double total = cartService.getCartTotal();
        assertTrue(total > 0);
    }

    @Test
    public void testRemoveFromCart() {
        Games game = new Games("Remove Cart Game", 10.0, 25.0, "Test");
        game.setPlayers("1");
        game.setQuantity(5);
        gameService.save(game);

        Cart cartItem = cartService.addToCart(game.getId(), 1);
        int cartItemId = cartItem.getId();

        cartService.removeFromCart(cartItemId);

        try {
            cartService.findById(cartItemId);
            fail("Expected ResourceNotFoundException");
        } catch (ResourceNotFoundException e) {
            // expected
        }
    }

    @Test
    public void testCartSubtotal() {
        Games game = new Games("Subtotal Game", 15.0, 40.0, "Test");
        game.setPlayers("1");
        game.setQuantity(10);
        gameService.save(game);

        Cart cartItem = cartService.addToCart(game.getId(), 3);
        assertEquals(120.0, cartItem.getSubtotal(), 0.01);
    }
}
