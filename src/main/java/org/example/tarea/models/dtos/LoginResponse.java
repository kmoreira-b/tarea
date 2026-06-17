package org.example.tarea.models.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    String token;
    String username;
    String rol;
}
