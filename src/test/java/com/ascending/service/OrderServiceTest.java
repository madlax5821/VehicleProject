package com.ascending.service;

import com.ascending.init.AppInitializer;
import com.ascending.model.Config;
import com.ascending.model.Order;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class OrderServiceTest {
    private Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private ConfigService configService;

    private Order testOrder;
    private Config testConfig;

    @Before
    public void setup(){
        testConfig = configService.getConfigById(1l);
        testOrder = new Order();
        testOrder.setOrderNumber("testOrder");
        orderService.saveOrder(testOrder,testConfig);
    }
    @After
    public void cleanUp(){ orderService.deleteOrder(testOrder); }

    @Test
    public void saveOrderTest(){
        Order order = testOrder;
        orderService.saveOrder(order,testConfig);
        Assert.assertEquals("order",testOrder,order);
    }

    @Test
    public void deleteOrderTest(){
        boolean ifDelete = orderService.deleteOrder(testOrder);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateOrderTest(){
        testOrder.setOrderNumber("updateTest");
        boolean ifUpdate = orderService.updateOrder(testOrder,testConfig);
        Assert.assertTrue(ifUpdate);
    }

    @Test
    public void getAllOrdersTest(){
        List<Order> orders = orderService.getAllOrders();
        Assert.assertEquals("order objects amount",5,orders.size());
    }

    @Test
    public void deleteOrderByName(){
        boolean ifDelete = orderService.deleteOrderByName(testOrder.getOrderNumber());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getOrderById(){
        Order order = orderService.getOrderById(testOrder.getId());
        Assert.assertEquals("order objects comparison",testOrder,order);
    }

    @Test
    public void getOrderByName(){
        Order order = orderService.getOrderByname(testOrder.getOrderNumber());
        Assert.assertEquals("order objects comparison",testOrder,order);
    }
}
