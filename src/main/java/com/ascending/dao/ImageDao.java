package com.ascending.dao;

import com.ascending.model.Image;
import com.ascending.model.User;

import java.util.List;

public interface ImageDao {
    Image saveImage(Image image, User user);
    boolean deleteImage(Image image);
    boolean updateImage(Image image, User user);
    List<Image> getImages();
    boolean deleteByImageKey(String imageKey);
    Image getByImageKey(String imageKey);
}
