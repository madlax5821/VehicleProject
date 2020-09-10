package com.ascending.jdbc;

import com.ascending.dao.BrandDao;
import com.ascending.model.Brand;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BrandJDBCDaoTest {
    private Logger logger = LoggerFactory.getLogger(BrandJDBCDaoTest.class);
    private static BrandDao brandDao;
    private Brand testBrand;

    @BeforeClass
    public static void setup(){
        brandDao = new BrandJDBCDaoImpl();
    }

    @Before
    public void setupObjectForTest(){
        testBrand = new Brand("Lamborghini","Germany","super racing car");
        brandDao.save(testBrand);
    }

    @After
    public void cleanUp(){
        brandDao.deleteByName("Lamborghini");
    }

    @Test
    public void getBrandsTest(){
        List<Brand> brands = brandDao.getBrands();
        assertEquals(2,brands.size());
    }
    @Test
    public void saveBrandTest(){
        Brand brand = testBrand;
        Brand savedBrand = brandDao.save(brand);
        assertEquals("compare if it`s identical",testBrand,savedBrand);
        logger.info("");
    }

    @Test
    public void deleteByNameTest(){
        boolean deleteResult = brandDao.deleteByName(testBrand.getName());
        assertTrue(deleteResult);
    }

    @Test
    public void updateTest(){
        Brand brand = testBrand;
        assertTrue(brandDao.update(brand));
    }
}
