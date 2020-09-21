package com.ascending.service;

import com.ascending.dao.RoleDao;
import com.ascending.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role save(Role role){return roleDao.save(role);}

    public boolean delete(Role role){return roleDao.delete(role);}
}
