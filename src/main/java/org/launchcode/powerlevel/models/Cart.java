package org.launchcode.powerlevel.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Cart {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Games game;

    @NotNull
    private int quantity;

    public Cart() {
    }

    public Cart(Games game, int quantity) {
        this.game = game;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Games getGame() {
        return game;
    }

    public void setGame(Games game) {
        this.game = game;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        if (game != null) {
            return game.getPrice() * quantity;
        }
        return 0;
    }
}
