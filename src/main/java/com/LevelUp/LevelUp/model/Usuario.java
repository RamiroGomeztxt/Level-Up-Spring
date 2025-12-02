package com.LevelUp.LevelUp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
        private Long id;
        private String nombre;

        private int edad;

        @Column(unique = true) // Importante para que no se repitan emails
        private String email;
        private String clave1;
        private String direccion;
        private String rol;
}
