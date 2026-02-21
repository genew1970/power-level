package org.launchcode.powerlevel.services;

import org.launchcode.powerlevel.models.Developers;
import org.launchcode.powerlevel.models.data.DevelopersDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeveloperService {

    private static final Logger logger = LoggerFactory.getLogger(DeveloperService.class);

    @Autowired
    private DevelopersDao developersDao;

    public Iterable<Developers> findAll() {
        return developersDao.findAll();
    }

    public Developers findById(int id) {
        Developers developer = developersDao.findOne(id);
        if (developer == null) {
            logger.warn("Developer not found with id: {}", id);
            throw new ResourceNotFoundException("Developer not found with id: " + id);
        }
        return developer;
    }

    public Developers save(Developers developer) {
        logger.info("Saving developer: {}", developer.getName());
        return developersDao.save(developer);
    }

    public void updateDeveloper(int id, Developers updated) {
        Developers existing = findById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        developersDao.save(existing);
        logger.info("Updated developer with id: {}", id);
    }
}
