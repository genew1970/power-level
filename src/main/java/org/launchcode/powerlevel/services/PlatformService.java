package org.launchcode.powerlevel.services;

import org.launchcode.powerlevel.models.Platforms;
import org.launchcode.powerlevel.models.data.PlatformsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatformService {

    private static final Logger logger = LoggerFactory.getLogger(PlatformService.class);

    @Autowired
    private PlatformsDao platformsDao;

    public Iterable<Platforms> findAll() {
        return platformsDao.findAll();
    }

    public Platforms findById(int id) {
        Platforms platform = platformsDao.findOne(id);
        if (platform == null) {
            logger.warn("Platform not found with id: {}", id);
            throw new ResourceNotFoundException("Platform not found with id: " + id);
        }
        return platform;
    }

    public Platforms save(Platforms platform) {
        logger.info("Saving platform: {}", platform.getName());
        return platformsDao.save(platform);
    }
}
