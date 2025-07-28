package com.apiweb.pagina.Controladores;

import com.apiweb.pagina.Entidades.Empleado;
import com.apiweb.pagina.Servicio.EmpleadoService;
import com.apiweb.pagina.DTOs.EmpleadoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empleados")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name="bearerAuth")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Operation(summary = "Listar todos los empleados")
    @ApiResponse(responseCode = "200", description = "Listado de empleados")
    @GetMapping
    public List<Empleado> getAllEmpleados() {
        return empleadoService.findAll();
    }

    @Operation(summary = "Crear un empleado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empleado creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PostMapping
    public ResponseEntity<?> createEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        try {
            Empleado saved = empleadoService.saveFromDTO(empleadoDTO);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar un empleado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empleado actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmpleado(@PathVariable Long id, @RequestBody EmpleadoDTO empleadoDTO) {
        try {
            Empleado updated = empleadoService.updateFromDTO(id, empleadoDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar empleado por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmpleadoById(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoService.findById(id);
        return empleado.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body("Empleado no encontrado"));
    }

    @Operation(summary = "Eliminar un empleado")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Empleado eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmpleado(@PathVariable Long id) {
        try {
            boolean deleted = empleadoService.deleteById(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(404).body("Empleado no encontrado");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 