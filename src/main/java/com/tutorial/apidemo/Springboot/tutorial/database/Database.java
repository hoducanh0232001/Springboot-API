package com.tutorial.apidemo.Springboot.tutorial.database;

import com.tutorial.apidemo.Springboot.tutorial.models.Product;
import com.tutorial.apidemo.Springboot.tutorial.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Now connect with mysql using JPA
/*
docker run -d --rm --name mysql-spring-boot-tutorial \
-e MYSQL_ROOT_PASSWORD=123456 \
-e MYSQL_USER=hoducanh \
-e MYSQL_PASSWORD=123456 \
-e MYSQL_DATABASE=test_db \
-p 3309:3306 \
--volume mysql-spring-boot-tutorial-volume:/var/lib/mysql \
mysql:8.0-oracle

mysql -h localhost -P 3309 --protocol=tcp -u hoducanh -p

* */
@Configuration
public class Database {
    //logger
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository){

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
       //         Product productA = new Product("Macbook Air M1", 2020,1900.0,"");
         //       Product productB = new Product("Macbook Pro M1",2020,2100.0,"");
           //     logger.info("insert data: "+productRepository.save(productA));
                //  logger.info("insert data: "+productRepository.save(productB));
            }
        };
    }
}
