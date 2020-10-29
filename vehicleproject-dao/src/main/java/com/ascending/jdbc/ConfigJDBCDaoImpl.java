package com.ascending.jdbc;

import com.ascending.dao.ConfigDao;
import com.ascending.dao.ModelDao;
import com.ascending.model.Config;
import com.ascending.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("ConfigJDBCDaoImpl")
public class ConfigJDBCDaoImpl implements ConfigDao {
    Logger logger = LoggerFactory.getLogger(ConfigJDBCDaoImpl.class);
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/windowsDB";
    private static final String USER = "admin";
    private static final String PASS = "123456";

    @Override
    public Config save(Config config, Model model) {
        Config createdConfig = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql_save = "INSERT INTO config(NAME,MODEL_ID)VALUES(?,?)";
            preparedStatement = conn.prepareStatement(sql_save);
            preparedStatement.setString(1, config.getConfigName());
            preparedStatement.setLong(2, model.getId());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                logger.info("object has been inserted successfully...");
                createdConfig = config;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return createdConfig;
    }

    @Override
    public boolean delete(Config config) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int ifDelete = 0;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql_delete = "DELETE FROM config where id=?";
            preparedStatement = conn.prepareStatement(sql_delete);

            preparedStatement.setLong(1,config.getId());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                logger.info("Data delete completed...");
                ifDelete=1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ifDelete>0;
    }

    @Override
    public boolean update(Config config, Model model) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int ifUpdate = 0;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql_update = "UPDATE config SET key_feature=?, name=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql_update);
            preparedStatement.setString(1, config.getKeyFeatures());
            preparedStatement.setString(2, config.getConfigName());
            preparedStatement.setLong(3, config.getId());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                logger.info("data updated successfully");
                ifUpdate=1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    preparedStatement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ifUpdate>0;
    }

    @Override
    public List<Config> getConfigs() {
        List<Config> configs = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = conn.createStatement();
            String sql_traverse = "SELECT * FROM config";
            resultSet = statement.executeQuery(sql_traverse);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String feature = resultSet.getString("key_feature");
                Date year = resultSet.getDate("year");
                Config config = new Config();
                config.setId(id);
                config.setConfigName(name);
                config.setKeyFeatures(feature);
                config.setYear(year);
                configs.add(config);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return configs;
    }

    @Override
    public boolean deleteByName(String name) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql_delByName = "DELETE FROM config WHERE name = ?";
            preparedStatement = conn.prepareStatement(sql_delByName);
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
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public Config getConfigById(long id) {
        Config targetConfig = new Config();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = conn.createStatement();
            String sql_getById = "SELECT * FROM config";
            resultSet = statement.executeQuery(sql_getById);
            while (resultSet.next()){
                long id1 = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String feature = resultSet.getString("key_feature");
                Date year = resultSet.getDate("year");
                if (id1==id){
                    targetConfig.setId(id1);
                    targetConfig.setConfigName(name);
                    targetConfig.setKeyFeatures(feature);
                    targetConfig.setYear(year);
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try{
                if (statement!=null){statement.close();}
                if (conn!=null){statement.close();}
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return targetConfig;
    }

    @Override
    public Config getConfigByName(String string) {
        Config targetConfig = new Config();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = conn.createStatement();
            String sql_getByName = "SELECT * FROM config";
            resultSet = statement.executeQuery(sql_getByName);
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String feature = resultSet.getString("key_feature");
                Date year = resultSet.getDate("year");
                long modelId = resultSet.getLong("model_id");
                if (name.equals(string)){
                    targetConfig.setId(id);
                    targetConfig.setConfigName(name);
                    targetConfig.setKeyFeatures(feature);
                    targetConfig.setYear(year);
                    targetConfig.setModelId(modelId);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try {
                if (statement!=null){statement.close();}
                if (conn!=null){conn.close();}
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return targetConfig;
    }
}
