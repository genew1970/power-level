package org.launchcode.powerlevel.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.launchcode.powerlevel.models.Developers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeveloperServiceTest {

    @Autowired
    private DeveloperService developerService;

    @Test
    public void testSaveAndFindDeveloper() {
        Developers dev = new Developers();
        dev.setName("Epic Games");
        dev.setEmail("contact@epicgames.com");
        dev.setPhone("555-1234");

        Developers saved = developerService.save(dev);
        assertNotNull(saved);
        assertTrue(saved.getId() > 0);

        Developers found = developerService.findById(saved.getId());
        assertEquals("Epic Games", found.getName());
        assertEquals("contact@epicgames.com", found.getEmail());
    }

    @Test
    public void testUpdateDeveloper() {
        Developers dev = new Developers();
        dev.setName("Old Name");
        dev.setEmail("old@test.com");
        dev.setPhone("111-1111");
        Developers saved = developerService.save(dev);

        Developers updated = new Developers();
        updated.setName("New Name");
        updated.setEmail("new@test.com");
        updated.setPhone("222-2222");

        developerService.updateDeveloper(saved.getId(), updated);

        Developers found = developerService.findById(saved.getId());
        assertEquals("New Name", found.getName());
        assertEquals("new@test.com", found.getEmail());
        assertEquals("222-2222", found.getPhone());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindByIdNotFound() {
        developerService.findById(99999);
    }

    @Test
    public void testFindAll() {
        Iterable<Developers> devs = developerService.findAll();
        assertNotNull(devs);
    }
}
