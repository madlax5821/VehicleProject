package com.ascending.service;

import com.ascending.init.AppInitializer;
import com.ascending.model.Role;
import com.ascending.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class UserServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private User testUser;
    private Role testRole1;
    private Role testRole2;

    @Before
    public void setup(){
        testUser = new User(); testUser.setName("Big Boss"); testUser.setEmail("BigBoss@test.com");

        testRole1 = new Role(); testRole1.setName("testRole1");
        roleService.save(testRole1);
        testUser.addRole(testRole1);

        testRole2 = new Role(); testRole2.setName("testRole2");
        roleService.save(testRole2);
        testUser.addRole(testRole2);

        userService.save(testUser);
    }
    @After
    public void cleanUp(){
        userService.delete(testUser);
        roleService.delete(testRole1);
        roleService.delete(testRole2);
    }

    @Test
    public void getUserByNameAndEmail(){
        String name = testUser.getName();
        String email = testUser.getEmail();
        User user = userService.getUserByNameAndEmail(name,email);
        Assert.assertEquals("user name comparison",testUser.getName(),user.getName());
        Assert.assertEquals("user email comparison", testUser.getEmail(),user.getEmail());
    }

    @Test
    public void encryptPassword(){
        logger.info("Hashed password:"+ DigestUtils.md5Hex("123456789"));
    }
}
