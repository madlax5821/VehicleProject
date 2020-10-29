package com.ascending.repository;

import com.ascending.dao.ImageDao;
import com.ascending.model.Image;
import com.ascending.model.User;

import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ImageDaoImpl implements ImageDao {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Image saveImage(Image image, User user) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            image.setUser(user);
            session.save(image);
            transaction.commit();
            session.close();
        }catch (Exception e){
            if (transaction!=null)
                logger.error("failed to insert record, error={}",e.getMessage());
                transaction.rollback();
                session.close();
        }
        return image;
    }

    @Override
    public boolean deleteImage(Image image) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        int ifDelete = 0;
        try{
            transaction = session.beginTransaction();
            session.delete(image);
            transaction.commit();
            session.close();
            ifDelete = 1;
        }catch (Exception e){
            if (transaction!=null)
                logger.error("failed to delete object, error={}",e.getMessage());
                transaction.rollback();
                session.close();
        }
        return ifDelete>0;
    }

    @Override
    public boolean updateImage(Image image, User user) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        int ifUpdate = 0;
        try {
            transaction = session.beginTransaction();
            image.setUser(user);
            session.update(image);
            transaction.commit();
            session.close();
            ifUpdate = 1;
        }catch (Exception e){
            if (transaction!=null)
                logger.error("failed to update object, error={}",e.getMessage());
                transaction.rollback();
                session.close();
        }
        return ifUpdate>0;
    }

    @Override
    public List<Image> getImages() {
        try (Session session=HibernateUtil.getSession()){
            String getAll = "from Image";
            Query<Image> query = session.createQuery(getAll);
            List<Image> images = query.list();
            return images;
        }
    }

//    @Override session delete method
//    public boolean deleteByUrl(String url) {
//        Session session = HibernateUtil.getSession();
//        Transaction transaction = null;
//        int ifDelete = 0;
//        try {
//            String deleteByName = "from Image as i where i.imageUrl=:image_url";
//            transaction = session.beginTransaction();
//            Query<Image> query = session.createQuery(deleteByName);
//            query.setParameter("image_url",url);
//            for (Image image:query.list()){
//                session.delete(image);
//            }
//            transaction.commit();
//            session.close();
//            ifDelete = 1;
//        }catch (Exception e){
//            if (transaction!=null)
//                logger.error("failed to delete object by name, error={}",e.getMessage());
//                transaction.rollback();
//                session.close();
//        }
//        return ifDelete>0;
//    }

    @Override //query delete method
    public boolean deleteByImageKey(String imageKey){
        try(Session session = HibernateUtil.getSession()){
            String deleteByImageKey = "delete from Image as i where i.imageKey=:image_key";
            Query<Image> query = session.createQuery(deleteByImageKey);
            query.setParameter("image_key",imageKey);
            int ifDelete = query.executeUpdate();
            return ifDelete>0;
        }
    }



    @Override
    public Image getByImageKey(String imageKey) {
        try (Session session = HibernateUtil.getSession()){
            String getByImageKey = "from Image as i where i.imageKey=:image_key";
            Query<Image> query = session.createQuery(getByImageKey);
            return query.uniqueResult();

        }
    }
}
