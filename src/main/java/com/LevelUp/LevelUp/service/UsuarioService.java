package com.LevelUp.LevelUp.service;


import com.LevelUp.LevelUp.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.LevelUp.LevelUp.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service

public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Usuario register(Usuario datos) {
        if (usuarioRepository.findByEmail(datos.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuarioNuevo = Usuario.builder()
                .email(datos.getEmail())
                .clave1(passwordEncoder.encode(datos.getClave1()))
                .rol("USER")
                .nombre(datos.getNombre())
                .edad(datos.getEdad())
                .direccion(datos.getDireccion())
                .build();

        return usuarioRepository.save(usuarioNuevo);
    }


    public Usuario getPerfil(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }


    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    public Usuario actualizarPerfil(String email, Usuario datos) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(datos.getNombre());
        usuario.setEdad(datos.getEdad());
        usuario.setEmail(datos.getEmail());
        usuario.setDireccion(datos.getDireccion());
        return usuarioRepository.save(usuario);
    }


    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }


}
