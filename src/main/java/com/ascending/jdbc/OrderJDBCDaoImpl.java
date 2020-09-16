package com.ascending.jdbc;


import com.ascending.dao.OrderDao;
import com.ascending.model.Config;
import com.ascending.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("OrderJDBCDaoImpl")
public class OrderJDBCDaoImpl implements OrderDao {
    Logger logger = LoggerFactory.getLogger(OrderJDBCDaoImpl.class);
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/windowsDB";
    private static final String USER = "admin";
    private static final String PASS = "123456";

    @Override
    public Order save(Order order, Config config) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Order savedOrder = null;
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            order.setConfig(config);
            String sql_save = "INSERT INTO orders (ORDER_NUMBER,CONFIG_ID)values(?,?)";
            preparedStatement = connection.prepareStatement(sql_save);
            preparedStatement.setString(1,order.getOrderNumber());
            preparedStatement.setLong(2,config.getId());
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql_delete = "DELETE FROM orders WHERE ID=?";
            preparedStatement = connection.prepareStatement(sql_delete);
            preparedStatement.setLong(1,order.getId());
            int row = preparedStatement.executeUpdate();
            if (row>0){
                logger.info("Delete order successfully.");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (preparedStatement!=null)preparedStatement.close();
                if (connection!=null)connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update(Order order, Config config) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql_update = "UPDATE orders SET ORDER_NUMBER=? WHERE ID=?";
            preparedStatement = connection.prepareStatement(sql_update);
            preparedStatement.setString(1,order.getOrderNumber());
            preparedStatement.setLong(2,order.getId());
            int row = preparedStatement.executeUpdate();
            if (row >0){
                logger.info("Update order object successfully");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (preparedStatement!=null)preparedStatement.close();
                if (connection!=null)connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = connection.createStatement();
            String sql_getAll = "SELECT * FROM ORDERS";
            resultSet = statement.executeQuery(sql_getAll);
            while(resultSet.next()){
                long id = resultSet.getLong("id");
                String orderNumber = resultSet.getString("order_number");
                BigDecimal price = resultSet.getBigDecimal("price");
                Date date = resultSet.getDate("purchase_date");
                String requirement = resultSet.getString("requirement");
                long configId = resultSet.getLong("config_id");

                Order order = new Order();
                order.setId(id);
                order.setOrderNumber(orderNumber);
                order.setPrice(price);
                order.setPurchaseDate(date);
                order.setRequirement(requirement);
                order.setConfigId(configId);
                orders.add(order);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (statement!=null) statement.close();
                if (connection!=null) connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return orders;
    }

    @Override
    public boolean deleteByOrderName(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql_deleteByName = "DELETE FROM orders WHERE ORDER_NUMBER = ?";
            preparedStatement = connection.prepareStatement(sql_deleteByName);
            preparedStatement.setString(1,name);
            int row = preparedStatement.executeUpdate();
            if (row>0){
                logger.info("Delete order object by name successfully");
                return true;
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
        return false;
    }

    @Override
    public Order getOrderById(long id) {
        Connection conn = null;
        Statement stmt =null;
        ResultSet rs = null;
        Order order = new Order();
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql_getAll = "SELECT * FROM ORDERS";
            rs = stmt.executeQuery(sql_getAll);
            while(rs.next()){
                long id1 = rs.getLong("id");
                String orderNumber = rs.getString("order_number");
                BigDecimal price = rs.getBigDecimal("price");
                Date date = rs.getDate("purchase_date");
                String requirement = rs.getString("requirement");
                long configId = rs.getLong("config_id");
                if (id1==id){
                    order.setId(id1);
                    order.setOrderNumber(orderNumber);
                    order.setPrice(price);
                    order.setPurchaseDate(date);
                    order.setRequirement(requirement);
                    order.setConfigId(configId);
                    break;
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (stmt!=null)stmt.close();
                if (conn!=null)conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return order;
    }

    @Override
    public Order getOrderByName(String orderNumber) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        Order order = new Order();
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = connection.createStatement();
            String sql_getAll = "SELECT * FROM ORDERS";
            rs = statement.executeQuery(sql_getAll);
            while (rs.next()){
                long id = rs.getLong("id");
                String orderNumber1 = rs.getString("order_number");
                BigDecimal price = rs.getBigDecimal("price");
                Date date = rs.getDate("purchase_date");
                String requirement = rs.getString("requirement");
                Long configId = rs.getLong("config_id");
                if(orderNumber1.equals(orderNumber)){
                    order.setId(id);
                    order.setOrderNumber(orderNumber1);
                    order.setPrice(price);
                    order.setPurchaseDate(date);
                    order.setRequirement(requirement);
                    order.setConfigId(configId);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (statement!=null)statement.close();
                if (connection!=null)connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return order;
    }
}
