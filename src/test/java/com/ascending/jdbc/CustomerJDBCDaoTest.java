package com.ascending.jdbc;

import com.ascending.dao.CustomerDao;
import com.ascending.model.Customer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomerJDBCDaoTest {
    Logger logger = LoggerFactory.getLogger(CustomerJDBCDaoTest.class);
    private CustomerDao customerJDBCDao;
    @Before
    public void setup(){ customerJDBCDao = new CustomerJDBCDaoImpl(); }

    @After
    public void cleanUp(){};

    @Test
    public void saveTest(){
        Customer customer = new Customer(3,"大人小李","1234567","2B@shabi.com","小碎李");


    }
    @Test
    public void getTest(){
        List<Customer> customers = customerJDBCDao.getCustomers();
        Assert.assertEquals("amount check",3,customers.size());
    }

}
