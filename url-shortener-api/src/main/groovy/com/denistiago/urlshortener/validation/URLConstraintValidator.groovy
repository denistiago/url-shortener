package com.denistiago.urlshortener.validation

import org.apache.commons.validator.routines.UrlValidator

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class URLConstraintValidator implements ConstraintValidator<URL, String> {

    public static final String DEFAULT_SCHEMA = 'http://'

    private UrlValidator urlValidator = new UrlValidator()

    @Override
    void initialize(URL constraintAnnotation) {}

    @Override
    boolean isValid(String value, ConstraintValidatorContext context) {
        return urlValidator.isValid(value) || urlValidator.isValid(DEFAULT_SCHEMA + value)
    }

}