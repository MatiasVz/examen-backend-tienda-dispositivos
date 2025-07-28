package com.apiweb.pagina.Controladores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DispositivoControllerTestSimple {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testListarDispositivosEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity("/dispositivo", String.class);
        
        // El endpoint debe existir y devolver 200 (público)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCRUDEndpointsExist() {
        // Test POST endpoint (puede devolver 403 sin autenticación)
        ResponseEntity<String> postResponse = restTemplate.postForEntity("/dispositivo", "{}", String.class);
        assertNotEquals(HttpStatus.NOT_FOUND, postResponse.getStatusCode());

        // Test GET by ID endpoint 
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/dispositivo/1", String.class);
        assertTrue(getResponse.getStatusCode() == HttpStatus.OK || 
                  getResponse.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    void testValidacionEndpoints() {
        // Test con datos inválidos
        String invalidJson = "{\"nombre\":\"\",\"precio\":-100}";
        ResponseEntity<String> response = restTemplate.postForEntity("/dispositivo", invalidJson, String.class);
        
        // Debe devolver algún tipo de error (no 200)
        assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST || 
                  response.getStatusCode() == HttpStatus.FORBIDDEN ||
                  response.getStatusCode() == HttpStatus.UNAUTHORIZED);
    }
}
