package com.ascending.dao;

import com.ascending.model.Brand;
import com.ascending.model.Model;

import java.util.List;

public interface ModelDao {
    Model save(Model model, Brand brand);
    boolean delete(Model model);
    boolean update(Model Model, Brand brand);
    List<Model> getModels();
    boolean deleteByName(String name);
    Model getModelById(long id);
    Model getModelByName(String name);
}
