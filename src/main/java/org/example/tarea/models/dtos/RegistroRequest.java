package org.example.tarea.models.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequest {
    String username;
    String password;
}
