package org.launchcode.powerlevel.services;

import org.launchcode.powerlevel.models.Cart;
import org.launchcode.powerlevel.models.Games;
import org.launchcode.powerlevel.models.data.CartDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartDao cartDao;

    @Autowired
    private GameService gameService;

    public Iterable<Cart> findAll() {
        return cartDao.findAll();
    }

    public Cart findById(int id) {
        Cart cart = cartDao.findOne(id);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart item not found with id: " + id);
        }
        return cart;
    }

    public Cart addToCart(int gameId, int quantity) {
        Games game = gameService.findById(gameId);
        Cart cartItem = new Cart(game, quantity);
        logger.info("Adding game '{}' to cart with quantity {}", game.getName(), quantity);
        return cartDao.save(cartItem);
    }

    public void removeFromCart(int cartItemId) {
        logger.info("Removing cart item with id: {}", cartItemId);
        cartDao.delete(cartItemId);
    }

    public double getCartTotal() {
        double total = 0;
        for (Cart item : cartDao.findAll()) {
            total += item.getSubtotal();
        }
        return total;
    }
}
