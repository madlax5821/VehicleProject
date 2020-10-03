package com.ascending.controller;

import com.ascending.model.Customer;
import com.ascending.model.Order;
import com.ascending.service.CustomerService;
import com.ascending.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Customer")
public class CustomerController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "saveCustomer", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer saveCustomer(@RequestBody Customer customer, Order order){
        order = orderService.getOrderById(customer.getOrderId());
        customerService.saveCustomer(customer,order);
        return customer;
    }

    @DeleteMapping(value = "deleteCustomer", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteCustomer(@RequestBody Customer customer){
        return customerService.deleteCustomer(customer);
    }

    @PutMapping(value = "updateCustomer", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateCustomer(@RequestBody Customer customer, Order order){
        order = orderService.getOrderById(customer.getOrderId());
        return customerService.updateCustomer(customer,order);
    }

    @GetMapping(value = "getCustomers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return customers;
    }

    @DeleteMapping(value = "deleteCustomerByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteCustomerByName(@RequestParam("name")String name){
        return customerService.deleteCustomerByName(name);
    }

    @GetMapping(value = "getCustomerById", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomerById(@RequestParam("id") long id){
        Customer customer = customerService.getCustomerById(id);
        return customer;
    }

    @GetMapping(value = "getCustomerByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomerByName(@RequestParam("name") String name){
        Customer customer = customerService.getCustomerByName(name);
        return customer;
    }
}
