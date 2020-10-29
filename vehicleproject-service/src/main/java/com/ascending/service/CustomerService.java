package com.ascending.service;

import com.ascending.dao.CustomerDao;
import com.ascending.model.Customer;
import com.ascending.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    @Qualifier("CustomerDaoImpl")
    private CustomerDao customerDao;

    public Customer saveCustomer(Customer customer, Order order){return customerDao.save(customer,order);}

    public boolean deleteCustomer(Customer customer){return customerDao.delete(customer);}

    public boolean updateCustomer(Customer customer, Order order){return customerDao.update(customer, order);}

    public List<Customer> getAllCustomers (){return customerDao.getCustomers();}

    public boolean deleteCustomerByName(String name){return customerDao.deleteByName(name);}

    public Customer getCustomerById(long id){return customerDao.getCustomerById(id);}

    public Customer getCustomerByName(String name){return customerDao.getCustomerByName(name);}
}
