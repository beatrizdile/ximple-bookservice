package org.example.springbootbookservice.model;

public record RegisterDTO(String login, String password, UserRole role) {
}
