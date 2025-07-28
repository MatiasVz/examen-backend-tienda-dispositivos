package com.apiweb.pagina.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiweb.pagina.Entidades.Dispositivo;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long>{
    
}