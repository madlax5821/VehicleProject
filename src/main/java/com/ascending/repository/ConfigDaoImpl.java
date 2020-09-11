package com.ascending.repository;

import com.ascending.dao.ConfigDao;
import com.ascending.model.Config;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfigDaoImpl implements ConfigDao {
    private Logger logger = LoggerFactory.getLogger(ConfigDaoImpl.class);

    @Override
    public Config save(Config config) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            session.save(config);
            transaction.commit();
            session.close();
        }catch(Exception e){
            if (transaction!=null)transaction.rollback();
            logger.info("Failed to save config object, error="+e.getMessage());
            session.close();
        }
        return config;
    }

    @Override
    public boolean delete(Config config) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        int ifDelete = 0;
        try {
            transaction = session.getTransaction();
            session.delete(config);
            transaction.commit();
            session.close();
            ifDelete = 1;
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to delete config object, error="+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public boolean update(Config config) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int ifUpdate = 0;
        try {
            transaction = session.getTransaction();
            session.update(config);
            transaction.commit();
            session.close();
            ifUpdate=1;
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to update config object, error="+e.getMessage());
            session.close();
        }
        return ifUpdate>0;
    }

    @Override
    public List<Config> getConfigs() {
        String hql_getAll = "from Config";
        try(Session session = HibernateUtil.getSession()){
            Query<Config> query = session.createQuery(hql_getAll);
            return query.list();
        }
    }

    @Override
    public boolean deleteByName(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        String hql_deleteByName = "from Config as con where con.configName=:name";
        int ifDeleteByName = 0;
        try {
            transaction = session.getTransaction();
            Query<Config> query = session.createQuery(hql_deleteByName);
            query.setParameter("name",name);
            for (Config config:query.list()){
                session.delete(config);
            }
            transaction.commit();
            session.close();
            ifDeleteByName=1;
        }catch (Exception e){
            if (transaction!=null) transaction.rollback();
            logger.info("Failed to delete config object by name, error="+e.getMessage());
            session.close();
        }
        return false;
    }

    @Override
    public Config getConfigById(long id) {
        String hql_getById = "from Config as con where con.id=:id";
        try(Session session = HibernateUtil.getSession()){
            Query<Config> query = session.createQuery(hql_getById);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }

    @Override
    public Config getConfigByName(String name) {
        String hql_getByName = "from Config as con where con.configName=:name";
        try(Session session = HibernateUtil.getSession()){
            Query<Config> query = session.createQuery(hql_getByName);
            query.setParameter("name",name);
            List<Config> configs = query.list();
            Random r = new Random();
            return configs.get(r.nextInt(configs.size()));
        }
    }
}
