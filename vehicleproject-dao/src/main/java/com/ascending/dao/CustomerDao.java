package com.ascending.dao;

import com.ascending.model.Customer;
import com.ascending.model.Order;

import java.util.List;

public interface CustomerDao {
    Customer save(Customer customer, Order order);
    boolean delete(Customer customer);
    boolean update(Customer customer, Order order);
    List<Customer> getCustomers();
    boolean deleteByName(String name);
    Customer getCustomerById(long id);
    Customer getCustomerByName(String name);
}
