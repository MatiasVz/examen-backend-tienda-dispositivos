package com.apiweb.pagina.DTOs;

import lombok.Data;
import java.util.List;

@Data
public class DispositivoDTO {
    private String nombre;
    private String categoria;
    private String marca;
    private String modelo;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private Integer estado;
    private List<String> imagenes; // URLs o base64
}