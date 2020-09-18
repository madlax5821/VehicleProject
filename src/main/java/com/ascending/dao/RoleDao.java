package com.ascending.dao;

import com.ascending.model.Role;

import java.util.List;

public interface RoleDao {
    Role save(Role role);
    int delete(Role role);
    boolean update(Role role);
    List<Role> findAllRoles();
    boolean deleteRoleByName(String name);
    Role getRoleByName(String name);

}
