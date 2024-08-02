package org.example.contractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.contractor.repository")
@EntityScan(basePackages = "org.example.contractor.entity")
public class ContractorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractorApplication.class, args);
    }

}
