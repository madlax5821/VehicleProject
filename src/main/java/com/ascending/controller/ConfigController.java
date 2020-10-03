package com.ascending.controller;

import com.ascending.dao.ConfigDao;
import com.ascending.dao.ModelDao;
import com.ascending.model.Config;
import com.ascending.model.Model;
import com.ascending.service.ConfigService;
import com.ascending.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Config")
public class ConfigController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConfigService configService;

    @Autowired
    private ModelService modelService;

    @PostMapping(value = "saveConfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public Config saveConfig(@RequestBody Config config, Model model){
        model = modelService.getModelById(config.getModelId());
        configService.saveConfig(config,model);
        return config;
    }

    @DeleteMapping(value = "deleteConfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteConfig(@RequestBody Config config){
        return configService.deleteConfig(config);
    }

    @PutMapping(value = "updateConfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateConfig(@RequestBody Config config, Model model){
        model = modelService.getModelById(config.getModelId());
        return configService.updateConfig(config,model);
    }

    @GetMapping(value = "getConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Config> getConfigs(){
        List<Config> configs = configService.getAllConfigs();
        return configs;
    }

    @DeleteMapping(value = "deleteConfigByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteConfigByName(@RequestParam("name")String name){
        return configService.deleteConfigByName(name);
    }

    @GetMapping(value = "getConfigById", produces = MediaType.APPLICATION_JSON_VALUE)
    public Config getConfigById(@RequestParam("id")long id){
        Config config = configService.getConfigById(id);
        return config;
    }

    @GetMapping(value = "getConfigByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public Config getConfigByName(@RequestParam("name")String name){
        Config config = configService.getConfigByName(name);
        return config;
    }

}
