package com.ascending.jdbc;

import com.ascending.dao.BrandDao;
import com.ascending.model.Brand;
import com.ascending.repository.BrandDaoImpl;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("BrandJDBCDaoImpl")
public class BrandJDBCDaoImpl implements BrandDao {
    private Logger logger = LoggerFactory.getLogger(BrandJDBCDaoImpl.class);
    //STEP 1: Database information
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/windowsDB";
    private static final String USER = "admin";
    private static final String PASS = "123456";

    @Override
    public Brand save(Brand brand) {
        Brand createdBrand = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            //STEP 2: Open a connection
            logger.info("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            logger.info("Insert statement...");

            String SQL_INSERT = "INSERT INTO brand (NAME, NATIONALITY, DESCRIPTION) VALUES (?,?,?)";
            preparedStatement = conn.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, brand.getName());
            preparedStatement.setString(2, brand.getNationality());
            preparedStatement.setString(3, brand.getDescription());
            int row = preparedStatement.executeUpdate();
            if (row > 0)
                createdBrand = brand;
                logger.info("object has been inserted successfully...");
        } catch (SQLException throwables) {
            logger.info("Call save(...) for brand throws SQLException, error=" + throwables.getMessage());
        } finally {
            //STEP 4: finally block used to close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                logger.info("Call save(...) for brand throws SQLException, error=" + se.getMessage());
            }
        }
        return createdBrand;
    }

    @Override
    public boolean delete(Brand brand) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            //1. connect to database
            logger.info("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //2. execute a query command
            logger.info("execute a query");
            String sql_delete = "DELETE FROM brand WHERE id=?";
            preparedStatement = conn.prepareStatement(sql_delete);
            preparedStatement.setLong(1,brand.getId());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                logger.info("Data delete completed...");
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                logger.error("Call delete(...) for brand throws SQLException, error=" + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean update(Brand brand) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int ifUpdate =0;
        try {
            logger.info("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql_update = "UPDATE brand SET nationality=?, description=? WHERE name=?";
            preparedStatement = conn.prepareStatement(sql_update);
            preparedStatement.setString(1, brand.getNationality());
            preparedStatement.setString(2, brand.getDescription());
            preparedStatement.setString(3, brand.getName());
            int rows = preparedStatement.executeUpdate();
            if (rows>0){
                logger.info("data updated successfully");
                ifUpdate=1;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try{
                if (preparedStatement!=null){preparedStatement.close();}
                if (conn!=null){conn.close();}
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ifUpdate>0;
    }

    @Override
    public List<Brand> getBrands() {
        List<Brand> brands = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        //STEP 2: Open a connection
        logger.info("Connecting to database...");
        try {
            //Step 2: Open a connection
            logger.info("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            logger.info("establish data into resultset object");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM brand";
            rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String nationality = rs.getString("nationality");
                String description = rs.getString("description");

                //Fill the object
                Brand brand = new Brand();
                brand.setId(id);
                brand.setName(name);
                brand.setNationality(nationality);
                brand.setDescription(description);
                brands.add(brand);

            }
        } catch (SQLException throwables) {
            logger.error("error is " + throwables.getMessage());
        } finally {
            //STEP 5: finally block used to close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                //se.printStackTrace();
                logger.error("error is " + se.getMessage());
            }

        }

        return brands;
    }

    @Override
    public boolean deleteByName(String name) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql_deleteByName = "DELETE FROM brand WHERE name=?";
            preparedStatement = conn.prepareStatement(sql_deleteByName);
            preparedStatement.setString(1, name);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                logger.info("Deleting by name successfully implemented.");
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                logger.error("Call delete(...) for brand throws SQLException, error=" + e.getMessage());
            }
        }
        return false;

    }

    @Override
    public Brand getBrandById(Long id) {
        Brand targetBrand = new Brand();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql_ByCol = "SELECT * FROM brand";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql_ByCol);
            while (resultSet.next()){
                Long id1 = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String nationality = resultSet.getString("nationality");
                String description = resultSet.getString("description");
                if (id1.equals(id)){
                    targetBrand.setId(id1);
                    targetBrand.setName(name);
                    targetBrand.setNationality(nationality);
                    targetBrand.setDescription(description);
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try{
                if(statement!=null){statement.close();}
                if(conn!=null){conn.close();}
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return targetBrand;
    }

    @Override
    public Brand getBrandByName(String brandName) {
        Brand targetBrand = new Brand();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs;
        List<Brand> brands = new ArrayList<>();
        //STEP 2: Open a connection
        logger.info("Connecting to database...");
        try {
            //Step 2: Open a connection
            logger.info("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            logger.info("establish data into resultset object");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM brand";
            rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String nationality = rs.getString("nationality");
                String description = rs.getString("description");

                if (name.equals(brandName)){

                    targetBrand.setId(id);
                    targetBrand.setName(name);
                    targetBrand.setNationality(nationality);
                    targetBrand.setDescription(description);
                    brands.add(targetBrand);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try{
                if(stmt!=null){stmt.close();}
                if (conn!=null){conn.close();}
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return targetBrand;
    }

    @Override
    public List<Brand> getBrandsWithChildren() {
        return null;
    }

    public static void main(String[] args) {
        BrandDao brandDao = new BrandJDBCDaoImpl();
        Brand brand = new Brand("ss","ss","ss");
        brandDao.save(brand);
        brand = brandDao.getBrandByName("ss");

        System.out.println(brandDao.getBrandById(184l));
    }
}
