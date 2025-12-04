package com.LevelUp.LevelUp.dataLoader;

import com.LevelUp.LevelUp.model.Producto;
import com.LevelUp.LevelUp.repository.ProductoRepository;
import com.LevelUp.LevelUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner, LinkImages {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        List<Producto> productos = new ArrayList<>();

        productos.add(Producto.builder()
                .nombre("Teclado Mecánico RGB HyperX Alloy Origins")
                .categoria("Teclados")
                .precio(89990)
                .imagen(teclado1)
                .build());
    }
}
