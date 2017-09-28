package org.launchcode.powerlevel.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by genew on 6/28/2017.
 */

// class to handle platforms with appropriate fields gained from user input
@Entity
public class Platforms {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    @JoinColumn(name = "platforms_id")
    private List<Games> games;

    @NotNull
    @Size(min=3, max=20)
    private String name;

    public Platforms() {

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
