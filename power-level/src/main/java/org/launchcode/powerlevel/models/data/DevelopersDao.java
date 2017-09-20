package org.launchcode.powerlevel.models.data;

import org.launchcode.powerlevel.models.Developers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by genew on 6/28/2017.
 */

@Repository
@Transactional
public interface DevelopersDao extends CrudRepository <Developers, Integer> {
    List<Developers> findAllByOrderByNameAsc();
}
