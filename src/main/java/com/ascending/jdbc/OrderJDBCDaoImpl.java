package com.ascending.jdbc;

import com.ascending.dao.OrderDao;
import com.ascending.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderJDBCDaoImpl implements OrderDao {
    Logger logger = LoggerFactory.getLogger(OrderJDBCDaoImpl.class);
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/windowsDB";
    private static final String USER = "admin";
    private static final String PASS = "123456";

    @Override
    public Order save(Order order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Order savedOrder = null;
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql_save = "INSERT INTO oders (name,price,requirement)values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql_save);
            int row = preparedStatement.executeUpdate();
            if (row>0){
                logger.info("new Order insert successfully");
                savedOrder = order;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (preparedStatement!=null) preparedStatement.close();
                if (connection!=null) connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return savedOrder;
    }

    @Override
    public boolean delete(Order order) {
        return false;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public boolean deleteByOrderName(String name) {
        return false;
    }

    @Override
    public Order getOrderById(long id) {
        return null;
    }

    @Override
    public Order getOrderByName(String orderNumber) {
        return null;
    }
}
