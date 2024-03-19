package org.hw_sorter.hw_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.hw_sorter.*"})
@OpenAPIDefinition(servers = {
        @Server(url = "http://localhost:8087", description = "localhost")
})
public class HwApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HwApiApplication.class, args);
    }

}
