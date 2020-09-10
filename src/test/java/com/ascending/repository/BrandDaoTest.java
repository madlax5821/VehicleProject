package com.ascending.repository;

import com.ascending.dao.BrandDao;
import com.ascending.model.Brand;
import com.ascending.model.Config;
import com.ascending.model.Model;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BrandDaoTest {
    private static BrandDao brandDao;
    private Logger logger = LoggerFactory.getLogger(BrandDaoTest.class);
    private Brand testBrand;
    private Model mod1;
    private Model mod2;
    private Config con1;
    private Config con2;
    @BeforeClass
    public static void setupOnce(){ brandDao = new BrandDaoImpl(); }

    @Before
    public void setup(){
        Brand brand = new Brand("Lamborghini","Italy","Super car");
        testBrand = brandDao.save(brand);
               Date date = new Date(2016);
        mod1 = new Model("Diablo","sedan","niuB");
        mod2 = new Model("Aventador","sedan","niuX");
        con1 = new Config("VT6.0","fast",date);
        con2 = new Config("LP700-4","fast",date);

        mod1.getConfigs().add(con1);
        con1.setModel(mod1);

        mod2.getConfigs().add(con2);
        con2.setModel(mod2);

        testBrand.getModels().add(mod1);
        mod1.setBrand(testBrand);

        testBrand.getModels().add(mod2);
        mod2.setBrand(testBrand);
    };

    @After
    public void teardown(){brandDao.delete(testBrand);}

    @Test
    public void saveBrandTest(){
        Brand brand = testBrand;
        Brand savedBrand = brandDao.save(brand);
        Assert.assertEquals(brand,savedBrand);
    }

    @Test
    public void deleteByBrandTest(){
        boolean ifDeleted = brandDao.delete(testBrand);
        Assert.assertTrue(ifDeleted);
    }

    @Test
    public void getAllBrandTest(){
        List<Brand> brands = brandDao.getBrands();
        Assert.assertEquals("amount comparison",4,brands.size());
    }

    @Test
    public void updateBrandTest(){
        Brand brand = new Brand(testBrand.getId(),"Stupid Car","Mars","Stupid");
        boolean ifUpated= brandDao.update(brand);
        Assert.assertTrue(ifUpated);
    }

    @Test
    public void getBrandByNameTest(){
        Brand brand = brandDao.getBrandByName(testBrand.getName());
        Assert.assertNotEquals("brand comparison",brand,testBrand);
    }

    @Test
    public void getBrandByIdTest(){
        Brand brand = brandDao.getBrandById(testBrand.getId());
        Assert.assertEquals(testBrand,brand);

    }
}
