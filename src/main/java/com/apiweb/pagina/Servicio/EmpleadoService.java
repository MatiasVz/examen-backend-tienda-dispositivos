package com.apiweb.pagina.Servicio;

import com.apiweb.pagina.Entidades.Empleado;
import com.apiweb.pagina.Entidades.Empresa;
import com.apiweb.pagina.Repositorios.EmpleadoRepository;
import com.apiweb.pagina.Repositorios.EmpresaRepository;
import com.apiweb.pagina.DTOs.EmpleadoDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpresaRepository empresaRepository;

    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> findById(Long id) {
        return empleadoRepository.findById(id);
    }

    public List<Empleado> findByEmpresa(Long empresaId) {
        return empleadoRepository.findByEmpresaSecuencial(empresaId);
    }

    public List<Empleado> findActiveEmployees() {
        return empleadoRepository.findByEstaActivoTrue();
    }

    public List<Empleado> findActiveEmployeesByEmpresa(Long empresaId) {
        return empleadoRepository.findByEmpresaSecuencialAndEstaActivoTrue(empresaId);
    }

    public List<Empleado> findByNombreContaining(String nombre) {
        return empleadoRepository.findByNombreContaining(nombre);
    }

    public List<Empleado> findByCargo(String cargo) {
        return empleadoRepository.findByCargo(cargo);
    }

    public List<Empleado> findByDepartamento(String departamento) {
        return empleadoRepository.findByDepartamento(departamento);
    }

    @Transactional
    public Empleado save(Empleado empleado) {
        // Validar que la empresa existe si se proporciona
        if (empleado.getEmpresa() != null && empleado.getEmpresa().getSecuencial() != null) {
            Optional<Empresa> empresa = empresaRepository.findById(empleado.getEmpresa().getSecuencial());
            if (empresa.isPresent()) {
                empleado.setEmpresa(empresa.get());
            } else {
                throw new RuntimeException("Empresa no encontrada con id: " + empleado.getEmpresa().getSecuencial());
            }
        }
        
        // Establecer valores por defecto
        if (empleado.getEstaActivo() == null) {
            empleado.setEstaActivo(true);
        }
        
        return empleadoRepository.save(empleado);
    }

    @Transactional
    public Empleado saveFromDTO(EmpleadoDTO dto) {
        Empleado empleado = new Empleado();
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setEmail(dto.getEmail());
        empleado.setTelefono(dto.getTelefono());
        empleado.setCargo(dto.getCargo());
        empleado.setDepartamento(dto.getDepartamento());
        empleado.setFechaContratacion(dto.getFechaContratacion());
        empleado.setEstaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true);
        if (dto.getEmpresaId() != null) {
            Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + dto.getEmpresaId()));
            empleado.setEmpresa(empresa);
        }
        return empleadoRepository.save(empleado);
    }

    @Transactional
    public Empleado update(Long id, Empleado empleadoDTO) {
        Empleado empleadoExistente = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));

        // Actualizar campos
        empleadoExistente.setNombre(empleadoDTO.getNombre());
        empleadoExistente.setApellido(empleadoDTO.getApellido());
        empleadoExistente.setEmail(empleadoDTO.getEmail());
        empleadoExistente.setTelefono(empleadoDTO.getTelefono());
        empleadoExistente.setCargo(empleadoDTO.getCargo());
        empleadoExistente.setDepartamento(empleadoDTO.getDepartamento());
        empleadoExistente.setFechaContratacion(empleadoDTO.getFechaContratacion());
        empleadoExistente.setEstaActivo(empleadoDTO.getEstaActivo());

        // Actualizar empresa si se proporciona
        if (empleadoDTO.getEmpresa() != null && empleadoDTO.getEmpresa().getSecuencial() != null) {
            Optional<Empresa> empresa = empresaRepository.findById(empleadoDTO.getEmpresa().getSecuencial());
            if (empresa.isPresent()) {
                empleadoExistente.setEmpresa(empresa.get());
            } else {
                throw new RuntimeException("Empresa no encontrada con id: " + empleadoDTO.getEmpresa().getSecuencial());
            }
        }

        return empleadoRepository.save(empleadoExistente);
    }

    @Transactional
    public Empleado updateFromDTO(Long id, EmpleadoDTO dto) {
        Empleado empleadoExistente = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        empleadoExistente.setNombre(dto.getNombre());
        empleadoExistente.setApellido(dto.getApellido());
        empleadoExistente.setEmail(dto.getEmail());
        empleadoExistente.setTelefono(dto.getTelefono());
        empleadoExistente.setCargo(dto.getCargo());
        empleadoExistente.setDepartamento(dto.getDepartamento());
        empleadoExistente.setFechaContratacion(dto.getFechaContratacion());
        empleadoExistente.setEstaActivo(dto.getEstaActivo());
        if (dto.getEmpresaId() != null) {
            Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + dto.getEmpresaId()));
            empleadoExistente.setEmpresa(empresa);
        }
        return empleadoRepository.save(empleadoExistente);
    }

    @Transactional
    public Empleado deactivateEmployee(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        
        empleado.setEstaActivo(false);
        return empleadoRepository.save(empleado);
    }

    @Transactional
    public Empleado activateEmployee(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        
        empleado.setEstaActivo(true);
        return empleadoRepository.save(empleado);
    }

    public boolean deleteById(Long id) {
        if (empleadoRepository.existsById(id)) {
            empleadoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Long id) {
        return empleadoRepository.existsById(id);
    }
} 