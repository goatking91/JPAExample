package io.lfin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JpaExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaExampleApplication.class, args);
    }

}
