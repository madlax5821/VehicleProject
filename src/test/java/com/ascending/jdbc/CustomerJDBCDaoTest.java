package com.ascending.jdbc;

import com.ascending.dao.CustomerDao;
import com.ascending.dao.OrderDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Customer;
import com.ascending.model.Order;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
public class CustomerJDBCDaoTest {
    private Logger logger = LoggerFactory.getLogger(CustomerJDBCDaoTest.class);

    @Autowired
    @Qualifier("CustomerJDBCDaoImpl")
    private CustomerDao customerDao;
    @Autowired
    @Qualifier("OrderJDBCDaoImpl")
    private OrderDao orderDao;

    private Customer testCustomer;
    private Order testOrder;

    @Before
    public void setup() {
        testOrder = orderDao.getOrderById(1l);
        testCustomer = new Customer();
        testCustomer.setName("testCustomer");
        customerDao.save(testCustomer, testOrder);
        testCustomer = customerDao.getCustomerByName(testCustomer.getName());
    }
    @After
    public void cleanUp(){customerDao.delete(testCustomer);};

    @Test
    public void saveCustomerTest(){
        Customer customer = testCustomer;
        customerDao.save(customer,testOrder);
        Assert.assertEquals("customer objects comparison",testCustomer,customer);
    }

    @Test
    public void deleteCustomerTest(){
        boolean ifDelete = customerDao.delete(testCustomer);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateCustomerTest(){
        testCustomer.setName("updateTest");
        boolean ifUpdate = customerDao.update(testCustomer,testOrder);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getCustomersTest(){
        List<Customer> customers = customerDao.getCustomers();
        Assert.assertEquals("amount check",5,customers.size());
    }

    @Test
    public void deleteCustomerByNameTest(){
        boolean ifDelete = customerDao.deleteByName(testCustomer.getName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getCustomerByIdTest(){
        long id = testCustomer.getId();
        Customer customer = customerDao.getCustomerById(id);
        Assert.assertEquals("customer ids comparison",testCustomer.getId(),customer.getId());

    }

    @Test
    public void getCustomerByNameTest(){
        String name = testCustomer.getName();
        Customer customer = customerDao.getCustomerByName(name);
        Assert.assertEquals("customer names comparison",testCustomer.getName(),customer.getName());
    }

}
