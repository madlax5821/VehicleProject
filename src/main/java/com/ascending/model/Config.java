package com.ascending.model;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name="config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String configName;
    @Column(name = "key_feature")
    private String keyFeatures;
    @Column(name= "year")
    private Date year;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Config(String configName, String keyFeatures, Date year) {
        this.setConfigName(configName);
        this.setKeyFeatures(keyFeatures);
        this.setYear(year);
    }

    public Config(long id, String configName, String keyFeatures, Date year) {
        this.setId(id);
        this.setConfigName(configName);
        this.setKeyFeatures(keyFeatures);
        this.setYear(year);
    }


    public Config() {};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getKeyFeatures() {
        return keyFeatures;
    }

    public void setKeyFeatures(String keyFeatures) {
        this.keyFeatures = keyFeatures;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }


}
