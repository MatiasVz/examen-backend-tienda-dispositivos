package com.apiweb.pagina.DTOs;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String cargo;
    private String departamento;
    private LocalDate fechaContratacion;
    private Boolean estaActivo;
    private Long empresaId;
} 