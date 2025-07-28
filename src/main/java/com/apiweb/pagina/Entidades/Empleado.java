package com.apiweb.pagina.Entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secuencial;
    
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String cargo;
    private String departamento;
    
    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;
    
    @Column(name = "esta_activo")
    private Boolean estaActivo = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    @JsonBackReference
    private Empresa empresa;
    
    @Override
    public String toString() {
        return "Empleado{" +
                "secuencial=" + secuencial +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", cargo='" + cargo + '\'' +
                ", departamento='" + departamento + '\'' +
                ", estaActivo=" + estaActivo +
                ", empresa=" + (empresa != null ? empresa.getSecuencial() : "null") +
                '}';
    }
} 