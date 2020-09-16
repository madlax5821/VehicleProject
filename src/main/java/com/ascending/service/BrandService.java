package com.ascending.service;

import com.ascending.dao.BrandDao;
import com.ascending.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BrandService {
    @Autowired()
    @Qualifier("BrandDaoImpl")
    private BrandDao brandDao;

    public Brand saveBrand(Brand brand){return brandDao.save(brand);}

    public boolean deleteBrand(Brand brand){return brandDao.delete(brand);}

    public boolean updateBrand(Brand brand){return brandDao.update(brand);}

    public List<Brand> getAllBrands(){
        return brandDao.getBrands();
    }

    public boolean deleteBrandByName(String name){return brandDao.deleteByName(name);}

    public Brand getBrandById(long id){return brandDao.getBrandById(id);}

    public Brand getBrandByName(String name){return brandDao.getBrandByName(name);}

}
