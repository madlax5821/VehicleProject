package com.ascending.dao;

import com.ascending.model.Brand;

import java.util.List;

public interface BrandDao {
    Brand save(Brand brand);
    boolean delete(Brand brand);
    boolean update(Brand brand);
    List<Brand> getBrands();
    boolean deleteByName(String name);
    Brand getBrandById(Long id);
    Brand getBrandByName(String name);
    List<Brand> getBrandsWithChildren();


}
