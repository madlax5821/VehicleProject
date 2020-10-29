package com.ascending.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.ascending"})
@ServletComponentScan("com.ascending.filter")
public class AppInitializer extends SpringBootServletInitializer {
    private Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    public static void main(String[] args) {
        SpringApplication.run(AppInitializer.class,args);
    }
}

//@SpringBootApplication(scanBasePackages = {"com.ascending"})
//@ServletComponentScan("com.ascending.filter")
//public class AppInitializer  {
//    private Logger logger = LoggerFactory.getLogger(AppInitializer.class);
//
//    public static void main(String[] args) {
//        SpringApplication.run(AppInitializer.class,args);
//    }
//}