package com.ascending.dao;

import com.ascending.model.Config;
import com.ascending.model.Model;

import java.util.List;

public interface ConfigDao {
    Config save(Config config, Model model);
    boolean delete(Config config);
    boolean update(Config congig, Model model);
    List<Config> getConfigs();
    boolean deleteByName(String name);
    Config getConfigById(long id);
    Config getConfigByName(String string);
}
