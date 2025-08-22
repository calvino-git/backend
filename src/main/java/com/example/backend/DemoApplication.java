package com.example.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
@EnableCaching
// The @EnableCaching annotation is used to enable caching support in a Spring application.
// It allows you to use caching annotations like @Cacheable, @CachePut, and @
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
	public static int minOperations(int[] arr, int threshold, int d) {
        // Step 1: Gather all potential targets and their costs.
        // The map's key is the target value, and the value is a list of operation
        // counts required to reach that target from different array elements.
        Map<Integer, List<Integer>> targetCosts = new HashMap<>();

        for (int num : arr) {
            int ops = 0;
            int currentVal = num;
            
            // Generate the path of transformations for the current number.
            while (true) {
                // `computeIfAbsent` gets the list for currentVal, or creates a new
                // ArrayList if the key is not present, then adds the operation count.
                targetCosts.computeIfAbsent(currentVal, k -> new ArrayList<>()).add(ops);

                if (currentVal == 0) {
                    // Stop once we reach 0.
                    break;
                }
                currentVal /= d;
                ops++;
            }
        }

        // Step 2 & 3: Find the minimum operations among all valid targets.
        int minTotalOps = Integer.MAX_VALUE;

        for (List<Integer> costs : targetCosts.values()) {
            // Check if the target is reachable by at least `threshold` elements.
            if (costs.size() >= threshold) {
                // Sort the costs to easily find the cheapest ones.
                Collections.sort(costs);

                // Sum the costs for the `threshold` cheapest paths.
                int currentTotalOps = 0;
                for (int i = 0; i < threshold; i++) {
                    currentTotalOps += costs.get(i);
                }
                
                // An alternative using the Java 8 Stream API:
                // int currentTotalOps = costs.stream().limit(threshold).mapToInt(Integer::intValue).sum();

                // Update the overall minimum cost found so far.
                minTotalOps = Math.min(minTotalOps, currentTotalOps);
            }
        }

        return minTotalOps;
    }

}
