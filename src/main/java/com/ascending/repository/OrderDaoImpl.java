package com.ascending.repository;

import com.ascending.dao.OrderDao;
import com.ascending.model.Config;
import com.ascending.model.Order;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;
@Repository("OrderDaoImpl")
public class OrderDaoImpl implements OrderDao {
    private Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Override
    public Order save(Order order, Config config) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            order.setConfig(config);
            session.save(order);
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null)transaction.rollback();
            logger.info("failed to insert order, error="+e.getMessage());
            session.close();
        }
        return order;
    }

    @Override
    public boolean delete(Order order) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        int ifDelete = 0;
        try {
            transaction = session.beginTransaction();
            session.delete(order);
            transaction.commit();
            session.close();
            ifDelete=1;
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to delete order, error="+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public boolean update(Order order, Config config) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        int ifUpdate = 0;
        try {
            transaction = session.beginTransaction();
            order.setConfig(config);
            session.update(order);
            transaction.commit();
            session.close();
            ifUpdate=1;
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to update order, error="+e.getMessage());
            session.close();
        }
        return ifUpdate>0;
    }

    @Override
    public List<Order> getOrders() {
        String hql_getAll = "from Order";
        try (Session session = HibernateUtil.getSession()){
            Query<Order> query = session.createQuery(hql_getAll);
            return query.list();
        }
    }

    @Override
    public boolean deleteByOrderName(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        String hql_deleteByName = "from Order as o where o.orderNumber=:order_number";
        int ifDelete = 0;
        try {
            transaction = session.beginTransaction();
            Query<Order> query = session.createQuery(hql_deleteByName);
            query.setParameter("order_number",name);
            for (Order order:query.list()){
                session.delete(order);
            }
            transaction.commit();
            session.close();
            ifDelete=1;
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to delete order by name, error="+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public Order getOrderById(long id) {
        String hql_getById = "from Order as o where o.id=:id";
        try(Session session = HibernateUtil.getSession()){
            Query<Order> query = session.createQuery(hql_getById);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }

    @Override
    public Order getOrderByName(String orderNumber) {
        String hql_getByName = "From Order as o where o.orderNumber=:order_number";
        try(Session session = HibernateUtil.getSession()){
            Query<Order> query = session.createQuery(hql_getByName);
            query.setParameter("order_number",orderNumber);
            List<Order> orders = query.list();
            Random r = new Random();
            return orders.get(r.nextInt(orders.size()));
        }
    }

}
