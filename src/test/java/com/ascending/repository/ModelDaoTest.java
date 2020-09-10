package com.ascending.repository;

import com.ascending.dao.ModelDao;
import com.ascending.model.Config;
import com.ascending.model.Model;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;

public class ModelDaoTest {
    Logger logger = LoggerFactory.getLogger(ModelDaoTest.class);
    private static ModelDao modelDao;
    private Model testModel;
    private Config con1;
    private Config con2;

    @BeforeClass
    public static void setup(){modelDao= new ModelDaoImpl();}

    @Before
    public void initTestModel(){
        testModel = modelDao.save(new Model("TestModel","TestType","TestDescription"));

        Date date = new Date(2016);
        con1=new Config("TestModel1","test1",date);
        con2=new Config("TestModel2","test2",date);

        testModel.getConfigs().add(con1);
        con1.setModel(testModel);
        testModel.getConfigs().add(con2);
        con2.setModel(testModel);
    }
    @After
    public void cleanUp(){modelDao.deleteByName("TestModel");}

    @Test
    public void saveModelTest(){
        Model model = testModel;
        Model savedModel = modelDao.save(model);
        Assert.assertEquals("object comparison",testModel,savedModel);
    }

    @Test
    public void deleteModelTest(){
        boolean ifDelete = modelDao.delete(testModel);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateModelTest(){
        Model model = new Model(testModel.getId(),"TestModel","Modified Type","Modified description");
        boolean ifUpdate = modelDao.update(model);
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
        Model model = modelDao.getModelById(testModel.getId());
        Assert.assertEquals("Model comparison",testModel,model);
    }

    @Test
    public void getModelByNameTest(){
        Model model = modelDao.getModelByName(testModel.getModelName());
        Assert.assertEquals("Model comparison",testModel,model);
    }


}

