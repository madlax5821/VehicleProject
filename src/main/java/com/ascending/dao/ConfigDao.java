package com.ascending.dao;

import com.ascending.model.Config;

import java.util.List;

public interface ConfigDao {
    Config save(Config config);
    boolean delete(Config config);
    boolean update(Config congig);
    List<Config> getConfigs();
    boolean deleteByName(String name);
    Config getConfigById(long id);
    Config getConfigByName(String string);
}
