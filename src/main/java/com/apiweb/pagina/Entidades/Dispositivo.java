package com.apiweb.pagina.Entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secuencial;

    private String nombre;
    private String categoria;
    private String marca;
    private String modelo;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    private Double precio;
    private Integer stock;
    private int estado; // 1: activo, 0: inactivo

    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DispositivoImagen> imagenes;
}