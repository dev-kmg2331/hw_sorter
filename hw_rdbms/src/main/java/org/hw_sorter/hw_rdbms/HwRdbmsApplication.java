package org.hw_sorter.hw_rdbms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HwRdbmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HwRdbmsApplication.class, args);
    }

}
