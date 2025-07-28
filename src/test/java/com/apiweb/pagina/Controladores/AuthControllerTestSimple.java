package com.apiweb.pagina.Controladores;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apiweb.pagina.DTOs.LoginRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTestSimple {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testLoginEndpointExists() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin123");

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/login", 
            loginRequest, 
            String.class
        );

        // El endpoint debe existir (no 404)
        assertNotEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getStatusCode() == HttpStatus.OK || 
                  response.getStatusCode() == HttpStatus.UNAUTHORIZED || 
                  response.getStatusCode() == HttpStatus.FORBIDDEN);
    }

    @Test
    void testLoginValidation() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("");

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/login", 
            loginRequest, 
            String.class
        );

        // Debe devolver algún tipo de error (no 200)
        assertNotEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLoginIncorrectCredentials() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("wronguser");
        loginRequest.setPassword("wrongpass");

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/auth/login", 
            loginRequest, 
            String.class
        );

        // Debe devolver algún tipo de error de autenticación
        assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED || 
                  response.getStatusCode() == HttpStatus.FORBIDDEN ||
                  response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }
}
