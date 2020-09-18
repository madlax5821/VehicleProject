package com.ascending.repository;

import com.ascending.dao.BrandDao;
import com.ascending.dao.ModelDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Brand;
import com.ascending.model.Model;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class ModelDaoTest {
    private Logger logger = LoggerFactory.getLogger(ModelDaoTest.class);

    @Autowired
    @Qualifier("ModelDaoImpl")
    private ModelDao modelDao;

    @Autowired
    @Qualifier("BrandDaoImpl")
    private BrandDao brandDao;

    private Model testModel;
    private Brand testBrand;

    @BeforeClass
    public static void setup(){
        //modelDao= new ModelDaoImpl();
    }

    @Before
    public void initTestModel(){
        testBrand = brandDao.getBrandById(1l);
        testModel = modelDao.save(new Model("TestModel","TestType","TestDescription"),testBrand);
    }
    @After
    public void cleanUp(){modelDao.delete(testModel);}

    @Test
    public void saveModelTest(){
        Model model = testModel;
        modelDao.save(model,testBrand);
        Assert.assertEquals("model objects comparison",testModel,model);
    }

    @Test
    public void deleteModelTest(){
        boolean ifDelete = modelDao.delete(testModel);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateModelTest(){
        Model model = new Model();
        model.setId(testModel.getId());
        model.setModelName("askjdhafg");
        boolean ifUpdate = modelDao.update(model,testBrand);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getAllModelsTest(){
        List<Model> models = modelDao.getModels();
        Assert.assertEquals("amount of model comparison",5, models.size());
    }

    @Test
    public void deleteModelByNameTest(){
        boolean ifDelete = modelDao.deleteByName(testModel.getModelName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getModelByIdTest(){
        long id = testModel.getId();
        Model model = modelDao.getModelById(id);
        Assert.assertEquals("Model ids comparison",testModel.getId(),model.getId());
    }

    @Test
    public void getModelByNameTest(){
        String name = testModel.getModelName();
        Model model = modelDao.getModelByName(name);
        Assert.assertEquals("model names comparison",testModel.getModelName(),model.getModelName());
    }


}

