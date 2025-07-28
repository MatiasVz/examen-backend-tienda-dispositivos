package com.apiweb.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiweb.pagina.Entidades.DispositivoImagen;

public interface DispositivoImagenRepository extends JpaRepository<DispositivoImagen, Long> {
    
}