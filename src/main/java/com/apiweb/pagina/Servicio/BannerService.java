package com.apiweb.pagina.Servicio;

import com.apiweb.pagina.DTOs.BannerDTO;
import com.apiweb.pagina.Entidades.Banner;
import com.apiweb.pagina.Entidades.Empresa;
import com.apiweb.pagina.Repositorios.BannerRepository;
import com.apiweb.pagina.Repositorios.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;
    private final EmpresaRepository empresaRepository;

    public List<Banner> listar() {
        return bannerRepository.findAll();
    }

    public Optional<Banner> buscarPorId(Long id) {
        return bannerRepository.findById(id);
    }

    @Transactional
    public Banner guardarDesdeDTO(BannerDTO dto) {
        Banner banner = new Banner();
        banner.setUrl(dto.getUrl());
        banner.setDescripcion(dto.getDescripcion());
        banner.setEstaBanner(dto.getEstaBanner() != null ? dto.getEstaBanner() : 1);
        if (dto.getEmpresaId() != null) {
            Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + dto.getEmpresaId()));
            banner.setEmpresa(empresa);
        }
        return bannerRepository.save(banner);
    }

    @Transactional
    public Banner actualizarDesdeDTO(Long id, BannerDTO dto) {
        Banner banner = bannerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Banner no encontrado con id: " + id));
        banner.setUrl(dto.getUrl());
        banner.setDescripcion(dto.getDescripcion());
        banner.setEstaBanner(dto.getEstaBanner() != null ? dto.getEstaBanner() : 1);
        if (dto.getEmpresaId() != null) {
            Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + dto.getEmpresaId()));
            banner.setEmpresa(empresa);
        }
        return bannerRepository.save(banner);
    }

    @Transactional
    public void eliminar(Long id) {
        bannerRepository.deleteById(id);
    }
} 