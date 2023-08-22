package com.example.springvalidation.controller;

import com.example.springvalidation.model.SignupRequest;
import jakarta.validation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController {

  @PostMapping("/validate")
  public ResponseEntity<Boolean> validateUser(@Valid @RequestBody SignupRequest signUpRequest) {
    return new ResponseEntity<>(true, HttpStatus.OK);
  }
}
