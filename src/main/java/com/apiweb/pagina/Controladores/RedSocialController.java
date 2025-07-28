package com.apiweb.pagina.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.apiweb.pagina.Entidades.RedSocial;
import com.apiweb.pagina.Servicio.RedSocialService;
import com.apiweb.pagina.DTOs.RedSocialDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/redes-sociales")
@SecurityRequirement(name="bearerAuth")
public class RedSocialController {
    @Autowired
    private RedSocialService redSocialService;

    @Operation(summary = "Crear una red social")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Red social creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Footer no encontrado o datos inválidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RedSocialDTO redSocialDTO) {
        try {
            RedSocial red = redSocialService.guardarDesdeDTO(redSocialDTO);
            return ResponseEntity.ok(red);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar todas las redes sociales")
    @ApiResponse(responseCode = "200", description = "Listado de redes sociales")
    @GetMapping
    public List<RedSocial> listar() {
        return redSocialService.listar();
    }

    @Operation(summary = "Buscar red social por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Red social encontrada"),
        @ApiResponse(responseCode = "404", description = "Red social no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<RedSocial> red = redSocialService.buscarPorId(id);
        return red.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body("Red social no encontrada"));
    }

    @Operation(summary = "Actualizar una red social")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Red social actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Footer no encontrado o datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Red social no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody RedSocialDTO redSocialDTO) {
        try {
            RedSocial red = redSocialService.actualizarDesdeDTO(id, redSocialDTO);
            return ResponseEntity.ok(red);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrada")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar una red social")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Red social eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Red social no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            redSocialService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
} 