package com.javatutoriales.gaming.users.infrastructure.adapters.input.api.handlers;

public record ErrorResponse(int status, String error, String message, String timestamp) {
}