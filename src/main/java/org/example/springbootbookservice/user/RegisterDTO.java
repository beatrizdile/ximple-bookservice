package org.example.springbootbookservice.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
