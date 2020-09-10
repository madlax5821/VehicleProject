package com.ascending.repository;

import com.ascending.dao.ModelDao;
import com.ascending.model.Brand;
import com.ascending.model.Model;
import com.ascending.util.HibernateUtil;
import com.sun.corba.se.impl.oa.toa.TransientObjectManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModelDaoImpl implements ModelDao {
    private Logger logger = LoggerFactory.getLogger(ModelDaoImpl.class);

    @Override
    public Model save(Model model) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(model);
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null)
                transaction.rollback();
            logger.info("failed to insert model data, error="+e.getMessage());
            session.close();
        }
        return model;
    }

    @Override
    public boolean delete(Model model) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        int ifDeleted=0;
        try {
            transaction = session.beginTransaction();
            session.delete(model);
            transaction.commit();
            session.close();
            ifDeleted = 1;
        }catch (Exception e){
            if(transaction!=null){
                transaction.rollback();
            }
            logger.info("failed to delete model, error="+e.getMessage());
            session.close();
        }
        return ifDeleted>0;
    }

    @Override
    public boolean update(Model model) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        int ifUpdated = 0;
        try{
            transaction = session.beginTransaction();
            session.update(model);
            transaction.commit();
            session.close();
            ifUpdated = 1;
        }catch (Exception e){
            if (transaction!=null)transaction.rollback();
            logger.info("Failed to update model data, error="+e.getMessage());
            session.close();
        }
        return ifUpdated>0;
    }

    @Override
    public List<Model> getModels() {
        String hql_getAll = "from Model as m";
        try(Session session = HibernateUtil.getSession()){
            Query<Model> query = session.createQuery(hql_getAll);
            return query.list();
        }
    }

    @Override
    public boolean deleteByName(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        int ifDelete = 0;
        String hql_getByName = "From Model as m where m.modelName=:name";
        try{
            transaction = session.beginTransaction();
            Query<Model> query = session.createQuery(hql_getByName);
            query.setParameter("name",name);
            for (Model model:query.list()){
                session.delete(model);
            }
            transaction.commit();
            session.close();
            ifDelete = 1;
        }catch (Exception e){
            if (transaction!=null)transaction.rollback();
            logger.info("failed to delete model by name, error="+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public Model getModelById(long id) {
        String hql_getById = "From Model as m where m.id=:id";
        try(Session session = HibernateUtil.getSession()){
            Query<Model> query = session.createQuery(hql_getById);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }

    @Override
    public Model getModelByName(String name) {
        String hql_getByName = "From Model as m where m.modelName=:name";
        List<Model> models = new ArrayList<>();
        try(Session session = HibernateUtil.getSession()){
            Query<Model> query = session.createQuery(hql_getByName);
            query.setParameter("name",name);
            models=query.list();
            Random r = new Random();
            return models.get(r.nextInt(models.size()));
        }
    }
    private static Brand brand;
    public static void main(String[] args) {
//        ModelDao modelDao = new ModelDaoImpl();
//        Model model = new Model("aaa","aaa","aaa");
//        Model savedModel = modelDao.save(model);
//        System.out.println(savedModel);
//        brand = new BrandDaoImpl().getBrandById(3l);
//        brand.getModels().add(savedModel);
//        savedModel.setBrand(brand);
    }
}
