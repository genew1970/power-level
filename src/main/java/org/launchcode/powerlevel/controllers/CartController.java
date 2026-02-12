package org.launchcode.powerlevel.controllers;

import org.launchcode.powerlevel.services.CartService;
import org.launchcode.powerlevel.services.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;

@Controller
@RequestMapping("cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String viewCart(Model model) {

        model.addAttribute("title", "Shopping Cart");
        model.addAttribute("cartItems", cartService.findAll());

        DecimalFormat df = new DecimalFormat("0.00");
        model.addAttribute("cartTotal", df.format(cartService.getCartTotal()));

        return "cart/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addToCart(@RequestParam int gameId, @RequestParam int quantity) {

        cartService.addToCart(gameId, quantity);
        logger.info("Added game {} to cart", gameId);

        return "redirect:/cart";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String removeFromCart(@RequestParam int cartItemId) {

        cartService.removeFromCart(cartItemId);
        logger.info("Removed cart item {}", cartItemId);

        return "redirect:/cart";
    }
}
