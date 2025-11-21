package com.LevelUp.LevelUp.repository;


import com.LevelUp.LevelUp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}