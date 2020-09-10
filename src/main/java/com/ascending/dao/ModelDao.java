package com.ascending.dao;

import com.ascending.model.Model;

import java.util.List;

public interface ModelDao {
    Model save(Model model);
    boolean delete(Model model);
    boolean update(Model Model);
    List<Model> getModels();
    boolean deleteByName(String name);
    Model getModelById(long id);
    Model getModelByName(String name);
}
