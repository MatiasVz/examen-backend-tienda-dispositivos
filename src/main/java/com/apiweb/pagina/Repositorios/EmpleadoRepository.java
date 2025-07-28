package com.apiweb.pagina.Repositorios;

import com.apiweb.pagina.Entidades.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
    List<Empleado> findByEmpresaSecuencial(Long empresaId);
    
    List<Empleado> findByEstaActivoTrue();
    
    List<Empleado> findByEmpresaSecuencialAndEstaActivoTrue(Long empresaId);
    
    @Query("SELECT e FROM Empleado e WHERE e.nombre LIKE %:nombre% OR e.apellido LIKE %:nombre%")
    List<Empleado> findByNombreContaining(@Param("nombre") String nombre);
    
    List<Empleado> findByCargo(String cargo);
    
    List<Empleado> findByDepartamento(String departamento);
} 