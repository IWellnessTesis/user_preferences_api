package com.iwellness.preferences.DTO;

import java.util.List;

import lombok.Data;

@Data
public class UsuarioInteresesEstadoDTO {
    private String userId;
    private List<String> intereses;
    private String estadoCivil;
    private String genero;
    
}
