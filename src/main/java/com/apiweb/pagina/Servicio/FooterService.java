package com.apiweb.pagina.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apiweb.pagina.Entidades.Footer;
import com.apiweb.pagina.Repositorios.FooterRepository;
import com.apiweb.pagina.DTOs.FooterDTO;
import com.apiweb.pagina.Entidades.RedSocial;

import java.util.List;
import java.util.Optional;

@Service
public class FooterService {
    @Autowired
    private FooterRepository footerRepository;

    public Footer guardar(Footer footer) {
        return footerRepository.save(footer);
    }

    public Footer guardarDesdeDTO(FooterDTO dto) {
        Footer footer = new Footer();
        footer.setDescripcion(dto.getDescripcion());
        footer.setTelefono(dto.getTelefono());
        footer.setAno(dto.getAno());
        if (dto.getEmpresaId() != null) {
            footer.setEmpresa(new com.apiweb.pagina.Entidades.Empresa());
            footer.getEmpresa().setSecuencial(dto.getEmpresaId());
        }
        return footerRepository.save(footer);
    }

    public Footer actualizarDesdeDTO(Long id, FooterDTO dto) {
        Footer footer = footerRepository.findById(id).orElseThrow(() -> new RuntimeException("Footer no encontrado con id: " + id));
        footer.setDescripcion(dto.getDescripcion());
        footer.setTelefono(dto.getTelefono());
        footer.setAno(dto.getAno());
        if (dto.getEmpresaId() != null) {
            footer.setEmpresa(new com.apiweb.pagina.Entidades.Empresa());
            footer.getEmpresa().setSecuencial(dto.getEmpresaId());
        }
        return footerRepository.save(footer);
    }

    public List<Footer> listar() {
        return footerRepository.findAll();
    }

    public Optional<Footer> buscarPorId(Long id) {
        return footerRepository.findById(id);
    }

    public void eliminar(Long id) {
        footerRepository.deleteById(id);
    }
} 