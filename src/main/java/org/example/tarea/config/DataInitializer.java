package org.example.tarea.config;

import org.example.tarea.models.entities.Rol;
import org.example.tarea.models.entities.Usuario;
import org.example.tarea.repos.RolRepository;
import org.example.tarea.repos.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Roles
        Rol superAdmin = rolRepository.findByNombre("SUPER-ADMIN-ROLE")
                .orElseGet(() -> crearRol("SUPER-ADMIN-ROLE"));
        Rol user = rolRepository.findByNombre("USER")
                .orElseGet(() -> crearRol("USER"));

        // Usuarios (password encriptado en MD5)
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            crearUsuario("admin", "admin123", superAdmin);
        }
        if (usuarioRepository.findByUsername("user").isEmpty()) {
            crearUsuario("user", "user123", user);
        }
    }

    private Rol crearRol(String nombre) {
        Rol rol = new Rol();
        rol.setNombre(nombre);
        return rolRepository.save(rol);
    }

    private void crearUsuario(String username, String password, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(rol);
        usuarioRepository.save(usuario);
    }
}
