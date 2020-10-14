package com.ascending.repository;
import com.ascending.dao.BrandDao;
import com.ascending.dao.ModelDao;
import com.ascending.model.Brand;
import com.ascending.model.Model;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Random;

@Repository("ModelDaoImpl")
public class ModelDaoImpl implements ModelDao {
    private Logger logger = LoggerFactory.getLogger(ModelDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Model save(Model model,Brand brand) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            model.setBrand(brand);
            session.save(model);
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null)
                logger.info("failed to insert model data, error="+e.getMessage());
                transaction.rollback();
                session.close();
        }
        return model;
    }

    @Override
    public boolean delete(Model model) {
        Session session = sessionFactory.openSession();
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
    public boolean update(Model model, Brand brand) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int ifUpdated = 0;
        try{
            transaction = session.beginTransaction();
            model.setBrand(brand);
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
        //String hql_getAll = "from Model";
        String hql_getAll = "from Model as mos join fetch mos.brand as br left join fetch mos.configs as cons left join fetch cons.orders as ors left join fetch ors.customer as cus";
        try(Session session = sessionFactory.openSession()){
            Query<Model> query = session.createQuery(hql_getAll);
            return query.list();
        }
    }

    @Override
    public boolean deleteByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        int ifDelete = 0;
        String hql_getByName = "delete From Model as m where m.modelName=:name";
        try{
            transaction = session.beginTransaction();
            Query<Model> query = session.createQuery(hql_getByName);
            query.setParameter("name",name);
            ifDelete = query.executeUpdate();
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null)transaction.rollback();
            logger.info("failed to delete model by name, error="+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public Model getModelById(long id) {
        String hql_getById = "From Model as m left join fetch m.configs as c left join fetch c.orders as o left join fetch o.customer where m.id=:id";
        try(Session session = sessionFactory.openSession()){
            Query<Model> query = session.createQuery(hql_getById);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }

    @Override
    public Model getModelByName(String name) {
        String hql_getByName = "From Model as m left join fetch m.configs as c left join fetch c.orders as o left join fetch o.customer where m.modelName=:name";
        List<Model> models;
        try(Session session = sessionFactory.openSession()){
            Query<Model> query = session.createQuery(hql_getByName);
            query.setParameter("name",name);
            models=query.list();
            Random r = new Random();
            return models.get(r.nextInt(models.size()));
        }
    }
}
