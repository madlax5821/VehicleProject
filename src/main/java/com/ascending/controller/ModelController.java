package com.ascending.controller;

import com.ascending.model.Brand;
import com.ascending.model.Model;
import com.ascending.service.BrandService;
import com.ascending.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Model")
public class ModelController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ModelService modelService;

    @Autowired
    private BrandService brandService;

    @PostMapping(value = "saveModel", produces = MediaType.APPLICATION_JSON_VALUE)
    public Model saveModel(@RequestBody Model model,Brand brand){
        brand = brandService.getBrandById(model.getBrandId());
        modelService.saveModel(model,brand);
        return model;
    }

    @DeleteMapping(value = "deleteModel", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteModel(@RequestBody Model model){
        return modelService.deleteModel(model);
    }

    @PutMapping(value = "updateModel", produces = MediaType.APPLICATION_JSON_VALUE)
    public Model updateModel(@RequestBody Model model, Brand brand){
        model.setModelName(model.getModelName());
        model.setVehicleType(model.getVehicleType());
        model.setBrandId(model.getBrandId());
        brand  =brandService.getBrandById(model.getBrandId());
        modelService.updateModel(model,brand);
        return model;
    }

    @GetMapping(value = "getModels", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Model> getModels(){
        return modelService.getAllModels();
    }

    @DeleteMapping (value = "deleteModelByName",produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteModelByName(@RequestParam("name")String name){
        return modelService.deleteModelByName(name);
    }

    @GetMapping(value = "getModelById", produces = MediaType.APPLICATION_JSON_VALUE)
    public Model getModelById(@RequestParam("id")long id){
        return modelService.getModelById(id);
    }

    @GetMapping(value = "getModelByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public Model getModelByName(@RequestParam("name")String name){
        return modelService.getModelByName(name);
    }
}
