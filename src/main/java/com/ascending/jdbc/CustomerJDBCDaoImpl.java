package com.ascending.jdbc;

import com.ascending.dao.CustomerDao;
import com.ascending.model.Customer;
import com.ascending.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository("CustomerJDBCDaoImpl")
public class CustomerJDBCDaoImpl implements CustomerDao {
    Logger logger = LoggerFactory.getLogger(CustomerJDBCDaoImpl.class);
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/windowsDB";
    private static final String USER = "admin";
    private static final String PASS = "123456";

    @Override
    public Customer save(Customer customer, Order order) {
        Customer createdCustomer = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            customer.setOrder(order);
            String sql_save = "INSERT INTO customer(NAME,ORDER_ID)VALUES(?,?)";
            preparedStatement = conn.prepareStatement(sql_save);
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setLong(2,order.getId());
            int row = preparedStatement.executeUpdate();
            if (row>0){
                logger.info("Customer insert successfully");
                createdCustomer = customer;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try {
                if(preparedStatement!=null){preparedStatement.close();}
                if(conn!=null){conn.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return createdCustomer;
    }

    @Override
    public boolean delete(Customer customer) {
        Connection conn = null;
        PreparedStatement preparedStatement= null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql_delete = "DELETE FROM customer where id = ?";
            preparedStatement = conn.prepareStatement(sql_delete);
            preparedStatement.setLong(1,customer.getId());
            int row = preparedStatement.executeUpdate();
            if (row>0){
                logger.info("Data deleted by ID successfully.");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(preparedStatement!=null){preparedStatement.close();}
                if(conn!=null){conn.close();}
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update(Customer customer, Order order) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql_update = "UPDATE customer SET NAME=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql_update);
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setLong(2,customer.getId());
            int row = preparedStatement.executeUpdate();
            if (row >0){
                logger.info("update data successfully");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(preparedStatement!=null){preparedStatement.close();}
                if(conn!=null){conn.close();}
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Customer> getCustomers() {
        Connection conn = null;
        List<Customer> customers = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = conn.createStatement();
            String sql_get = "SELECT * FROM customer";
            resultSet = statement.executeQuery(sql_get);
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("cell_number");
                String email = resultSet.getString("email");
                String information = resultSet.getString("information");
                Customer customer = new Customer();
                customer.setId(id);
                customer.setName(name);
                customer.setPhoneNumber(phoneNumber);
                customer.setEmail(email);
                customer.setInformation(information);
                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(statement!=null){statement.close();}
                if(conn!=null){conn.close();}
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return customers;
    }

    @Override
    public boolean deleteByName(String name) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int result =0;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql_delByName = "Delete FROM customer WHERE name=?";
            preparedStatement = conn.prepareStatement(sql_delByName);
            preparedStatement.setString(1,name);
            int row = preparedStatement.executeUpdate();
            if (row>0){
                logger.info("delete by name successfully");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (preparedStatement!=null){preparedStatement.close();}
                if (conn!=null){conn.close();}
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Customer getCustomerById(long id) {
        Connection conn = null;
        Statement statement = null;
        Customer customer = new Customer();
        ResultSet resultSet = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = conn.createStatement();
            String sql_byId = "SELECT * FROM customer";
            resultSet = statement.executeQuery(sql_byId);
            while(resultSet.next()){
                long id1 = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("cell_number");
                String email = resultSet.getString("email");
                String information = resultSet.getString("information");
                if (id1 == id){
                    customer.setId(id1);
                    customer.setName(name);
                    customer.setPhoneNumber(phoneNumber);
                    customer.setEmail(email);
                    customer.setInformation(information);
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(statement!=null){statement.close();}
                if(conn!=null){conn.close();}
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return customer;
    }

    @Override
    public Customer getCustomerByName(String name) {
        Connection conn = null;
        Statement statement = null;
        Customer customer = new Customer();
        ResultSet resultSet = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = conn.createStatement();
            String sql_getByName = "SELECT * FROM customer";
            resultSet = statement.executeQuery(sql_getByName);
            while(resultSet.next()){
                long id = resultSet.getLong("id");
                String name1 = resultSet.getString("name");
                String phoneNumber = resultSet.getString("cell_number");
                String email = resultSet.getString("email");
                String information = resultSet.getString("information");
                if (name1.equals(name)){
                    customer.setId(id);
                    customer.setName(name1);
                    customer.setPhoneNumber(phoneNumber);
                    customer.setEmail(email);
                    customer.setInformation(information);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(statement!=null)statement.close();
                if(conn!=null)conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return customer;
    }
}
