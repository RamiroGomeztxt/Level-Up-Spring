package com.LevelUp.LevelUp.dataLoader;

import com.LevelUp.LevelUp.model.Producto;
import com.LevelUp.LevelUp.model.Usuario;
import com.LevelUp.LevelUp.repository.ProductoRepository;
import com.LevelUp.LevelUp.repository.UsuarioRepository;
import com.LevelUp.LevelUp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class dataLoader implements CommandLineRunner, LinkImages {

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
                .nombre("Teclado De Oficina HP")
                .descripcion("Teclado de membrana especial para oficina y comodidad")
                .categoria("consolas")
                .precio("89990")
                .imagen(teclado1)
                .build());

        productoRepository.saveAll(productos);


        productos.add(Producto.builder()
                .nombre("Teclado De Kumara")
                .descripcion("Teclado mecanico Moderno y Conveniente con una duacion prolongada en sus teclas")
                .categoria("consolas")
                .precio("89990")
                .imagen(teclado2)
                .build());

        productoRepository.saveAll(productos);



        Usuario usuario = Usuario.builder()
                .nombre("usuario_admin")
                .email("admin@gmail.com")
                .clave1(passwordEncoder.encode("admin123"))
                .rol("ADMIN")
                .build();


        usuarioRepository.save(usuario);
    }
}
