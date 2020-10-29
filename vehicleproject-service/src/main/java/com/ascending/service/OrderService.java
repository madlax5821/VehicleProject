package com.ascending.service;

import com.ascending.dao.OrderDao;
import com.ascending.model.Config;
import com.ascending.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    @Qualifier("OrderDaoImpl")
    private OrderDao orderDao;

    public Order saveOrder(Order order, Config config){return orderDao.save(order, config);}

    public boolean deleteOrder(Order order){return orderDao.delete(order);}

    public boolean updateOrder(Order order,Config config){return orderDao.update(order,config);}

    public List<Order> getAllOrders(){return orderDao.getOrders();}

    public boolean deleteOrderByName(String name){return orderDao.deleteByOrderName(name);}

    public Order getOrderById(long id){return orderDao.getOrderById(id);}

    public Order getOrderByname(String name){return orderDao.getOrderByName(name);}
}
