package com.ascending.jdbc;

import com.ascending.dao.ModelDao;
import com.ascending.model.Brand;
import com.ascending.model.Model;
import com.ascending.util.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("ModelJDBCDaoImpl")
public class ModelJDBCDaoImpl implements ModelDao {
    Logger logger = LoggerFactory.getLogger(ModelJDBCDaoImpl.class);
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/windowsDB";
    private static final String USER = "admin";
    private static final String PASS = "123456";

    @Override
    public Model save(Model model, Brand brand) {
        Model createdModel = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql_save = "INSERT INTO model(NAME,TYPE,DESCRIPTION,BRAND_ID)VALUES(?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql_save);
            preparedStatement.setString(1,model.getModelName());
            preparedStatement.setString(2,model.getVehicleType());
            preparedStatement.setString(3,model.getDescription());
            preparedStatement.setLong(4,brand.getId());
            int row = preparedStatement.executeUpdate();
            if(row>0){
                logger.info("new Model insert successfully");
                createdModel = model;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(preparedStatement!=null)preparedStatement.close();
                if(conn!=null)conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return createdModel;
    }

    @Override
    public boolean delete(Model model) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String sql_delete = "DELETE FROM model WHERE id=?";
            preparedStatement = conn.prepareStatement(sql_delete);
            preparedStatement.setLong(1,model.getId());
            int row = preparedStatement.executeUpdate();
            if (row>0){
                logger.info("Model successfully deleted by id");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(preparedStatement!=null)preparedStatement.close();
                if(conn!=null)conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update(Model model, Brand brand) {
        PreparedStatement preparedStatement = null;
        String sql_update = "UPDATE model SET NAME=?,TYPE=?,DESCRIPTION=? WHERE ID=?";
        try {
            preparedStatement = JDBCUtil.getConnection().prepareStatement(sql_update);
            preparedStatement.setString(1,model.getModelName());
            preparedStatement.setString(2,model.getVehicleType());
            preparedStatement.setString(3,model.getDescription());
            preparedStatement.setLong(4,model.getId());
            int row = preparedStatement.executeUpdate();
            if (row>0){
                logger.info("update model successfully");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (preparedStatement!=null)preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Model> getModels() {
        Statement statement = null;
        List<Model> models = new ArrayList<>();
        String sql_getAll = "SELECT * FROM model";
        ResultSet resultSet = null;
        try {
            statement = JDBCUtil.getConnection().createStatement();
            resultSet = statement.executeQuery(sql_getAll);
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");

                Model model = new Model();
                model.setId(id);
                model.setModelName(name);
                model.setVehicleType(type);
                model.setDescription(description);
                models.add(model);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (statement!=null)statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return models;
    }

    @Override
    public boolean deleteByName(String name) {
        PreparedStatement preparedStatement = null;
        String sql_DeleteByName = "DELETE FROM model where name= ?";
        try {
            preparedStatement = JDBCUtil.getConnection().prepareStatement(sql_DeleteByName);
            preparedStatement.setString(1,name);
            int row =preparedStatement.executeUpdate();
            if (row>0){
                logger.info("Delete by name successfully implemented");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (preparedStatement!=null)preparedStatement.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Model getModelById(long id) {
        Model model = new Model();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet =null;
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = connection.createStatement();
            String sql = "SELECT * FROM model";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                long id1 = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");

                if (id1==id){
                    model.setId(id);
                    model.setModelName(name);
                    model.setVehicleType(type);
                    model.setDescription(description);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if(statement!=null)statement.close();
                if(connection!=null)statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return model;
    }

    @Override
    public Model getModelByName(String name) {
        Model model = new Model();
        Statement statement = null;
        ResultSet resultSet;
        String sql_getModelByName = "SELECT * FROM model";
        try {
            statement = JDBCUtil.getConnection().createStatement();
            resultSet = statement.executeQuery(sql_getModelByName);
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String targetName = resultSet.getString("name");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                if (targetName.equals(name)){
                    model.setId(id);
                    model.setModelName(targetName);
                    model.setVehicleType(type);
                    model.setDescription(description);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (statement!=null)statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return model;
    }
}
