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
public class RoleDaoTest {
    private Logger logger = LoggerFactory.getLogger(RoleDaoTest.class);

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    private Role testRole;
    private User testUser1;
    private User testUser2;

    @Before
    public void setup() {
        testRole = new Role();
        testRole.setName("testRole");

        testUser1 = new User();
        testUser1.setName("testUser1");
        testUser1.setEmail("test1@Email.com");
        testUser2 = new User();
        testUser2.setName("testUser2");
        testUser2.setEmail("test2@email.com");

        testRole.addUser(testUser1);
        testRole.addUser(testUser2);

        roleDao.save(testRole);
        userDao.save(testUser1);
        userDao.save(testUser2);

    }
    @After
    public void cleanUp() {
        userDao.delete(testUser1);
        userDao.delete(testUser2);
        roleDao.delete(testRole);

    }

    @Test
    public void saveRoleTest() {
        Role role = testRole;
        roleDao.save(role);
        Assert.assertEquals("role objects comparison", testRole, role);
    }

    @Test
    public void deleteRoleTest() {
        userDao.delete(testUser1);
        userDao.delete(testUser2);
        int i = roleDao.delete(testRole);
        Assert.assertTrue(i > 0);
    }

    @Test
    public void updateRoleTest() {
        testRole.setName("updateTest");
        boolean ifUpdate = roleDao.update(testRole);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getAllRolesTest() {
        List<Role> roles = roleDao.findAllRoles();
        Assert.assertEquals("roles amount comparison", 7, roles.size());
    }

    @Test
    public void deleteRoleByNameTest(){
        userDao.delete(testUser1);
        userDao.delete(testUser2);
        boolean ifDelete = roleDao.deleteRoleByName(testRole.getName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getRoleByNameTest(){
        String name = testRole.getName();
        Role role = roleDao.getRoleByName(name);
        Assert.assertEquals("role names comparison",testRole.getName(),role.getName());
    }

}
