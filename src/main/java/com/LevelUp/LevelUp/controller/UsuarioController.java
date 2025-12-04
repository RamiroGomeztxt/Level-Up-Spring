package com.LevelUp.LevelUp.controller;

import com.LevelUp.LevelUp.model.Usuario;
import com.LevelUp.LevelUp.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/perfil")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> obtenerMiPerfil(Authentication auth) {
        try {
            Usuario usuario = usuarioService.getPerfil(auth.getName());

            Map<String, Object> response = new HashMap<>();
            response.put("id", usuario.getId());
            response.put("email", usuario.getEmail());
            response.put("nombre", usuario.getNombre());
            response.put("edad", usuario.getEdad());
            response.put("direccion", usuario.getDireccion());
            response.put("rol", usuario.getRol());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/me")
    public ResponseEntity<?> actualizarMiPerfil(Authentication auth,
                                                @RequestBody Usuario datosActualizados) {
        try {
            Usuario actualizado = usuarioService.actualizarPerfil(auth.getName(), datosActualizados);

            Map<String, Object> response = new HashMap<>();
            response.put("id", actualizado.getId());
            response.put("email", actualizado.getEmail());
            response.put("nombre", actualizado.getNombre());
            response.put("edad", actualizado.getEdad());
            response.put("direccion", actualizado.getDireccion());
            response.put("rol", actualizado.getRol());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ==============================
    //      ADMIN (requiere rol ADMIN)
    // ==============================

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario datos) {
        Usuario existente = usuarioService.getUsuarioById(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setNombre(datos.getNombre());
        existente.setEmail(datos.getEmail());
        existente.setEdad(datos.getEdad());
        existente.setDireccion(datos.getDireccion());
        existente.setRol(datos.getRol());

        return ResponseEntity.ok(existente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        Usuario existente = usuarioService.getUsuarioById(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.deleteUsuario(id);

        return ResponseEntity.ok(Map.of("message", "Usuario eliminado correctamente"));
    }
}
