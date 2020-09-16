package com.ascending.jdbc;

import com.ascending.dao.ConfigDao;
import com.ascending.dao.OrderDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Config;
import com.ascending.model.Order;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class OrderJDBCDaoTest {
    @Autowired
    @Qualifier("OrderJDBCDaoImpl")
    private OrderDao orderDao;

    @Autowired
    @Qualifier("ConfigJDBCDaoImpl")
    private ConfigDao configDao;

    private Order testOrder;
    private Config testConfig;

    @Before
    public void setup(){
        testConfig = configDao.getConfigById(1l);
        testOrder = new Order();
        testOrder.setOrderNumber("testNumber");
        orderDao.save(testOrder,testConfig);
        testOrder = orderDao.getOrderByName(testOrder.getOrderNumber());
    }
    @After
    public void cleanUp(){orderDao.delete(testOrder);}

    @Test
    public void saveOrderTest(){
        Order order = testOrder;
        orderDao.save(order,testConfig);
        Assert.assertEquals("order objects comparison",testOrder,order);
    }

    @Test
    public void deleteOrderTest(){
        boolean ifDelete = orderDao.delete(testOrder);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateOrderTest(){
        testOrder.setOrderNumber("UpdateNumber");
        boolean ifUpdate = orderDao.update(testOrder,testConfig);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getAllOrdersTest(){
        List<Order> orders = orderDao.getOrders();
        Assert.assertEquals("order amount comparison",5,orders.size());

    }

    @Test
    public void deleteOrderByNameTest(){
        boolean ifDelete = orderDao.deleteByOrderName(testOrder.getOrderNumber());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getOrderByIdTest(){
        Order order = orderDao.getOrderById(testOrder.getId());
        Assert.assertEquals("order objects comparison",testOrder,order);
    }

    @Test
    public void getOrderByNameTest(){
        Order order = orderDao.getOrderByName(testOrder.getOrderNumber());
        Assert.assertEquals("order objects comparison",testOrder,order);
    }
}
