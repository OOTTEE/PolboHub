package com.polbohub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.polbohub")
public class PolboHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolboHubApplication.class, args);
    }

}
