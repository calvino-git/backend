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
