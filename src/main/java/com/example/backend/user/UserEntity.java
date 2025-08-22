package com.example.backend.user;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
// @RedisHash("User")
// The @RedisHash annotation is used to indicate that this entity should be stored in Redis.
// It is not a standard JPA annotation, but rather a Spring Data Redis annotation.
// It allows the entity to be serialized and stored in Redis, which is a key-value store
// commonly used for caching and fast data retrieval in Spring applications.
// This annotation is useful when you want to use Redis as a data store for your entities,
// allowing you to take advantage of Redis's performance and scalability features.
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
  @Column(nullable = false, updatable = false)
  private UUID id;
  @Column(unique = true)
  private String email;
  private String mobileNumber;
  private byte[] storedHash;
  private byte[] storedSalt;

  public UserEntity(String email, String mobileNumber) {
    this.email = email;
    this.mobileNumber = mobileNumber;
  }
}
