package com.apiweb.pagina.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apiweb.pagina.Entidades.RedSocial;
import com.apiweb.pagina.Repositorios.RedSocialRepository;
import com.apiweb.pagina.DTOs.RedSocialDTO;
import com.apiweb.pagina.Entidades.Footer;

import java.util.List;
import java.util.Optional;

@Service
public class RedSocialService {
    @Autowired
    private RedSocialRepository redSocialRepository;

    public RedSocial guardar(RedSocial redSocial) {
        return redSocialRepository.save(redSocial);
    }

    public RedSocial guardarDesdeDTO(RedSocialDTO dto) {
        RedSocial red = new RedSocial();
        red.setNombre(dto.getNombre());
        red.setUrl(dto.getUrl());
        if (dto.getFooterId() != null) {
            Footer footer = new Footer();
            footer.setId(dto.getFooterId());
            red.setFooter(footer);
        }
        return redSocialRepository.save(red);
    }

    public RedSocial actualizarDesdeDTO(Long id, RedSocialDTO dto) {
        RedSocial red = redSocialRepository.findById(id).orElseThrow(() -> new RuntimeException("Red social no encontrada con id: " + id));
        red.setNombre(dto.getNombre());
        red.setUrl(dto.getUrl());
        if (dto.getFooterId() != null) {
            Footer footer = new Footer();
            footer.setId(dto.getFooterId());
            red.setFooter(footer);
        }
        return redSocialRepository.save(red);
    }

    public List<RedSocial> listar() {
        return redSocialRepository.findAll();
    }

    public Optional<RedSocial> buscarPorId(Long id) {
        return redSocialRepository.findById(id);
    }

    public void eliminar(Long id) {
        redSocialRepository.deleteById(id);
    }
} 