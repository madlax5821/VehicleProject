package com.ascending.service;

import com.ascending.init.AppInitializer;
import com.ascending.model.Config;
import com.ascending.model.Model;
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
public class ConfigServiceTest {
    private Logger logger = LoggerFactory.getLogger(ConfigServiceTest.class);

    @Autowired
    private ConfigService configService;

    @Autowired
    private ModelService modelService;

    private Config testConfig;
    private Model testModel;

    @Before
    public void setup(){
        testModel = modelService.getModelById(1l);
        testConfig = new Config();
        testConfig.setConfigName("testConfig");
        configService.saveConfig(testConfig,testModel);
    }
    @After
    public void cleanUp(){configService.deleteConfig(testConfig);}

    @Test
    public void saveConfigTest(){
        Config config = testConfig;
        configService.saveConfig(config,testModel);
        Assert.assertEquals("config objects comparison",testConfig,config);
    }

    @Test
    public void deleteConfigTest(){
        boolean ifDelete = configService.deleteConfig(testConfig);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateConfigTest(){
        testConfig.setConfigName("updateTest");
        boolean ifUpdate = configService.updateConfig(testConfig,testModel);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getAllConfigsTest(){
        List<Config> configs = configService.getAllConfigs();
        Assert.assertEquals("config objects amount",4,configs.size());
    }
    
    @Test
    public void deleteConfigByNameTest(){
        boolean ifDelete = configService.deleteConfigByName(testConfig.getConfigName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getConfigByIdTest(){
        long id = testConfig.getId();
        Config config = configService.getConfigById(id);
        Assert.assertEquals("config ids comparison",testConfig.getId(),config.getId());
    }

    @Test
    public void getConfigByNameTest(){
        String name = testConfig.getConfigName();
        Config config = configService.getConfigByName(name);
        Assert.assertEquals("config names comparison",testConfig.getConfigName(),config.getConfigName());
    }
}
