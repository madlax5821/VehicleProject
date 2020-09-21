package com.ascending.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "brand",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //@OneToMany(mappedBy = "brand",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Model> models;

    public void addModel(Model model){
        this.getModels().add(model);
        model.setBrand(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Model> getModels() {
        if (models==null){
            models = new HashSet<Model>();
        }
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
