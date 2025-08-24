package com.example.backend.user.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.backend.antiheroes.entity.AntiHero;
import com.example.backend.user.UserEntity;
import com.example.backend.user.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        //Populate the in-memory database with test data if necessary
        //Create and save userEntity1
        UserEntity user1 = new UserEntity("user1@example.com", "password123");
        userRepository.save(user1);
        //Create and save userEntity2
        UserEntity user2 = new UserEntity("user2@example.com", "password456");
        userRepository.save(user2);
    }

    @Test
    void shouldCheckIfEmailExists() {
        //Given an existing email in the database
        String existingEmail = "user1@example.com";
        
        //When checking if the email exists
        Boolean exists = userRepository.existsEmail(existingEmail);

        //Then the result should be true
        assertThat(exists).isTrue();
    }

    @Test
    void shouldCheckIfEmailDoesNotExist() {
        //Given a non-existing email in the database
        String nonExistingEmail = "user3@example.com";
        //When checking if the email exists
        Boolean exists = userRepository.existsEmail(nonExistingEmail);
        //Then the result should be false
        assertThat(exists).isFalse();
    }

    @Test
    void shouldFindUserByEmail() {
        //Given an existing email in the database
        String existingEmail = "user2@example.com";
        //When finding the user by email
        UserEntity existingUser = userRepository.findByEmail(existingEmail);
        //Then the user should not be null and should have the correct email
        assertThat(existingUser).isNotNull();
        assertThat(existingUser.getEmail()).isEqualTo(existingEmail);
    }

    @Test
    void shouldReturnNullWhenFindingNonExistingEmail() {
        //Given a non-existing email in the database
        String nonExistingEmail = "user4@example.com";
        //When finding the user by email
        UserEntity nonExistingUser = userRepository.findByEmail(nonExistingEmail);
        //Then the result should be null
        assertThat(nonExistingUser).isNull();
    }
}
