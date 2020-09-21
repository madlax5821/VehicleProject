package com.ascending.repository;

import com.ascending.dao.BrandDao;
import com.ascending.dao.ModelDao;
import com.ascending.jdbc.BrandJDBCDaoImpl;
import com.ascending.model.Brand;
import com.ascending.model.Model;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("BrandDaoImpl")
public class BrandDaoImpl implements BrandDao {
    private Logger logger = LoggerFactory.getLogger(BrandDaoImpl.class);

    @Override
    public Brand save(Brand brand) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        try{
            transaction = session.beginTransaction();
            session.save(brand);
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null)
                transaction.rollback();
            logger.error("fail to insert record, error={}",e.getMessage());
            session.close();
        }
    return brand;
    }

    @Override
    public boolean delete(Brand brand) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        int deleteCount = 0;
        try{
            transaction = session.beginTransaction();
            session.delete(brand);
            transaction.commit();
            session.close();
            deleteCount = 1;
        }catch (Exception e){
            if (transaction!=null)
                transaction.rollback();
            logger.error("fail to delete record, error={}",e.getMessage());
            session.close();
        }
        return deleteCount>0;
    }

    @Override
    public boolean update(Brand brand) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int updateCount = 0;
        try {
            transaction = session.beginTransaction();
            session.update(brand);
            transaction.commit();
            session.close();
            updateCount = 1;
        }catch (Exception e){
            if(transaction!=null)
                transaction.rollback();
            logger.error("fail to update record, error={}",e.getMessage());
            session.close();
        }
        return updateCount>0;
    }

    @Override
    public boolean deleteByName(String name) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int ifDelete = 0;
        String hql_deleteByName = "delete from Brand as br where br.name=:name";
        try{
            transaction = session.beginTransaction();
            Query<Brand> query = session.createQuery(hql_deleteByName);
            query.setParameter("name",name);
            ifDelete = query.executeUpdate();
            transaction.commit();
            session.close();
        }catch (Exception e){
            if(transaction!=null){
                transaction.rollback();
                logger.error("fail to delete record by name, error={}",e.getMessage());
                session.close();
            }
        }
        return ifDelete>0;
    }

    @Override
    public Brand getBrandById(Long id) {
        String hql_getById = "from Brand as br left join fetch br.models as m left join fetch m.configs as c left join fetch c.orders as o left join fetch o.customer as c where br.id=:id";
        try(Session session=HibernateUtil.getSession()){
            Query<Brand> query = session.createQuery(hql_getById);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }

    @Override
    public List<Brand> getBrands() {
        String hql_getAll = "select distinct br From Brand as br left join fetch br.models as mos left join fetch mos.configs " +
                "as cons left join fetch cons.orders as o left join fetch o.customer";
//        String hql_getAll = "from Brand";
        try (Session session = HibernateUtil.getSession()) {
            Query<Brand> query = session.createQuery(hql_getAll);
            return query.list();
        }
    }

    @Override
    public Brand getBrandByName(String name) {
        String hql_getbyName = "from Brand as br left join fetch br.models as m left join fetch m.configs as c left join fetch c.orders as o left join fetch o.customer where br.name=:name";
        //String hql_getByName = "from Brand as br";
        try(Session session = HibernateUtil.getSession()){
            Query<Brand> query = session.createQuery(hql_getbyName);
            query.setParameter("name",name);
            return query.uniqueResult();
        }
    }

    @Override
    public List<Brand> getBrandsWithChildren() {
        return null;
    }

}


