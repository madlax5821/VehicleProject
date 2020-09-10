package com.ascending.dao;

import com.ascending.model.Customer;

import java.util.List;

public interface CustomerDao {
    Customer save(Customer customer);
    boolean delete(Customer customer);
    boolean update(Customer customer);
    List<Customer> getCustomers();
    boolean deleteByName(String name);
    Customer getCustomerById(long id);
    Customer getCustomerByName(String name);
}
