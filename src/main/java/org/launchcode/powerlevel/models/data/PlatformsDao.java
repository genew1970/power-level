package org.launchcode.powerlevel.models.data;

import org.launchcode.powerlevel.models.Platforms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by genew on 6/28/2017.
 */

@Transactional
@Repository
public interface PlatformsDao extends CrudRepository<Platforms, Integer> {
}
