package com.ascending.repository;

import com.ascending.dao.CustomerDao;
import com.ascending.dao.OrderDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Customer;
import com.ascending.model.Order;
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
public class CustomerDaoTest {
    private Logger logger = LoggerFactory.getLogger(CustomerDaoTest.class);
    @Autowired
    @Qualifier("CustomerDaoImpl")
    private CustomerDao customerDao;
    @Autowired
    @Qualifier("OrderDaoImpl")
    private OrderDao orderDao;
    private Customer testCustomer;
    private Order testOrder;

//    @BeforeClass
//    public static void setup(){customerDao=new CustomerDaoImpl();}

    @Before
    public void initTestCustomer(){
        testOrder = orderDao.getOrderById(1l);
        testCustomer=new Customer();
        testCustomer.setName("testCustomer");
        customerDao.save(testCustomer,testOrder);
        testCustomer = customerDao.getCustomerByName("testCustomer");
    }
    @After
    public void cleanUp(){customerDao.delete(testCustomer);}

    @Test
    public void saveCustomerTest(){
        Customer customer = testCustomer;
        customerDao.save(customer,testOrder);
        Assert.assertEquals("Customer comparison",testCustomer,customer);
    }

    @Test
    public void deleteCustomerTest(){
        boolean ifDelete = customerDao.delete(testCustomer);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateCustomerTest(){
        Customer customer = new Customer(testCustomer.getId(),"test","999","999@qq.com","modified");
        boolean ifUpdate= customerDao.update(customer,testOrder);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getCustomersTest(){
        List<Customer> customers = customerDao.getCustomers();
        Assert.assertEquals("amount of customer comparison",5,customers.size());
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
