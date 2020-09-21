package com.ascending.repository;

import com.ascending.dao.BrandDao;
import com.ascending.dao.ModelDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.*;
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
@SpringBootTest (classes = AppInitializer.class)
public class BrandDaoTest {
    private Logger logger = LoggerFactory.getLogger(BrandDaoTest.class);

    @Autowired
    @Qualifier("BrandDaoImpl")
    private BrandDao brandDao;

    private Brand testBrand;
    private Model mod1;
    private Model mod2;
    private Config con1;
    private Config con2;
    private Order or1;
    private Order or2;
    private Customer cus1;
    private Customer cus2;
//    @BeforeClass
//    public static void setupOnce(){ brandDao = new BrandDaoImpl(); modelDao = new ModelDaoImpl();}

    @Before
    public void setup(){

//        mod1 = new Model("TestModel1","Test","Test");
//        mod2 = new Model("TestModel2","Test","Test");
//
//        con1 = new Config();con2 = new Config();
//        con1.setConfigName("testConfig1");con2.setConfigName("testConfig2");
//
//        cus1 = new Customer();cus2 = new Customer();
//        cus1.setName("testCus1");cus2.setName("testCus2");
//
//        or1 = new Order();or2 = new Order();
//        or1.setOrderNumber("testOrder1");or2.setOrderNumber("testOrder2");
//
//        or1.addCustomer(cus1);
//        or2.addCustomer(cus2);
//
//        con1.addOrder(or1);
//        con2.addOrder(or2);
//
//        mod1.addConfig(con1);
//        mod2.addConfig(con2);
        testBrand= new Brand(); testBrand.setName("testBrand");
//        testBrand.addModel(mod1);
//        testBrand.addModel(mod2);
        brandDao.save(testBrand);
    };

    @After
    public void teardown(){brandDao.delete(testBrand);}

    @Test
    public void saveBrandTest(){
        Brand brand = testBrand;
        brandDao.save(brand);
        Assert.assertEquals(testBrand,brand);
    }

    @Test
    public void deleteBrandTest(){
        boolean ifDeleted = brandDao.delete(testBrand);
        Assert.assertTrue(ifDeleted);
    }

    @Test
    public void getAllBrandTest(){
        List<Brand> brandList = brandDao.getBrands();
        Assert.assertEquals("amount comparison",4,brandList.size());
    }

    @Test
    public void updateBrandTest(){
        testBrand.setName("updateTest");
        boolean ifUpated= brandDao.update(testBrand);
        Assert.assertTrue(ifUpated);
    }

    @Test
    public void getBrandByNameTest(){
        String name = testBrand.getName();
        Brand brand = brandDao.getBrandByName(name);
        Assert.assertEquals("brand names comparison",testBrand.getName(),brand.getName());
    }

    @Test
    public void getBrandByIdTest(){
        long id = testBrand.getId();
        Brand brand = brandDao.getBrandById(id);
        Assert.assertEquals("brand ids comparison",testBrand.getId(),brand.getId());

    }

    @Test
    public void deleteBrandByNameTest(){
        String name = testBrand.getName();
        boolean ifdelete = brandDao.deleteByName(name);
        Assert.assertTrue(ifdelete);
    }
}
