package com.LevelUp.LevelUp.controller;

import com.LevelUp.LevelUp.model.Usuario;
import com.LevelUp.LevelUp.security.jwt.JwtService;
import com.LevelUp.LevelUp.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService, UsuarioService usuarioService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> body) {
        try {
            String email = (String) body.get("email");
            String clave1 = (String) body.get("clave1");
            String nombre = (String) body.get("nombre");
            String direccion = (String) body.get("direccion");


            int edad = 0;
            if (body.get("edad") != null) {
                edad = Integer.parseInt(body.get("edad").toString());
            }

            if (email == null || email.isBlank() || !email.contains("@")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email inválido"));
            }
            if (clave1 == null || clave1.length() < 6) {
                return ResponseEntity.badRequest().body(Map.of("error", "La clave es muy corta"));
            }
            if (nombre == null || nombre.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "El nombre es obligatorio"));
            }
            if (edad < 18 || edad ==0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Debes ser mayor de 14 años"));
            }

            Usuario datosRegistro = Usuario.builder()
                    .email(email)
                    .clave1(clave1)
                    .nombre(nombre)
                    .edad(edad)
                    .direccion(direccion)
                    .build();
            usuarioService.register(datosRegistro);

            return ResponseEntity.ok(Map.of("message", "Usuario registrado correctamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String clave1 = body.get("clave1");

            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, clave1));

            if (auth.isAuthenticated()) {
                Usuario usuario = usuarioService.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                String token = jwtService.generateToken(email, usuario.getRol());

                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "Gmail", email,
                        "role", usuario.getRol()
                ));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Gmail o contraseña incorrectos"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar la solicitud"));
        }
    }

}
