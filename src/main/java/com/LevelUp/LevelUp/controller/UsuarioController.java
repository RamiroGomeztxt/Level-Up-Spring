package com.LevelUp.LevelUp.controller;


import com.LevelUp.LevelUp.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.LevelUp.LevelUp.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping("/me")
    public Usuario obtenerMiPerfil(Authentication auth) {
        return usuarioService.getPerfil(auth.getName());
    }

    @PutMapping("/me")
    public Usuario actualizarMiPerfil(Authentication auth, @RequestBody Usuario datosActualizados) {
        return usuarioService.actualizarPerfil(auth.getName(),datosActualizados);
    }


}
