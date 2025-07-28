package com.apiweb.pagina.Controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiweb.pagina.DTOs.DispositivoDTO;
import com.apiweb.pagina.Entidades.Dispositivo;
import com.apiweb.pagina.Servicio.DispositivoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dispositivo")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name="bearerAuth")
public class DispositivoController {

    private final DispositivoService dispositivoService;

    @Operation(summary = "Listar todos los dispositivos")
    @ApiResponse(responseCode = "200", description = "Listado de dispositivos")
    @GetMapping
    public List<Dispositivo> getAllDispositivos() {
        return dispositivoService.listAll();
    }

    @Operation(summary = "Crear un dispositivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dispositivo creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PostMapping
    public ResponseEntity<?> saveDispositivo(@RequestBody DispositivoDTO dispositivoDTO) {
        try {
            Dispositivo saved = dispositivoService.saveFromDTO(dispositivoDTO);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar un dispositivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dispositivo actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Dispositivo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDispositivo(@PathVariable Long id, @RequestBody DispositivoDTO dispositivoDTO) {
        try {
            Optional<Dispositivo> updated = dispositivoService.updateFromDTO(id, dispositivoDTO);
            return updated.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body("Dispositivo no encontrado"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar dispositivo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dispositivo encontrado"),
        @ApiResponse(responseCode = "404", description = "Dispositivo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getDispositivoById(@PathVariable Long id) {
        Optional<Dispositivo> dispositivo = dispositivoService.findById(id);
        return dispositivo.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body("Dispositivo no encontrado"));
    }

    @Operation(summary = "Eliminar un dispositivo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Dispositivo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Dispositivo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDispositivo(@PathVariable Long id) {
        try {
            boolean deleted = dispositivoService.deleteById(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(404).body("Dispositivo no encontrado");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 