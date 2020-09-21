package com.ascending.repository;

import com.ascending.dao.RoleDao;
import com.ascending.dao.UserDao;
import com.ascending.model.Role;
import com.ascending.model.User;
import com.ascending.util.HibernateUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    @Override
    public User save(User user) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        try{
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null)
                transaction.rollback();
            logger.error("fail to insert record, error={}",e.getMessage());
            session.close();
        }
        return user;
    }

    @Override
    public boolean delete(User u) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            session.delete(u);
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null)
                transaction.rollback();
            logger.error("fail to delete record, error={}",e.getMessage());
            session.close();
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        int ifUpdate = 0;
        try {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
            ifUpdate=1;
        }catch (Exception e){
            if (transaction!=null);
            transaction.rollback();
            logger.debug("Failed to update user, error:"+e.getMessage());
            session.close();
        }

        return ifUpdate>0;
    }

    @Override
    public List<User> findAllUsers() {
        String hql_getAll = "from User as u join fetch u.roles";
        //String hql_getAll = "from User";
        try(Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql_getAll);
            return query.list();
        }
    }

    @Override
    public boolean deleteByName(String name) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        String hql_deleteByName = "from User as u where u.name=:name";
        int ifDelete = 0;
        try {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(hql_deleteByName);
            query.setParameter("name",name);
            for (User user:query.list()){
                session.delete(user);
            }
            transaction.commit();
            session.close();
            ifDelete=1;
        }catch (Exception e){
            if (transaction!=null)
                transaction.rollback();
            logger.debug("Failed to delete user object by name, error:"+e.getMessage());
            session.close();
        }
        return ifDelete>0;
    }

    @Override
    public User getUserByEmail(String email) {
        String hql_getByEmail = "from User as u join fetch u.roles where lower(u.email)=:email";
        try(Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql_getByEmail);
            query.setParameter("email",email);
            return query.uniqueResult();
        }
    }

    @Override
    public User getUserById(Long Id) {
        String hql_getById = "from User as u join fetch u.roles where u.id=:id";
        try(Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql_getById);
            query.setParameter("id",Id);
            return query.uniqueResult();
        }
    }

    @Override
    public User getUserByCredentials(String email, String password) throws Exception {
        String hql = "from User as u join fetch u.roles where lower(u.email) = :email and u.password = :password";
//        logger.debug(String.format("User emial",email,password));
        try(Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("email",email.toLowerCase().trim());
            query.setParameter("password",password);
            return query.uniqueResult();
        }
        catch(Exception e){
            logger.debug(e.getMessage());
            throw new Exception("can`t find user record or session");
        }
    }

    @Override
    public User addRole(User user, Role role) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSession();
        try {
            transaction = session.beginTransaction();
            user.addRole(role);
            session.saveOrUpdate(user);
            transaction.commit();
            session.close();
        }catch(Exception e){
            if (transaction!=null)
                transaction.rollback();
            logger.debug("failed to add role to user object, error:"+e.getMessage());
            session.close();
        }
        return user;
    }

    @Override
    public User getUserByName(String username) {
        String hql_getByName = "from User as u join fetch u.roles where u.name = :name";
        try(Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql_getByName);
            query.setParameter("name",username);
            return query.uniqueResult();
        }
    }

    @Override
    public User getUserByNameAndEmail(String name, String email) {
        String hql_getNameAndEmail = "From User as u join fetch u.roles as r where u.name=:name and lower(u.email)=:email";
        try(Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql_getNameAndEmail);
            query.setParameter("name",name);
            query.setParameter("email",email.toLowerCase().trim());
            return query.uniqueResult();
        }
    }

}
