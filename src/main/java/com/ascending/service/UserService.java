package com.ascending.service;

import com.ascending.dao.UserDao;
import com.ascending.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByNameAndEmail(String name, String email){return userDao.getUserByNameAndEmail(name,email);}

    public User save(User user){return userDao.save(user);}

    public User getUserByName(String name){return userDao.getUserByName(name);}

    public boolean delete(User user){return userDao.delete(user);}

}
