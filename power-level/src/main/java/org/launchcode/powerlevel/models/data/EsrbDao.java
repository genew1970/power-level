package org.launchcode.powerlevel.models.data;

import org.launchcode.powerlevel.models.Esrb;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by genew on 7/11/2017.
 */

@Repository
@Transactional
public interface EsrbDao extends CrudRepository <Esrb, Integer>{
}
