package com.ascending.service;

import com.ascending.dao.ConfigDao;
import com.ascending.model.Config;
import com.ascending.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConfigService {
    private Logger logger = LoggerFactory.getLogger(ConfigService.class);

    @Autowired
    @Qualifier("ConfigDaoImpl")
    private ConfigDao configDao;

    public Config saveConfig(Config config, Model model){return configDao.save(config,model);}

    public boolean deleteConfig(Config config){return configDao.delete(config);}

    public boolean updateConfig(Config config, Model model){return configDao.update(config,model);}

    public List<Config> getAllConfigs(){return configDao.getConfigs();}

    public boolean deleteConfigByName(String name){return configDao.deleteByName(name);}

    public Config getConfigById(long id){return configDao.getConfigById(id);}

    public Config getConfigByName(String name){return configDao.getConfigByName(name);}
}
