package com.apiweb.pagina.Servicio;

import com.apiweb.pagina.Entidades.TipoUsuario;
import com.apiweb.pagina.Repositorios.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoUsuarioService {
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public List<TipoUsuario> listar() {
        return tipoUsuarioRepository.findAll();
    }

    public Optional<TipoUsuario> buscarPorId(Long id) {
        return tipoUsuarioRepository.findById(id);
    }

    public TipoUsuario guardar(TipoUsuario tipoUsuario) {
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    public void eliminar(Long id) {
        tipoUsuarioRepository.deleteById(id);
    }

    public TipoUsuario actualizar(Long id, TipoUsuario tipoUsuario) {
        TipoUsuario existente = tipoUsuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado con id: " + id));
        existente.setNombre(tipoUsuario.getNombre());
        return tipoUsuarioRepository.save(existente);
    }
} 