package com.iwellness.preferences.DTO;

import java.util.List;

import lombok.Data;

@Data
public class UsuarioInteresesDTO {
    private String userId;
    private String pais;
    private List<String> intereses;
    private String fechaDeBusqueda;
}
