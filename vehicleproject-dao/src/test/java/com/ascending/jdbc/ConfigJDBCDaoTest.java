package com.ascending.jdbc;

import com.ascending.dao.ConfigDao;
import com.ascending.dao.ModelDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Config;
import com.ascending.model.Model;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class ConfigJDBCDaoTest {
    private Logger logger = LoggerFactory.getLogger(ConfigJDBCDaoTest.class);
    @Autowired
    @Qualifier("ConfigJDBCDaoImpl")
    private ConfigDao configDao;

    @Autowired
    @Qualifier("ModelJDBCDaoImpl")
    private ModelDao modelDao;

    private Config testConfig;
    private Model testModel;

//    @BeforeClass
//    public static void setup(){configDao = new ConfigJDBCDaoImpl();}

    @Before
    public void setTestConfig(){
        testModel = modelDao.getModelById(1l);
        testConfig = new Config();
        testConfig.setConfigName("testConfig");
        configDao.save(testConfig,testModel);
        testConfig = configDao.getConfigByName(testConfig.getConfigName());
    }

    @After
    public void cleanUp(){configDao.delete(testConfig);}

    @Test
    public void saveConfigTest(){
        Config config = testConfig;
        configDao.save(config,testModel);
        Assert.assertEquals("config objects comparison",testConfig,config);
    }

    @Test
    public void deleteConfigTest(){
        boolean ifDelete = configDao.delete(testConfig);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateConfigTest(){
        testConfig.setConfigName("updateTest");
        boolean ifUpdated = configDao.update(testConfig,testModel);
        Assert.assertTrue(ifUpdated);
    }

    @Test
    public void getAllConfigsTest(){
        List<Config> configs = configDao.getConfigs();
        Assert.assertEquals("configs amount comparison",4,configs.size());
    }

    @Test
    public void deleteConfigByNameTest(){
        boolean ifDelete = configDao.deleteByName(testConfig.getConfigName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getConfigByIdTest(){
        long id = testConfig.getId();
        Config config = configDao.getConfigById(id);
        Assert.assertEquals("config ids comparison",testConfig.getId(),config.getId());
    }

    @Test
    public void getConfigByNameTest(){
        String name = testConfig.getConfigName();
        Config config = configDao.getConfigByName(name);
        Assert.assertEquals("config objects comparison",testConfig.getConfigName(),config.getConfigName());
    }
}
