package com.ascending.repository;

import com.ascending.dao.ConfigDao;
import com.ascending.dao.ModelDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Config;
import com.ascending.model.Model;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class ConfigDaoTest{
    @Autowired
    @Qualifier("ConfigDaoImpl")
    private ConfigDao configDao;
    @Autowired
    @Qualifier("ModelDaoImpl")
    private ModelDao modelDao;

    private Config testConfig;
    private Model testModel;

    @Before
    public void setup(){
        testModel = modelDao.getModelById(1l);
        testConfig = new Config();
        testConfig.setConfigName("testConfig");
        configDao.save(testConfig,testModel);

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
        Config config = new Config();
        config.setId(testConfig.getId());
        config.setConfigName("updatedTest");
        boolean ifUpdate = configDao.update(testConfig,testModel);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getAllConfigsTest(){
        List<Config> configs = configDao.getConfigs();
        Assert.assertEquals("Configs amount comparison",4, configs.size());
    }

    @Test
    public void deteleConfigByNameTest(){
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
        Assert.assertEquals("config names comparison",testConfig.getConfigName(),config.getConfigName());
    }
}