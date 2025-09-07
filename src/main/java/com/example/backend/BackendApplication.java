package com.example.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.backend.antiheroes.entity.AntiHero;
import com.example.backend.antiheroes.repository.AntiHeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableCaching
// The @EnableCaching annotation is used to enable caching support in a Spring application.
// It allows you to use caching annotations like @Cacheable, @CachePut, and @
public class BackendApplication implements CommandLineRunner {
	@Autowired
	private AntiHeroRepository antiHeroRepository;
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	// This annotation is used to run the method at application startup
	// It is a custom annotation that you can define to execute commands
	// when the application starts. It is not a standard Spring annotation.
	// You can implement this annotation to execute the method automatically.
	// For example, you can use @PostConstruct or implement CommandLineRunner.
	public void run(String... args) throws Exception {
        /*AntiHero antiHero1 = new AntiHero();
        antiHero1.setFirstName("Deadpool");
        antiHero1.setHouse("Regeneration");
        antiHero1.setKnowAs("Marvel");
        antiHero1.setLastName("Wilson");
        antiHeroRepository.save(antiHero1);

        //Create and save antiHero2
        AntiHero antiHero2 = new AntiHero();
        antiHero2.setFirstName("Wolverine");
        antiHero2.setHouse("Regeneration");
        antiHero2.setKnowAs("Marvel");
        antiHero2.setLastName("Logan");
        antiHeroRepository.save(antiHero2);

        //Create and save antiHero3
        AntiHero antiHero3 = new AntiHero();
        antiHero3.setFirstName("Punisher");
        antiHero3.setHouse("Military");
        antiHero3.setKnowAs("Marvel");
        antiHero3.setLastName("Castle");
        antiHeroRepository.save(antiHero3);

        //Create and save antiHero4
        AntiHero antiHero4 = new AntiHero();
        antiHero4.setFirstName("Catwoman");
        antiHero4.setHouse("Stealth");
        antiHero4.setKnowAs("DC");
        antiHero4.setLastName("Kyle");
        antiHeroRepository.save(antiHero4);

		System.out.println("Rows inserted: " + antiHeroRepository.count());*/
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
