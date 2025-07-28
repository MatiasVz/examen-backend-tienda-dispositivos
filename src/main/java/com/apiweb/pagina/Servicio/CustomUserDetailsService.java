package com.apiweb.pagina.Servicio;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apiweb.pagina.Entidades.Usuario;
import com.apiweb.pagina.Repositorios.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Username extraído del token: " + username);
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        Usuario usuario = usuarioOpt.orElseThrow(() -> 
            new UsernameNotFoundException("Usuario no encontrado con username: " + username));

        // Aquí debes convertir tu entidad Usuario a UserDetails
        return new org.springframework.security.core.userdetails.User(
            usuario.getUsername(), 
            usuario.getPassword(), 
            mapearRoles(usuario.getTipoUsuario().getNombre()) // Método para convertir roles a GrantedAuthority
        );
    }

    private Collection<? extends GrantedAuthority> mapearRoles(String nombreRol) {
        return List.of(new SimpleGrantedAuthority(nombreRol));
    }
}