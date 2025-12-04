package com.LevelUp.LevelUp.controller;

import com.LevelUp.LevelUp.model.Producto;
import com.LevelUp.LevelUp.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/createproducto")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProducto(@RequestBody Producto producto) {
        Producto saved = productoService.save(producto);
        return ResponseEntity.ok(saved);
    }


    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public Producto getProducotById(@PathVariable Long id) {
        return productoService.findById(id);
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

    @GetMapping("/getcategoria/{categoria}")
    public List<Producto> getProductosbyCategoria(@PathVariable String categoria) {
        return productoService.getProductosbyCategoria(categoria);
    }



}
