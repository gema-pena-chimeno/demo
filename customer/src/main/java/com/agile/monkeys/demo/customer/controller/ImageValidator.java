package com.agile.monkeys.demo.customer.controller;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.util.List;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ImageValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {

    private static final List<String> SUPPORTED_TYPES = List.of("image/jpg", "image/jpeg", "image/png");

    public void initialize(ValidImageFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty() || file.getSize()==0) {
            return true;
        }
        if (!SUPPORTED_TYPES.contains(file.getContentType().toLowerCase())) {
            return false;
        }
        return true;
    }
}
