package org.launchcode.powerlevel.services;

import org.launchcode.powerlevel.models.Esrb;
import org.launchcode.powerlevel.models.data.EsrbDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsrbService {

    private static final Logger logger = LoggerFactory.getLogger(EsrbService.class);

    @Autowired
    private EsrbDao esrbDao;

    public Iterable<Esrb> findAll() {
        return esrbDao.findAll();
    }

    public Esrb findById(int id) {
        Esrb esrb = esrbDao.findOne(id);
        if (esrb == null) {
            logger.warn("ESRB rating not found with id: {}", id);
            throw new ResourceNotFoundException("ESRB rating not found with id: " + id);
        }
        return esrb;
    }

    public Esrb save(Esrb esrb) {
        logger.info("Saving ESRB rating: {}", esrb.getName());
        return esrbDao.save(esrb);
    }
}
