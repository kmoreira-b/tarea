package org.example.tarea.services;

import org.example.tarea.models.dtos.LoginRequest;
import org.example.tarea.models.dtos.LoginResponse;
import org.example.tarea.models.dtos.RegistroRequest;
import org.example.tarea.models.entities.Rol;
import org.example.tarea.models.entities.Usuario;
import org.example.tarea.repos.RolRepository;
import org.example.tarea.repos.UsuarioRepository;
import org.example.tarea.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    /** Rol que se asigna a todo usuario que se registra desde el frontend. */
    private static final String ROL_POR_DEFECTO = "USER";

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       UsuarioRepository usuarioRepository,
                       RolRepository rolRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }

        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername()).get();
        String rol = usuario.getRol().getNombre();
        String token = jwtService.generarToken(usuario.getUsername(), rol);

        return ResponseEntity.ok(new LoginResponse(token, usuario.getUsername(), rol));
    }

    /**
     * Registra un usuario nuevo y deja su sesion iniciada.*/

    public ResponseEntity<?> registrar(RegistroRequest registroRequest) {
        String username = registroRequest.getUsername();
        String password = registroRequest.getPassword();

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body("El usuario y la contraseña son obligatorios"); // validacion de campos
        }

        if (usuarioRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(409).body("El usuario '" + username + "' ya está registrado");// usuario existente
        }

        Rol rolUsuario = rolRepository.findByNombre(ROL_POR_DEFECTO)
                .orElseThrow(() -> new IllegalStateException("No existe el rol " + ROL_POR_DEFECTO));

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(rolUsuario);
        usuarioRepository.save(usuario);

        String token = jwtService.generarToken(usuario.getUsername(), ROL_POR_DEFECTO); // Genera el jwt para el usuario
        return ResponseEntity.status(201).body(new LoginResponse(token, usuario.getUsername(), ROL_POR_DEFECTO));
    }
}
