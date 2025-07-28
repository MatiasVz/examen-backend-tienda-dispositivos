package com.apiweb.pagina.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "footer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Footer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private String telefono;
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;


    @JsonManagedReference
    @OneToMany(mappedBy = "footer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RedSocial> redesSociales;
} 