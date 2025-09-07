package com.example.backend.jwt.services;

import com.example.backend.jwt.models.UserPrincipal;
import com.example.backend.user.UserEntity;
import com.example.backend.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(userService.findUserByEmail(username));
    }

    public UserEntity authenticate(String email, String password) throws NoSuchAlgorithmException {
        if (email.isEmpty() || password.isEmpty()) throw new BadCredentialsException("Unauthorized");
        UserEntity userEntity = userService.findUserByEmail(email);
        if (userEntity == null) throw new BadCredentialsException("Unauthorized");
        boolean verified = verifyPasswordHash(password, userEntity.getStoredSalt(), userEntity.getStoredHash());
        if (!verified) throw new BadCredentialsException("Unauthorized");
        return userEntity;
    }

    public Boolean verifyPasswordHash(String password, byte[] salt, byte[] hash) throws NoSuchAlgorithmException {
        //if (password.isBlank() /* in java 17*/ || password.isEmpty()) throw new IllegalArgumentException("Password cannot be empty or whitespace only string.");
        if (password == null || password.isEmpty()) throw new IllegalArgumentException("Password cannot be empty or whitespace only string.");
        if (hash.length != 64) throw new IllegalArgumentException("Invalid length of password hash (64 bytes expected)");
        if (salt.length != 128) throw new IllegalArgumentException("Invalid length of password salt (128 bytes expected).");

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        byte[] computedHash = md.digest(password.getBytes(StandardCharsets.UTF_8));

        for (int i = 0; i < computedHash.length; i++) {
            if (computedHash[i] != hash[i]) return false;
        }

        // The above for loop is the same as below
        return MessageDigest.isEqual(computedHash, hash);
    }

}
