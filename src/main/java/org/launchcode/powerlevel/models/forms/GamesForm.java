package org.launchcode.powerlevel.models.forms;

import org.launchcode.powerlevel.models.Developers;
import org.launchcode.powerlevel.models.Games;
import org.launchcode.powerlevel.models.Platforms;

import javax.validation.constraints.NotNull;

/**
 * Created by genew on 7/8/2017.
 */

public class GamesForm {
    private Iterable<Developers> developers;
    private Iterable<Platforms> platforms;

    @NotNull
    private int developersId;

    @NotNull
    private int platformsId;

    @NotNull
    private int gamesId;

    public GamesForm() {
    }

    public GamesForm(Iterable<Developers> developers, Iterable<Platforms> platforms) {
        this.developers = developers;
        this.platforms = platforms;
    }

    public int getDevelopersId() {
        return developersId;
    }

    public void setDevelopersId(int developersId) {
        this.developersId = developersId;
    }

    public int getPlatformsId() {
        return platformsId;
    }

    public void setPlatformsId(int platformsId) {
        this.platformsId = platformsId;
    }

}
