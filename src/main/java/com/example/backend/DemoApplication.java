package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	// This annotation is used to run the method at application startup
	// It is a custom annotation that you can define to execute commands
	// when the application starts. It is not a standard Spring annotation.
	// You can implement this annotation to execute the method automatically.
	// For example, you can use @PostConstruct or implement CommandLineRunner.
	public void run(String... args) throws Exception {
		String sql = """
        INSERT INTO blog (title, author, body)
		VALUES ('Awesome Java Project', 'Seiji Villafranca',
		'This is an awesome blog for java');
		""";
        int rows = jdbcTemplate.update(sql);
		System.out.println("Rows inserted: " + rows);
    }

}
