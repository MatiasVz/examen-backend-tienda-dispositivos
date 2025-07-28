package com.apiweb.pagina.Servicio;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.apiweb.pagina.Entidades.Banner;
import com.apiweb.pagina.Entidades.Empresa;
import com.apiweb.pagina.Entidades.Empleado;
import com.apiweb.pagina.Repositorios.BannerRepository;
import com.apiweb.pagina.Repositorios.EmpresaRepository;
import com.apiweb.pagina.Repositorios.EmpleadoRepository;
import com.apiweb.pagina.DTOs.EmpresaDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final BannerRepository bannerRepository;
    private final EmpleadoRepository empleadoRepository;

    @Transactional
    public Empresa saveFullEmpresa(Empresa empresaDTO) {
        Empresa empresa = new Empresa();
        empresa.setNombre(empresaDTO.getNombre());
        empresa.setLogo(empresaDTO.getLogo());
        empresa.setNombre(empresaDTO.getNombre());
        empresa.setVision(empresaDTO.getVision());
        empresa.setMision(empresaDTO.getMision());
       
        if (empresaDTO.getBanners() != null) {
            empresa.setBanners(empresaDTO.getBanners());
            for (Banner b : empresaDTO.getBanners()) {
                 Banner banner= new Banner();
                 banner.setSecuencial(b.getSecuencial());
                 banner.setDescripcion(b.getDescripcion());
                 banner.setUrl(b.getUrl());
                 banner.setEstaBanner(b.getEstaBanner());
                 banner.setEmpresa(empresa);
                 bannerRepository.save(banner);

            }
           
        }

        return empresaRepository.save(empresa);
    }
    
    @Transactional
    public Empresa updateEmpresa(Long id, Empresa empresaDTO) {
        Empresa empresaExistente = empresaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + id));

        empresaExistente.setNombre(empresaDTO.getNombre());
        empresaExistente.setLogo(empresaDTO.getLogo());
        empresaExistente.setVision(empresaDTO.getVision());
        empresaExistente.setMision(empresaDTO.getMision());
        empresaExistente.setAnio(empresaDTO.getAnio());
        empresaExistente.setRealizadopor(empresaDTO.getRealizadopor());

        if (empresaDTO.getBanners() != null) {
            List<Banner> bannersExistentes = empresaExistente.getBanners();

            Map<Long, Banner> mapExistente = bannersExistentes.stream()
                .filter(b -> b.getSecuencial() != null)
                .collect(Collectors.toMap(Banner::getSecuencial, Function.identity()));

            Set<Long> idsActualizados = new HashSet<>();

            for (Banner bannerDTO : empresaDTO.getBanners()) {
                if (bannerDTO.getSecuencial() != null && mapExistente.containsKey(bannerDTO.getSecuencial())) {
                    Banner bannerExistente = mapExistente.get(bannerDTO.getSecuencial());
                    bannerExistente.setDescripcion(bannerDTO.getDescripcion());
                    bannerExistente.setUrl(bannerDTO.getUrl());
                    bannerExistente.setEstaBanner(bannerDTO.getEstaBanner());
                    idsActualizados.add(bannerDTO.getSecuencial());
                } else {
                    Banner nuevoBanner = new Banner();
                    nuevoBanner.setDescripcion(bannerDTO.getDescripcion());
                    nuevoBanner.setUrl(bannerDTO.getUrl());
                    nuevoBanner.setEstaBanner(bannerDTO.getEstaBanner());
                    nuevoBanner.setEmpresa(empresaExistente);
                    bannersExistentes.add(nuevoBanner);
                }
            }

            // Eliminar los banners que no están en la nueva lista
            bannersExistentes.removeIf(banner -> 
                banner.getSecuencial() != null && !idsActualizados.contains(banner.getSecuencial())
            );
        }

        return empresaRepository.save(empresaExistente);
    }

    @Transactional
    public Empresa saveFromDTO(EmpresaDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setNombre(dto.getNombre());
        empresa.setLogo(dto.getLogo());
        empresa.setMision(dto.getMision());
        empresa.setVision(dto.getVision());
        empresa.setAnio(dto.getAnio());
        empresa.setRealizadopor(dto.getRealizadopor());
        return empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa updateFromDTO(Long id, EmpresaDTO dto) {
        Empresa empresaExistente = empresaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + id));
        empresaExistente.setNombre(dto.getNombre());
        empresaExistente.setLogo(dto.getLogo());
        empresaExistente.setMision(dto.getMision());
        empresaExistente.setVision(dto.getVision());
        empresaExistente.setAnio(dto.getAnio());
        empresaExistente.setRealizadopor(dto.getRealizadopor());
        return empresaRepository.save(empresaExistente);
    }

    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }
    
    public Optional<Empresa> findFirst() {
        return empresaRepository.findAll().stream().findFirst(); // o por ID fijo
    }

    public boolean deleteById(Long id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos para gestión de empleados
    public List<Empleado> getEmpleadosByEmpresa(Long empresaId) {
        return empleadoRepository.findByEmpresaSecuencial(empresaId);
    }

    public List<Empleado> getEmpleadosActivosByEmpresa(Long empresaId) {
        return empleadoRepository.findByEmpresaSecuencialAndEstaActivoTrue(empresaId);
    }

    @Transactional
    public Empleado addEmpleadoToEmpresa(Long empresaId, Empleado empleado) {
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + empresaId));
        
        empleado.setEmpresa(empresa);
        if (empleado.getEstaActivo() == null) {
            empleado.setEstaActivo(true);
        }
        
        return empleadoRepository.save(empleado);
    }

    @Transactional
    public boolean removeEmpleadoFromEmpresa(Long empresaId, Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + empleadoId));
        
        if (empleado.getEmpresa().getSecuencial().equals(empresaId)) {
            empleadoRepository.delete(empleado);
            return true;
        }
        return false;
    }
}
