package com.ascending.service;

import com.ascending.dao.UserDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.User;

import io.jsonwebtoken.Claims;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class JWTServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDao userDao;

    private User user;

    @Before
    public void setup(){
        String userName = "dwang";
        user = userDao.getUserByName(userName);
    }
    /*
    *
    * 1. get a token by call jwtservice
    * 2. assert not null
    * 3. parse the token string using "." to get a string array
    * 4. assert the string array size == 3
    * 5. logger info token value*/
    @Test
    public void generateTokenTest(){
        String token = jwtService.generateToken(user);
        Assert.assertNotNull(user);
        String[] strings = token.split("\\.");
        Assert.assertEquals("the array amount comparison",3,strings.length);
    }

    @Test
    public void decryptTokenTest(){
        String token = jwtService.generateToken(user);
        logger.info("token is ={}",token);
        Claims claims = jwtService.decryptJwtToken(token);
        String name = claims.getSubject();
        Assert.assertEquals("user name comparison",user.getName(),name);
    }

    @Test
    public void tokenExpiredTest(){
        String token = jwtService.generateToken(user);
        Assert.assertNotNull(token);
        boolean ifTokenExpired = jwtService.hasTokenExpired(token);

        Assert.assertFalse("to distinguish if the token is expired",ifTokenExpired);
    }
}
