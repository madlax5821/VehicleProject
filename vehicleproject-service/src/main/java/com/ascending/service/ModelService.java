package com.ascending.service;

import com.ascending.dao.ModelDao;
import com.ascending.model.Brand;
import com.ascending.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ModelService {
    private Logger logger = LoggerFactory.getLogger(ModelService.class);

    @Autowired
    @Qualifier("ModelDaoImpl")
    private ModelDao modelDao;

    public Model saveModel(Model model,Brand brand){return modelDao.save(model,brand);}

    public boolean deleteModel(Model model){return modelDao.delete(model);}

    public boolean updateModel(Model model,Brand brand){return modelDao.update(model,brand);}

    public List<Model> getAllModels(){return modelDao.getModels();}

    public boolean deleteModelByName(String name){return modelDao.deleteByName(name);}

    public Model getModelById(long id){return modelDao.getModelById(id);}

    public Model getModelByName(String name){return modelDao.getModelByName(name);}
}
