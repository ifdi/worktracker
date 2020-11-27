package com.worktracker.service;

public interface AuthorizationService {

    void validateTokenAuthorization(String token);

    void validateToken(String token);
}
