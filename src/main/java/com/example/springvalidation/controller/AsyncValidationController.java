package com.example.springvalidation.controller;

import com.example.springvalidation.model.SignupRequest;
import com.example.springvalidation.service.ValidationService;
import com.example.springvalidation.util.JWTUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AsyncValidationController {

    private final ValidationService validationService;

    private final JWTUtility utility;

    @PostMapping("/validate-async")
    public CompletableFuture<ResponseEntity<?>> validateUserAsync(@RequestBody SignupRequest signUpRequest) {
        return CompletableFuture.supplyAsync(() -> validationService.validateRequest(signUpRequest))
                .thenApply(fieldErrors -> {
                    if (fieldErrors.isEmpty()) {
                        return ResponseEntity.ok(utility.generateToken(signUpRequest.getUsername()));
                    } else {
                        return ResponseEntity.badRequest().body(fieldErrors);
                    }
                });
    }

    @GetMapping("/validate-token/{token}")
    public ResponseEntity<String> validateToken(@PathVariable String token) {
        try {
            if(utility.validateToken(token, utility.getUsernameFromToken(token))) {
                return ResponseEntity.ok("verification pass");
            }
        }catch (Exception ex) {
            log.info("validation failed ------ > {}", ex.getMessage());
        }

        return ResponseEntity.badRequest().body("verification fails");
    }
}

