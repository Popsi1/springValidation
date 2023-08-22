package com.example.springvalidation.service;

import com.example.springvalidation.model.SignupRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ValidationService {

    public Map<String, String> validateRequest(SignupRequest signUpRequest) {
        Map<String, String> fieldErrors = new HashMap<>();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.usingContext().getValidator();

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signUpRequest);

        for (ConstraintViolation<SignupRequest> violation : violations) {
            String fieldName = String.valueOf(violation.getPropertyPath());
            String errorMessage = violation.getMessage();
            fieldErrors.put(fieldName, errorMessage);
        }
        return fieldErrors;
    }
}
