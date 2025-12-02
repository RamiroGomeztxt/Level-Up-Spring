package com.LevelUp.LevelUp.service;

import com.LevelUp.LevelUp.model.*;
import com.LevelUp.LevelUp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final UsuarioService usuarioService;
    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final ProductoRepository productoRepository;

    // Obtener carrito del usuario (y si no existe, crearlo)
    public Carrito getCarritoByUser(String email) {
        Usuario usuario = usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return carritoRepository.findByUsuario(usuario)
                .orElseGet(() -> crearCarrito(usuario));
    }

    private Carrito crearCarrito(Usuario usuario) {
        Carrito carrito = Carrito.builder()
                .usuario(usuario)
                .build();
        return carritoRepository.save(carrito);
    }

    // Agregar producto al carrito
    public Carrito agregarProducto(String email, Long productoId, int cantidad) {
        Carrito carrito = getCarritoByUser(email);

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Buscar si el item ya existe
        CarritoItem itemExistente = carrito.getItems().stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
        } else {
            CarritoItem item = CarritoItem.builder()
                    .carrito(carrito)
                    .producto(producto)
                    .cantidad(cantidad)
                    .precioUnitario(producto.getPrecio())
                    .build();

            carrito.addItem(item);
        }

        return carritoRepository.save(carrito);
    }

    // Actualizar cantidad
    public Carrito actualizarCantidad(String email, Long itemId, int cantidad) {
        Carrito carrito = getCarritoByUser(email);

        CarritoItem item = carritoItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (cantidad <= 0) {
            carrito.removeItem(item);
            carritoItemRepository.delete(item);
        } else {
            item.setCantidad(cantidad);
            carritoItemRepository.save(item);
        }

        return carritoRepository.save(carrito);
    }

    // Eliminar item
    public Carrito eliminarItem(String email, Long itemId) {
        Carrito carrito = getCarritoByUser(email);

        CarritoItem item = carritoItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        carrito.removeItem(item);
        carritoItemRepository.delete(item);

        return carritoRepository.save(carrito);
    }

    // Vaciar todo el carrito
    public void vaciarCarrito(String email) {
        Carrito carrito = getCarritoByUser(email);

        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }
}
