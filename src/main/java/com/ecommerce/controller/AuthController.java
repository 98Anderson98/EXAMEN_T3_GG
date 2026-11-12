package com.ecommerce.controller;

import com.ecommerce.dto.AuthResponseDTO;
import com.ecommerce.dto.LoginRequestDTO;
import com.ecommerce.dto.RegisterRequestDTO;
import com.ecommerce.entity.Rol;
import com.ecommerce.entity.Usuario;
import com.ecommerce.exception.ReglaNegocioException;
import com.ecommerce.repository.UsuarioRepository;
import com.ecommerce.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacion", description = "Login y registro (genera token JWT)")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
                          UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion y obtener token JWT")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
        UserDetails user = (UserDetails) auth.getPrincipal();
        String rol = user.getAuthorities().iterator().next().getAuthority();
        String token = jwtService.generarToken(user, Map.of("rol", rol));
        return ResponseEntity.ok(new AuthResponseDTO(token, "Bearer", user.getUsername(), rol));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo usuario (rol USER)")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequestDTO dto) {
        if (usuarioRepository.existsByUsername(dto.username())) {
            throw new ReglaNegocioException("El username ya existe");
        }
        Usuario u = new Usuario();
        u.setUsername(dto.username());
        u.setPassword(passwordEncoder.encode(dto.password()));
        u.setNombreCompleto(dto.nombreCompleto());
        u.setRol(Rol.USER);
        u.setActivo(true);
        usuarioRepository.save(u);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Usuario registrado correctamente", "username", u.getUsername()));
    }
}
