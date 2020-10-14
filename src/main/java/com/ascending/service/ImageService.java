package com.ascending.service;

import com.ascending.dao.ImageDao;
import com.ascending.model.Image;
import com.ascending.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImageDao imageDao;

    public Image saveImage(Image image, User user){return imageDao.saveImage(image,user);}

    public boolean deleteImage(Image image){return imageDao.deleteImage(image);}

    public boolean updateImage(Image image, User user){return imageDao.updateImage(image,user);}

    public List<Image> getAllImages(){return imageDao.getImages();}

    public Image getByImageKey(String imageKey){return imageDao.getByImageKey(imageKey);}

    public boolean deleteByImageKey(String imageKey){return imageDao.deleteByImageKey(imageKey);}
}
