package com.LevelUp.LevelUp.repository;


import com.LevelUp.LevelUp.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
