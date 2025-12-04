package com.LevelUp.LevelUp.service;

import com.LevelUp.LevelUp.model.Producto;
import com.LevelUp.LevelUp.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    public List<Producto>getProductosbyCategoria(String categoria){
        return productoRepository.findByCategoria(categoria);
    }
}
