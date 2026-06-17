package org.example.tarea.services;

import org.example.tarea.models.dtos.LoginRequest;
import org.example.tarea.models.dtos.LoginResponse;
import org.example.tarea.models.entities.Usuario;
import org.example.tarea.repos.UsuarioRepository;
import org.example.tarea.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
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
}
