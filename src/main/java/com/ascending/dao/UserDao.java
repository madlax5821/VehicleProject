package com.ascending.dao;

import com.ascending.model.Role;
import com.ascending.model.User;

import java.util.List;

public interface UserDao {
    User save(User user);
    boolean delete(User user);
    boolean update(User user);
    List<User> findAllUsers();
    boolean deleteByName(String name);
    User getUserByEmail(String email);
    User getUserById(Long Id);
    User getUserByCredentials(String email, String password) throws Exception;
    User addRole(User user, Role role);
    User getUserByName(String username);
    User getUserByNameAndEmail(String name, String email);
}
