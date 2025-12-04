package com.LevelUp.LevelUp.controller;

import com.LevelUp.LevelUp.model.Producto;
import com.LevelUp.LevelUp.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public Producto getProducotById(@PathVariable Long id) {
        return productoService.findById(id);
    }

    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id) {
        productoService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto existingProducto = productoService.findById(id);

        if (existingProducto != null) {
            existingProducto.setNombre(producto.getNombre());
            existingProducto.setCategoria(producto.getCategoria());
            existingProducto.setPrecio(producto.getPrecio());
            existingProducto.setImagen(producto.getImagen());

            return productoService.save(existingProducto);
        }

        return null;
    }
}
