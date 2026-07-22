package com.cafeteria.cafeteria;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class CafeteriaApplication {

	public static void main(String[] args) {
		migrarBancoDeDados();
		SpringApplication.run(CafeteriaApplication.class, args);
	}
	 private static void migrarBancoDeDados() {
        String url = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/cafeteria");
        String user = System.getenv().getOrDefault("DB_USERNAME", "postgres");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "postgres");

        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
    }
}
