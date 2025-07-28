package com.apiweb.pagina.Controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiweb.pagina.DTOs.FooterDTO;
import com.apiweb.pagina.Entidades.Footer;
import com.apiweb.pagina.Servicio.FooterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/footers")
@SecurityRequirement(name="bearerAuth")
public class FooterController {
    @Autowired
    private FooterService footerService;

    @Operation(summary = "Crear un footer")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Footer creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Empresa no encontrada o datos inválidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody FooterDTO footerDTO) {
        try {
            Footer footer = footerService.guardarDesdeDTO(footerDTO);
            return ResponseEntity.ok(footer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar todos los footers")
    @ApiResponse(responseCode = "200", description = "Listado de footers")
    @GetMapping
    public List<Footer> listar() {
        return footerService.listar();
    }
    
    @Operation(summary = "Obtener redes sociales por footer")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Redes sociales encontradas"),
        @ApiResponse(responseCode = "404", description = "Footer no encontrado")
    })
    @GetMapping("/{footerId}/redes-sociales")
    public ResponseEntity<?> obtenerRedesSocialesPorFooter(@PathVariable Long footerId) {
        Optional<Footer> footerOpt = footerService.buscarPorId(footerId);
        if (footerOpt.isPresent()) {
            Footer footer = footerOpt.get();
            return ResponseEntity.ok(footer.getRedesSociales());
        } else {
            return ResponseEntity.status(404).body("Footer no encontrado");
        }
    }

    @Operation(summary = "Buscar footer por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Footer encontrado"),
        @ApiResponse(responseCode = "404", description = "Footer no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Footer> footer = footerService.buscarPorId(id);
        return footer.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body("Footer no encontrado"));
    }

    @Operation(summary = "Actualizar un footer")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Footer actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Empresa no encontrada o datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Footer no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody FooterDTO footerDTO) {
        try {
            Footer footer = footerService.actualizarDesdeDTO(id, footerDTO);
            return ResponseEntity.ok(footer);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un footer")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Footer eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Footer no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            footerService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
} 