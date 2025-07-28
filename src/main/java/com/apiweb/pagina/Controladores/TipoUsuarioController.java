package com.apiweb.pagina.Controladores;

import com.apiweb.pagina.Entidades.TipoUsuario;
import com.apiweb.pagina.Servicio.TipoUsuarioService;
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
@RequestMapping("/tipos-usuario")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerAuth")
public class TipoUsuarioController {
    private final TipoUsuarioService tipoUsuarioService;

    @Operation(summary = "Listar todos los tipos de usuario")
    @ApiResponse(responseCode = "200", description = "Listado de tipos de usuario")
    @GetMapping
    public List<TipoUsuario> listar() {
        return tipoUsuarioService.listar();
    }

    @Operation(summary = "Buscar tipo de usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<TipoUsuario> tipo = tipoUsuarioService.buscarPorId(id);
        return tipo.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body("Tipo de usuario no encontrado"));
    }

    @Operation(summary = "Crear un tipo de usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de usuario creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody TipoUsuario tipoUsuario) {
        try {
            TipoUsuario tipo = tipoUsuarioService.guardar(tipoUsuario);
            return ResponseEntity.ok(tipo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar un tipo de usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de usuario actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario) {
        try {
            TipoUsuario actualizado = tipoUsuarioService.actualizar(id, tipoUsuario);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un tipo de usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Tipo de usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            tipoUsuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
} 