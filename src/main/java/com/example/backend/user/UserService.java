package com.example.backend.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.backend.exception.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    /**
     * Creates a new user in the system.
     *
     * @param userDto the user data transfer object containing user details
     * @return the created user as a UserDto
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     * @throws IllegalStateException if the email already exists
     */

    public UserDto createUser(UserDto userDto) throws NoSuchAlgorithmException {
        if (userRepository.selectExistsEmail(userDto.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        var salt = createSalt();
        var passwordHash = createPasswordHash(userDto.getPassword(), salt);

        UserEntity userEntity = convertToEntity(userDto);
        userEntity.setStoredSalt(salt);
        userEntity.setStoredHash(passwordHash);

        userRepository.save(userEntity);
        return convertToDto(userEntity);
    }

    /**
     * Updates an existing user in the system.
     *
     * @param userDto the user data transfer object containing updated user details
     * @return the updated user as a UserDto
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     * @throws IllegalStateException if the email already exists
     */
    public UserDto updateUser(UserDto userDto) throws NoSuchAlgorithmException {
        if (userRepository.selectExistsEmail(userDto.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        UserEntity userEntity = convertToEntity(userDto);
        var existingUser = userRepository.findById(userEntity.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userDto.getPassword() != null) {
            var salt = createSalt();
            var passwordHash = createPasswordHash(userDto.getPassword(), salt);
            userEntity.setStoredSalt(salt);
            userEntity.setStoredHash(passwordHash);
        } else {
            userEntity.setStoredSalt(existingUser.getStoredSalt());
            userEntity.setStoredHash(existingUser.getStoredHash());
        }

        userRepository.save(userEntity);
        return convertToDto(userEntity);
    }
    /**
     * Removes a user from the system.
     *
     * @param id the UUID of the user to remove
     * @throws NotFoundException if the user is not found
     */
    public void removeUser(final UUID id) {
        findOrThrowIfUserNotFound(id);
        userRepository.deleteById(id);
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user to retrieve
     * @return the user as a UserDto
     * @throws IllegalStateException if the user is not found
     */
    public UserEntity getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new IllegalStateException("User not found");
        }
        return userEntity;
    }
    
    /**
     * Retrieves a user by their ID.
     *
     * @param id the UUID of the user to retrieve
     * @return the user as a UserDto
     * @throws NotFoundException if the user is not found
     */
    public UserDto findUserById(final UUID id) {
        var user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("User by id " + id +
                                " was not found"));
        return convertToDto(user);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a list of all users as UserDto objects
     */
    public Iterable<UserDto> findAllUsers() {
        var userEntityList = new ArrayList<>(userRepository.findAll());
        return userEntityList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertToDto(UserEntity entity) {
        return mapper.map(entity, UserDto.class);
    }

    private UserEntity convertToEntity(UserDto dto) {
        return mapper.map(dto, UserEntity.class);
    }

    public void findOrThrowIfUserNotFound(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User by id " + id + " was not found"));
    }

    /**
     * Updates an existing user in the system.
     *
     * @param userDto the user data transfer object containing updated user details
     * @return the updated user as a UserDto
     * @throws NoSuchAlgorithmException if the hashing algorithm is not found
     * @throws IllegalStateException if the email already exists
     */
    
    private byte[] createSalt() {
        var random = new SecureRandom();
        var salt = new byte[128];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] createPasswordHash(String password, byte[] salt) throws NoSuchAlgorithmException {
        var md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        return md.digest(
                password.getBytes(StandardCharsets.UTF_8));
    }

}
