package com.ascending.jdbc;

import com.ascending.dao.ModelDao;
import com.ascending.model.Model;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebParam;

public class ModelJDBCDaoTest {
    Logger logger = LoggerFactory.getLogger(ModelJDBCDaoTest.class);
    private Model testModel;
    private static ModelDao modelDao;
    @BeforeClass
    public static void setup(){modelDao = new ModelJDBCDaoImpl();}

    @Before
    public void setTestModel(){
        Model model =new Model("XT6","Sedan", "American luxury");
        modelDao.save(model);
        testModel = modelDao.getModelByName(model.getModelName());
    }
    @After
    public void cleanUp(){
        modelDao.delete(testModel);
    }

    @Test
    public void updateModelTest(){
        Model model = new Model(testModel.getId(),"222","asdasd","asdasd");
        boolean ifUpdated = modelDao.update(model);
        Assert.assertTrue("if updated",ifUpdated);
    }
}
