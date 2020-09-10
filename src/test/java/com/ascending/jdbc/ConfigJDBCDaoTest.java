package com.ascending.jdbc;

import com.ascending.dao.ConfigDao;
import com.ascending.model.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class ConfigJDBCDaoTest {
    Logger logger = LoggerFactory.getLogger(ConfigJDBCDaoTest.class);
    private static ConfigDao configDao;
    private Config testConfig;
    @BeforeClass
    public static void setup(){configDao = new ConfigJDBCDaoImpl();}

    @Before
    public void setTestConfig(){
        Date date = new Date(2016);
        Config config = new Config("XT6 3.0tt","twin turbo engine power", date);
        testConfig = configDao.save(config);
    }

    @After
    public void cleanUp(){
        configDao.deleteByName(testConfig.getConfigName());
    }

    @Test
    public void updateTest(){
        Date date = new Date(2016);
        Config config = new Config(testConfig.getConfigName(),"rubbish",date);
        boolean ifUpdated = configDao.update(config);
    }
}
