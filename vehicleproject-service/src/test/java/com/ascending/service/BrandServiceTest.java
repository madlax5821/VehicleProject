package com.ascending.service;

import com.ascending.init.AppInitializer;
import com.ascending.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class BrandServiceTest {
    private Logger logger = LoggerFactory.getLogger(BrandServiceTest.class);

    @Autowired
    private BrandService brandService;

    private Brand testBrand;
    private Model mod1;
    private Model mod2;
    private Config con1;
    private Config con2;
    private Order ord1;
    private Order ord2;
    private Customer cus1;
    private Customer cus2;

    @Before
    public void setup(){
        testBrand = new Brand();
        testBrand.setName("ServiceTest");
        mod1 =new Model();
        mod1.setModelName("testModel1");
        //mod2.setModelName("testModel2");
        con1 = new Config();
        con1.setConfigName("testConfig1");
        //con2.setConfigName("testConfig2");
        ord1 = new Order();
        ord1.setOrderNumber("testOrder1");
        ord2 = new Order();
        ord2.setOrderNumber("testOrder2");
        cus1 = new Customer();
        cus1.setName("testCustomer1");
        cus2 = new Customer();
        cus2.setName("testCustomer2");

        cus1.setOrder(ord1);
        ord1.setCustomer(cus1);
        ord1.getCustomer();

        cus2.setOrder(ord2);
        ord2.setCustomer(cus2);
        ord2.getCustomer();

        ord1.setConfig(con1);
        con1.getOrders().add(ord1);

        ord2.setConfig(con1);
        con1.getOrders().add(ord2);

        con1.setModel(mod1);
        mod1.getConfigs().add(con1);

        mod1.setBrand(testBrand);
        testBrand.getModels().add(mod1);

        brandService.saveBrand(testBrand);
    }

    @After
    public void cleanUp(){brandService.deleteBrand(testBrand);}

    @Test
    public void saveBrandTest(){
        Brand brand = testBrand;
        brandService.saveBrand(brand);
        Assert.assertEquals("Brand objects comparison",testBrand,brand);
    }

    @Test
    public void deleteBrandTest(){
        boolean ifDelete = brandService.deleteBrand(testBrand);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateBrandTest(){
        testBrand.setName("updateTest");
        boolean ifUpdate = brandService.updateBrand(testBrand);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getAllBrandsTest(){
        List<Brand> brands = brandService.getAllBrands();
        Assert.assertEquals("Brand amount comparison",4,brands.size());
    }

    @Test
    public void deleteBrandByName(){
        boolean ifDelete = brandService.deleteBrandByName(testBrand.getName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getBrandByIdTest(){
        long id = testBrand.getId();
        Brand brand = brandService.getBrandById(id);
        Assert.assertEquals("brand ids comparison",testBrand.getId(),brand.getId());
    }

    @Test
    public void getBrandByNameTest(){
        String name = testBrand.getName();
        Brand brand = brandService.getBrandByName(name);
        Assert.assertEquals("brand names comparison",testBrand.getName(),brand.getName());
    }
}
