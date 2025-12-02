package com.LevelUp.LevelUp.controller;

import com.LevelUp.LevelUp.model.Carrito;
import com.LevelUp.LevelUp.service.CarritoService;
import com.LevelUp.LevelUp.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;
    private final JwtService jwtService;

    private String getEmailFromHeader(String header) {
        String token = header.replace("Bearer ", "");
        return jwtService.extractUsername(token);
    }

    @GetMapping
    public Carrito obtenerCarrito(@RequestHeader("Authorization") String authHeader) {
        String email = getEmailFromHeader(authHeader);
        return carritoService.getCarritoByUser(email);
    }

    @PostMapping("/agregar")
    public Carrito agregarProducto(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long productoId,
            @RequestParam int cantidad
    ) {
        String email = getEmailFromHeader(authHeader);
        return carritoService.agregarProducto(email, productoId, cantidad);
    }

    @PutMapping("/actualizar")
    public Carrito actualizarCantidad(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long itemId,
            @RequestParam int cantidad
    ) {
        String email = getEmailFromHeader(authHeader);
        return carritoService.actualizarCantidad(email, itemId, cantidad);
    }

    @DeleteMapping("/eliminar")
    public Carrito eliminarItem(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long itemId
    ) {
        String email = getEmailFromHeader(authHeader);
        return carritoService.eliminarItem(email, itemId);
    }

    @DeleteMapping("/vaciar")
    public String vaciarCarrito(
            @RequestHeader("Authorization") String authHeader
    ) {
        String email = getEmailFromHeader(authHeader);
        carritoService.vaciarCarrito(email);
        return "Carrito vaciado correctamente";
    }
}
