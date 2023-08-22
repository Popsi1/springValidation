package com.example.springvalidation.model;

import com.example.springvalidation.validation.BirthDate;
import com.example.springvalidation.validation.StrongPassword;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class SignupRequest {
  @NotEmpty(message = "The user name is required.")
  @Size(min = 4)
  private String username;

  @NotEmpty(message = "The email address is required.")
  @Email
  private String email;

  @NotEmpty(message = "The password is required.")
  @StrongPassword
  private String password;

  @NotNull(message = "The date of birth is required.")
  @BirthDate
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dateOfBirth;


}
