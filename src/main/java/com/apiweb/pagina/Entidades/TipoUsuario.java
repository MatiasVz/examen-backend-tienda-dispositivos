package com.apiweb.pagina.Entidades;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tipoUsuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TipoUsuario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long secuencial;

    private String nombre;
}