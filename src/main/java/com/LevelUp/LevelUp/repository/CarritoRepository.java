package com.LevelUp.LevelUp.repository;

import com.LevelUp.LevelUp.model.Carrito;
import com.LevelUp.LevelUp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuario(Usuario usuario);
}
