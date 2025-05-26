package com.iwellness.preferences.DTO;

import lombok.Data;

@Data
public class TuristaDTO {
    private Long _idUsuario;
    private TuristaInfo turistaInfo;


    @Data
    public static class TuristaInfo {
       private String pais;
       private String estadoCivil;
       private String genero;
    }
}