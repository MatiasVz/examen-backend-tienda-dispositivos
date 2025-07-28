package com.apiweb.pagina.Controladores;

import com.apiweb.pagina.Entidades.Empresa;
import com.apiweb.pagina.Entidades.Empleado;
import com.apiweb.pagina.Servicio.EmpresaService;
import com.apiweb.pagina.DTOs.EmpresaDTO;
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
@RequestMapping("/empresa")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name="bearerAuth")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Operation(summary = "Obtener la primera empresa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @GetMapping
    public ResponseEntity<?> getEmpresa() {
        Optional<Empresa> empresa = empresaService.findFirst();
        return empresa.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body("Empresa no encontrada"));
    }

    @Operation(summary = "Crear una empresa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PostMapping
    public ResponseEntity<?> saveEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        try {
            Empresa saved = empresaService.saveFromDTO(empresaDTO);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar una empresa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empresa actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmpresa(@PathVariable Long id, @RequestBody EmpresaDTO empresaDTO) {
        try {
            Optional<Empresa> empresaExistente = empresaService.findById(id);
            if (empresaExistente.isPresent()) {
                Empresa updated = empresaService.updateFromDTO(id, empresaDTO);
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.status(404).body("Empresa no encontrada");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar una empresa")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Empresa eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmpresa(@PathVariable Long id) {
        try {
            boolean deleted = empresaService.deleteById(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(404).body("Empresa no encontrada");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoints para gestión de empleados
    @GetMapping("/{id}/empleados")
    public ResponseEntity<List<Empleado>> getEmpleadosByEmpresa(@PathVariable Long id) {
        List<Empleado> empleados = empresaService.getEmpleadosByEmpresa(id);
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/{id}/empleados/activos")
    public ResponseEntity<List<Empleado>> getEmpleadosActivosByEmpresa(@PathVariable Long id) {
        List<Empleado> empleados = empresaService.getEmpleadosActivosByEmpresa(id);
        return ResponseEntity.ok(empleados);
    }

    @PostMapping("/{id}/empleados")
    public ResponseEntity<Empleado> addEmpleadoToEmpresa(@PathVariable Long id, @RequestBody Empleado empleado) {
        try {
            Empleado saved = empresaService.addEmpleadoToEmpresa(id, empleado);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{empresaId}/empleados/{empleadoId}")
    public ResponseEntity<Void> removeEmpleadoFromEmpresa(@PathVariable Long empresaId, @PathVariable Long empleadoId) {
        try {
            boolean removed = empresaService.removeEmpleadoFromEmpresa(empresaId, empleadoId);
            if (removed) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
