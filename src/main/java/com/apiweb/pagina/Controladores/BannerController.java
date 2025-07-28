package com.apiweb.pagina.Controladores;

import com.apiweb.pagina.DTOs.BannerDTO;
import com.apiweb.pagina.Entidades.Banner;
import com.apiweb.pagina.Servicio.BannerService;
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
@RequestMapping("/banners")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name="bearerAuth")
public class BannerController {

    private final BannerService bannerService;

    @Operation(summary = "Listar todos los banners")
    @ApiResponse(responseCode = "200", description = "Listado de banners")
    @GetMapping
    public List<Banner> listar() {
        return bannerService.listar();
    }

    @Operation(summary = "Buscar banner por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Banner encontrado"),
        @ApiResponse(responseCode = "404", description = "Banner no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Banner> banner = bannerService.buscarPorId(id);
        return banner.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body("Banner no encontrado"));
    }

    @Operation(summary = "Crear un banner")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Banner creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Empresa no encontrada o datos inválidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody BannerDTO bannerDTO) {
        try {
            Banner banner = bannerService.guardarDesdeDTO(bannerDTO);
            return ResponseEntity.ok(banner);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar un banner")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Banner actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Empresa no encontrada o datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Banner no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody BannerDTO bannerDTO) {
        try {
            Banner banner = bannerService.actualizarDesdeDTO(id, bannerDTO);
            return ResponseEntity.ok(banner);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("no encontrada")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un banner")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Banner eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Banner no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            bannerService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
} 