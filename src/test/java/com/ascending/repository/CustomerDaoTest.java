package com.ascending.repository;

import com.ascending.dao.CustomerDao;
import com.ascending.model.Customer;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class CustomerDaoTest {

    private Logger logger = LoggerFactory.getLogger(CustomerDaoTest.class);
    private static CustomerDao customerDao;
    private Customer testCustomer;

    @BeforeClass
    public static void setup(){customerDao=new CustomerDaoImpl();}

    @Before
    public void initTestCustomer(){testCustomer=customerDao.save(new Customer("test","123","123@test","test"));}

    @After
    public void cleanUp(){customerDao.deleteByName(testCustomer.getName());}

    @Test
    public void saveCustomerTest(){
        Customer customer = testCustomer;
        Customer savedCustomer = customerDao.save(customer);
        Assert.assertEquals("Customer comparison",testCustomer,savedCustomer);
    }

    @Test
    public void deleteCustomerTest(){
        boolean ifDelete = customerDao.delete(testCustomer);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateCustomerTest(){
        Customer customer = new Customer(testCustomer.getId(),"test","999","999@qq.com","modified");
        boolean ifUpdate= customerDao.update(customer);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getCustomersTest(){
        List<Customer> customers = customerDao.getCustomers();
        Assert.assertEquals("amount of customer comparison",1,customers.size());
    }

    @Test
    public void deleteCustomerByNameTest(){
        boolean ifDelete = customerDao.deleteByName(testCustomer.getName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getCustomerByIdTest(){
        Customer customer = customerDao.getCustomerById(testCustomer.getId());
        Assert.assertEquals("customer comparison",testCustomer,customer);
    }

    @Test
    public void getCustomerByNameTest(){
        Customer customer = customerDao.getCustomerByName(testCustomer.getName());
        Assert.assertEquals("customer comparison",testCustomer,customer);
    }

}
