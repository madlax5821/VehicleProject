package com.ascending.jdbc;

import com.ascending.dao.BrandDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Brand;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class BrandJDBCDaoTest {
    private Logger logger = LoggerFactory.getLogger(BrandJDBCDaoTest.class);

    @Autowired
    @Qualifier("BrandJDBCDaoImpl")
    private BrandDao brandDao;

    private Brand testBrand;

//    @BeforeClass
//    public static void setup(){
//        brandDao = new BrandJDBCDaoImpl();
//    }

    @Before
    public void setupObjectForTest(){
        testBrand = new Brand("test","test","test");
        brandDao.save(testBrand);
        testBrand = brandDao.getBrandByName("test");
    }
    @After
    public void cleanUp(){
        brandDao.delete(testBrand);
    }

    @Test
    public void getBrandsTest(){
        List<Brand> brands = brandDao.getBrands();
        assertEquals(4,brands.size());
    }

    @Test
    public void saveBrandTest(){
        Brand brand = testBrand;
        Brand savedBrand = brandDao.save(brand);
        assertEquals("compare if it`s identical",testBrand,savedBrand);
        logger.info("");
    }

    @Test
    public void deleteBrandByNameTest(){
        boolean deleteResult = brandDao.deleteByName(testBrand.getName());
        assertTrue(deleteResult);
    }

    @Test
    public void deleteBrandTest(){
        boolean ifDelete = brandDao.delete(testBrand);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getBrandByIdTest(){
        long id = testBrand.getId();
        Brand brand = brandDao.getBrandById(id);
        Assert.assertEquals("brand id comparison",testBrand.getId(),brand.getId());
    }

    @Test
    public void getBrandByNameTest(){
        String name = testBrand.getName();
        Brand brand = brandDao.getBrandByName(name);
        Assert.assertEquals("brand names comparison",testBrand.getName(),brand.getName());
    }

    @Test
    public void updateTest(){
        testBrand.setNationality("updateTest");
        testBrand.setDescription("updateTest");
        assertTrue(brandDao.update(testBrand));
    }
}
