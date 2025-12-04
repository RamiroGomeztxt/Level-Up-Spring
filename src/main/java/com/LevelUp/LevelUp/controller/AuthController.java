package com.LevelUp.LevelUp.controller;

import com.LevelUp.LevelUp.model.Usuario;
import com.LevelUp.LevelUp.security.jwt.JwtService;
import com.LevelUp.LevelUp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> body) {
        try {
            String email = (String) body.get("email");
            String clave1 = (String) body.get("clave1");
            String nombre = (String) body.get("nombre");
            String direccion = (String) body.get("direccion");
            String rol = body.get("rol") != null ? body.get("rol").toString() : "USER";

            int edad = 0;
            if (body.get("edad") != null) {
                edad = Integer.parseInt(body.get("edad").toString());
            }

            if(email.equals("admin@gmail.com")){
                rol ="ADMIN";


            }
            Usuario datosRegistro = Usuario.builder()
                    .email(email)
                    .clave1(clave1)
                    .nombre(nombre)
                    .edad(edad)
                    .direccion(direccion)
                    .rol(rol)
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
                        "usuario", Map.of(
                                "email", usuario.getEmail(),
                                "nombre", usuario.getNombre()
                        ),
                        "roles", List.of(usuario.getRol())
                ));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email o contraseña incorrectos"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar la solicitud"));
        }
    }
}
