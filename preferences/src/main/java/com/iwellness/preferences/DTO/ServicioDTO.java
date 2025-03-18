package com.iwellness.preferences.DTO;

import lombok.Data;

@Data
public class ServicioDTO {
    private Long _idServicio;
    private Long _idProveedor;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen;
    private String horario;
    private boolean estado;

}
