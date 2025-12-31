package io.github.ngthduongg623.enterprise_manager.controller;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}