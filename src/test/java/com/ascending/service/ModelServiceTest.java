package com.ascending.service;

import com.ascending.init.AppInitializer;
import com.ascending.model.Brand;
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
public class ModelServiceTest {
    private Logger logger = LoggerFactory.getLogger(ModelServiceTest.class);

    @Autowired
    private ModelService modelService;

    @Autowired
    private BrandService brandService;

    private Model testModel;
    private Brand testBrand;

    @Before
    public void setup(){
        testBrand = brandService.getBrandById(1l);
        testModel = modelService.saveModel(new Model("testModel","test","test"),testBrand);
    }
    @After
    public void cleanUp(){modelService.deleteModel(testModel);}

    @Test
    public void saveModelTest(){
        Model model = testModel;
        modelService.saveModel(model,testBrand);
        Assert.assertEquals("model objects comparison",testModel,model);
    }

    @Test
    public void deleteModelTest(){
        boolean ifDelete = modelService.deleteModel(testModel);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateModelTest(){
        testModel.setModelName("updateTest");
        boolean ifUpdate = modelService.updateModel(testModel,testBrand);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getAllModels(){
        List<Model> models = modelService.getAllModels();
        Assert.assertEquals("model objects amount comparison",5,models.size());
    }

    @Test
    public void deleteModelByNameTest(){
        boolean ifDelete = modelService.deleteModelByName(testModel.getModelName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getModelByIdTest(){
        Model model = modelService.getModelById(testModel.getId());
        Assert.assertEquals("model objects comparison",testModel,model);
    }

    @Test
    public void getModelByNameTest(){
        Model model = modelService.getModelByName(testModel.getModelName());
        Assert.assertEquals("model objects comparison",testModel,model);
    }
}
