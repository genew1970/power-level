package org.launchcode.powerlevel.models;

import org.hibernate.annotations.SortComparator;
import org.hibernate.annotations.SortNatural;
import org.springframework.core.annotation.Order;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by genew on 6/28/2017.
 */

@Entity
public class Developers {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=30)
    private String name;

    private String email;
    private String phone;

    @OneToMany
    @JoinColumn(name = "developers_id")
    private List<Games> games;

    public Developers() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
