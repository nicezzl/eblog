package com.zzl.eblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
public class EblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(EblogApplication.class, args);
        System.out.println("http://localhost:8080");
    }

}
