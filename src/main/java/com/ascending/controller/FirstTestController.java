package com.ascending.controller;

import com.ascending.model.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping(value = "world")
public class FirstTestController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "Hello",produces = MediaType.APPLICATION_JSON_VALUE)
    //@RequestMapping(value = "Hello",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(){
        logger.info("===From hello()===");
        return "Hello";
    }

    @PostMapping(value = "Hello",produces = MediaType.APPLICATION_JSON_VALUE)
    //@RequestMapping(value = "Hello",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello1(){
        logger.info("===From hello()===");
        return "Hello";
    }

    @GetMapping(value="hello/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public String greetingWithHello(@PathVariable("name")String name123){
        logger.info("=== From greetingWithHello(), the input value of PathVariable");
        return "Hello"+name123;
    }

    @GetMapping(value="hi",produces = MediaType.APPLICATION_JSON_VALUE)
    public String greetingWithHiUsingSingleRequestParam(@RequestParam("name123")String name){
        return "hi "+name;
    }

    @GetMapping(value = "hi",params = {"name"},produces = MediaType.APPLICATION_JSON_VALUE)
    public String greetingWithHiUsingSingleRequestParam1(@RequestParam("name")String name){
        return "hi "+name;
    }

    @GetMapping(value = "hia",params = {"name","location"},produces = MediaType.APPLICATION_JSON_VALUE)
    public String greetingWithHiUsingSingleRequestParam2(@RequestParam("name")String name, @RequestParam("location")String location){
        return "hi "+name+" "+location;
    }

    @PostMapping(value = "hi/brands",produces = MediaType.APPLICATION_JSON_VALUE)
    public Brand createBrand(@RequestBody Brand brand){
        brand.setName("Honda");
        return brand;
    }

    @PutMapping(value = "hi/brands",produces = MediaType.APPLICATION_JSON_VALUE)
    public Brand putBrand(@RequestBody Brand brand){
        brand.setName(brand.getName()+"testUpdate");
        return brand;
    }
    @DeleteMapping(value = "delete/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteTest(@PathVariable("name") String name){
        return "delete succussfully"+name;
    }

    @GetMapping("headerTest")
    public String greetings(@RequestHeader("Xiaofei")String language){
        return "The item 'Xiaofei' in headers are "+language;
    }

}
