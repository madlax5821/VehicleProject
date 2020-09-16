package com.ascending.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


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

    //@OneToMany(mappedBy = "config",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "config", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orders;

    @Transient
    @Column(name="model_id")
    private long modelId;

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

    public Set<Order> getOrders() {
        if(orders==null){
            orders=new HashSet<Order>();
        }
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", configName='" + configName + '\'' +
                ", keyFeatures='" + keyFeatures + '\'' +
                ", year=" + year +
                '}';
    }
}
