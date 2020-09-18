package com.ascending.repository;

import com.ascending.dao.RoleDao;
import com.ascending.dao.UserDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Role;
import com.ascending.model.User;
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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class UserDaoTest {
    private Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    private User testUser;
    private Role testRole1;
    private Role testRole2;

    @Before
    public void setup(){
        testUser = new User(); testUser.setName("testUser");testUser.setEmail("testEmail");

        testRole1 = new Role(); testRole1.setName("testRole1");
        roleDao.save(testRole1);
        testUser.addRole(testRole1);

        testRole2 = new Role(); testRole2.setName("testRole2");
        roleDao.save(testRole2);
        testUser.addRole(testRole2);

        userDao.save(testUser);
    }

    @After
    public void cleanUp(){
        userDao.delete(testUser);
        roleDao.delete(testRole1);
        roleDao.delete(testRole2);
    }

    @Test
    public void getCredentialTest(){
        String email = "dwang@training.ascendingdc.com";
        String password = "25f9e794323b453885f5181f1b624d0b";
        User user = new User();
        try {
            user = userDao.getUserByCredentials(email,password);
        } catch (Exception e) {
            logger.debug("failed to get credentials, error:"+e.getMessage());
        }
        Assert.assertEquals("email comparison",email,user.getEmail());
        Assert.assertEquals("password comparison",password,user.getPassword());
    }

    @Test
    public void saveUserTest(){
        User user = testUser;
        userDao.save(user);
        Assert.assertEquals(testUser,user);
    }

    @Test
    public void deleteUserTest(){
        boolean ifDelete = userDao.delete(testUser);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateUserTest(){
        testUser.setName("updateTest");
        boolean ifUpdate = userDao.update(testUser);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getAllUsersTest(){
        List<User> users = userDao.findAllUsers();
        Assert.assertEquals("users amount comparison",7,users.size());
    }

    @Test
    public void deleteUserByNameTest(){
        boolean ifDelete = userDao.deleteByName(testUser.getName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getUserByNameTest(){
        String name = "testUser";
        User user = userDao.getUserByName(name);
        Assert.assertEquals("user name comparison",testUser.getName(),user.getName());
    }

    @Test
    public void getUserByIdTest(){
        long id = testUser.getId();
        User user = userDao.getUserById(id);
        Assert.assertEquals("user id comparison",testUser.getId(),user.getId());
    }

    @Test
    public void getUserByEmailTest(){
        String email = testUser.getEmail();
        User user = userDao.getUserByEmail(email);
        Assert.assertEquals("user email comparison",testUser.getEmail(),user.getEmail());
    }

    @Test
    public void addRoleTest(){

    }

}
