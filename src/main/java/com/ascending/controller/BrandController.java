package com.ascending.controller;

import com.ascending.model.Brand;
import com.ascending.model.Model;
import com.ascending.service.BrandService;
import com.ascending.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "Brand")
public class BrandController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BrandService brandService;

    @PostMapping(value="saveBrand",produces = MediaType.APPLICATION_JSON_VALUE)
    public Brand saveBrand(@RequestBody Brand brand){
        brand = brandService.saveBrand(brand);
        return brand;
    }

    @DeleteMapping(value="deleteBrand",produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteBrand(@RequestBody Brand brand){
        logger.info("delete brand successfully..");
        return brandService.deleteBrand(brand);
    }

    @PutMapping(value = "updateBrand", produces = MediaType.APPLICATION_JSON_VALUE)
    public Brand updateBrand(@RequestBody Brand brand){
        brand.setName(brand.getName());
        brand.setDescription(brand.getDescription());
        brand.setNationality(brand.getNationality());
        brandService.updateBrand(brand);
        return brand;
    }

    @GetMapping(value = "getBrands",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Brand> getAllBrands(){
        List<Brand> brands = brandService.getAllBrands();
        return brands;
    }

    @DeleteMapping(value="deleteBrandByName",produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteBrandByName(@RequestParam("name")String name){
        return brandService.deleteBrandByName(name);
    }

    @GetMapping(value = "getBrandById",produces = MediaType.APPLICATION_JSON_VALUE)
    public Brand getBrandById(@RequestParam("id")long id){
        Brand brand = brandService.getBrandById(id);
        return brand;
    }

    @GetMapping(value = "getBrandByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public Brand getBrandByname(@RequestParam("name")String name){
        Brand brand = brandService.getBrandByName(name);
        return brand;
    }




}
