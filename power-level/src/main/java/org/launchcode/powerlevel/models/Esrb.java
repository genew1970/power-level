package org.launchcode.powerlevel.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by genew on 7/11/2017.
 */

// class to handle developers with appropriate fields
    // does not allow the user to update the information because the ratings should not change much over the years
@Entity
public class Esrb {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @OneToMany
    @JoinColumn(name = "esrb_id")
    private List<Games> games = new ArrayList<>();

    public Esrb() {

    }

    public int getId() {

        return id;
    }

    public List<Games> getGames() {
        return games;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
