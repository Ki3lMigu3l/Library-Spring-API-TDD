package com.github.ki3lmigu3l.library.api.exception;

import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    private List<String> errors;

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult
                .getAllErrors()
                .forEach(error -> this.errors
                        .add(error.getDefaultMessage()));
    }

    public ApiErrors(BusinessException exception) {
        this.errors = Arrays.asList(exception.getMessage());
    }

    public ApiErrors(ResponseStatusException exception) {
        this.errors = Arrays.asList(exception.getReason());
    }

    public List<String> getErrors() {
        return errors;
    }
}
