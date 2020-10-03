package com.ascending.controller;

import com.ascending.model.Config;
import com.ascending.model.Order;
import com.ascending.service.ConfigService;
import com.ascending.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService orderService;

    @Autowired
    private ConfigService configService;

    @PostMapping(value = "saveOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order saveOrder(@RequestBody Order order, Config config){
        config = configService.getConfigById(order.getConfigId());
        orderService.saveOrder(order,config);
        return order;
    }

    @DeleteMapping(value = "deleteOrder",produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteOrder(@RequestBody Order order){
        return orderService.deleteOrder(order);
    }

    @PutMapping(value = "updateOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateOrder(@RequestBody Order order, Config config){
        config = configService.getConfigById(order.getConfigId());
        return orderService.updateOrder(order, config);
    }

    @GetMapping(value = "getOrders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrders(){
        List<Order> orders = orderService.getAllOrders();
        return orders;
    }

    @DeleteMapping(value = "deleteOrderByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteOrderByName(@RequestParam("name")String name){
        return orderService.deleteOrderByName(name);
    }

    @GetMapping(value = "getOrderById", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrderById(@RequestParam("id")long id){
        Order order = orderService.getOrderById(id);
        return order;
    }

    @GetMapping(value = "getOrderByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrderByName(@RequestParam("name")String name){
        Order order = orderService.getOrderByname(name);
        return order;
    }
}
