package org.launchcode.powerlevel.models.data;

import org.launchcode.powerlevel.models.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CartDao extends CrudRepository<Cart, Integer> {
}
