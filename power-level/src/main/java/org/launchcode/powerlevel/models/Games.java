package org.launchcode.powerlevel.models;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by genew on 6/28/2017.
 */

@Entity
public class Games{
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=30)
    private String name;

    @NotNull
    private double price;

    @NotNull
    @Size(min=3, max=200)
    private String description;

    @NotNull(message = "check at least one box")
    private String players;

    @NotNull
    private double cost;

    @NotNull
    private int quantity;

    @ManyToOne
    @OrderBy("name asc")
    private Developers developers;

    @ManyToOne
    private Platforms platforms;

    @ManyToOne
    private Esrb esrb;


    public Games(String name, double cost, double price, String description) {
        this.name = name;
        this.cost = cost;
        this.price = price;
        this.description = description;
    }

    public Games() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Platforms getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Platforms platforms) {
        this.platforms = platforms;
    }

    public Developers getDevelopers() {
        return developers;
    }

    public void setDevelopers(Developers developers) {
        this.developers = developers;
    }

    public Esrb getEsrb() {
        return esrb;
    }

    public void setEsrb(Esrb esrb) {
        this.esrb = esrb;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String decFormat(double percent) {
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
        String formatted = twoPlaces.format(percent);

        return formatted;
    }
}
