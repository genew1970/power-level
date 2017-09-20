package org.launchcode.powerlevel.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by genew on 7/23/2017.
 */
public class Cart {

    private int id;
    private int quantity;

    public Cart(){

    }

    public Cart(int id) {
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
