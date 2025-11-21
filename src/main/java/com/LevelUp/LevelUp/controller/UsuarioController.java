package com.LevelUp.LevelUp.controller;

import com.LevelUp.LevelUp.model.Usuario;
import com.LevelUp.LevelUp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
//@Tag(name= "Usuarios", description= "Usuarios Management System")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    //@Operation(summary = "Ver la lista de los Usuarios disponibles")
    public List<Usuario> getAllUsuarios(){
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    //@Operation(summary = "Toma a los Usuarios por Id")
    public Usuario getUsuarioById(@PathVariable Long id){
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    //@Operation(summary = "Añadir nuevo Usuario")
    public Usuario creteUsuario(@RequestBody Usuario usuario){
        return usuarioService.saveUsuario(usuario);
    }

    @PutMapping("/{id}")
    //@Operation(summary = "Actualiza un Usuario existente")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario existingUsuario = usuarioService.getUsuarioById(id);
        if (existingUsuario != null){
            existingUsuario.setNombre(usuario.getNombre());
            existingUsuario.setEdad(usuario.getEdad());
            existingUsuario.setEmail(usuario.getEmail());
            existingUsuario.setClave1(usuario.getClave1());
            existingUsuario.setClave2(usuario.getClave2());
            existingUsuario.setDireccion(usuario.getDireccion());
            return usuarioService.saveUsuario(existingUsuario);
        }

        return null;

    }
    @DeleteMapping("/{id}")
    //@Operation(summary = "Eliminar Usuario")
    public void deleteUsuario(@PathVariable Long id){
        usuarioService.deleteUsuario(id);
    }

}