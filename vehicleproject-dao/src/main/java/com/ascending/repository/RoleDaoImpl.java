package com.ascending.repository;

import com.ascending.dao.RoleDao;
import com.ascending.model.Role;
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

@Repository
public class RoleDaoImpl implements RoleDao {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role save(Role role) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(role);
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null)
                transaction.rollback();
            logger.error("fail to insert record, error={}",e.getMessage());
            session.close();
        }
        return role;
    }

    @Override
    public boolean delete(Role role) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int deleteCount = 0;
        try{
            transaction = session.beginTransaction();
            session.delete(role);
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
    public boolean update(Role role) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int ifUpdate = 0;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(role);
            transaction.commit();
            session.close();
            ifUpdate = 1;
        }catch (Exception e){
            if (transaction!=null)
                transaction.rollback();
            logger.debug("Failed to update role, error:"+e.getMessage());
            session.close();
        }
        return ifUpdate>0;
    }

    @Override
    public List<Role> findAllRoles() {
        String hql_getAll = "from Role as r join fetch r.users";
        //String hql_getAll = "from Role as r";
        try(Session session = HibernateUtil.getSession()){
            Query<Role> query = session.createQuery(hql_getAll);
            return query.list();
        }
    }

    @Override
    public boolean deleteRoleByName(String name) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int ifDelete = 0;
        String hql_getByName = "from Role as r where r.name=:name";
        try {
            transaction = session.beginTransaction();
            Query<Role> query = session.createQuery(hql_getByName);
            query.setParameter("name",name);
            for (Role role:query.list()){
                session.delete(role);
            }
            transaction.commit();
            session.close();
            ifDelete = 1;
        }catch (Exception e){
            if (transaction!=null)
                transaction.rollback();
            logger.debug("Failed to delete role by name, error:"+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public Role getRoleByName(String name) {
        String hql_getByName = "from Role as r join fetch r.users where r.name = :name";
        try(Session session = HibernateUtil.getSession()){
            Query<Role> query = session.createQuery(hql_getByName);
            query.setParameter("name",name);
            List<Role> roles = query.list();
            Random r = new Random();
            return roles.get(r.nextInt(roles.size()));
        }
    }

}
