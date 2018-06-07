package com.prioritization.test.application;

import com.prioritization.test.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan(basePackages = {"com.prioritization.test.controller", "com.prioritization.test.service", "com.prioritization.test.utils"})
public class Application  implements CommandLineRunner {

    @Resource
    StorageService storageService;

    @Override
    public void run(String... arg) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }
    final static Logger LOGGER = Logger.getLogger(Application.class.getName());
    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(Application.class, args);
    }
}
