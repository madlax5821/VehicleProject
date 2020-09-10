package com.ascending.repository;

import com.ascending.dao.CustomerDao;
import com.ascending.model.Customer;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class CustomerDaoImpl implements CustomerDao {
    private Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);
    @Override
    public Customer save(Customer customer) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
            session.close();
        }catch (Exception e){
            if(transaction!=null)transaction.rollback();
            logger.info("Failed to insert customer object, error="+e.getMessage());
            session.close();
        }
        return customer;
    }

    @Override
    public boolean delete(Customer customer) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        int ifDelete = 0;
        try {
            transaction = session.beginTransaction();
            session.delete(customer);
            transaction.commit();
            session.close();
            ifDelete = 1;
        }catch (Exception e){
            if (transaction!=null)transaction.rollback();
            logger.info("Failed to delete customer, error="+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public boolean update(Customer customer) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        int ifUpdate = 0;
        try {
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
            session.close();
            ifUpdate=1;
        }catch (Exception e){
            if (transaction!=null)transaction.rollback();
            logger.info("failed to update customer, error="+e.getMessage());
            session.close();
        }
        return ifUpdate>0;
    }

    @Override
    public List<Customer> getCustomers() {
        String hql_getAll = "from Customer";
        try(Session session = HibernateUtil.getSession()){
            Query<Customer> query = session.createQuery(hql_getAll);
            return query.list();
        }
    }

    @Override
    public boolean deleteByName(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        String hql_deleteByName = "From Customer as cus where cus.name=:name";
        int ifDelete = 0;
        try {
            transaction = session.beginTransaction();
            Query<Customer> query = session.createQuery(hql_deleteByName);
            query.setParameter("name",name);
            for (Customer customer:query.list()){
                session.delete(customer);
            }
            transaction.commit();
            session.close();
            ifDelete=1;
        }catch (Exception e){
            if(transaction!=null) transaction.rollback();
            logger.info("Failed to delete customer by name, error="+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public Customer getCustomerById(long id) {
        String hql_getById = "From Customer as cus where cus.id=:id";
        try(Session session = HibernateUtil.getSession()){
            Query<Customer> query = session.createQuery(hql_getById);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }

    @Override
    public Customer getCustomerByName(String name) {
        String hql_getByName = "From Customer as cus where cus.name = :name";
        try (Session session = HibernateUtil.getSession()){
            Query<Customer> query = session.createQuery(hql_getByName);
            query.setParameter("name",name);
            List<Customer> customers = query.list();
            Random r = new Random();
            return customers.get(r.nextInt(customers.size()));
        }
    }

    public static void main(String[] args) {
        Customer customer = new Customer("name","123456","123@123","stupid");
        CustomerDao customerDao = new CustomerDaoImpl();
        customerDao.save(customer);
        System.out.println(customerDao.deleteByName(customer.getName()));
    }
}
