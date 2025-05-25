package com.iwellness.preferences.Controladores;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwellness.preferences.Clientes.TuristaFeignClient;
import com.iwellness.preferences.DTO.TuristaDTO;
import com.iwellness.preferences.DTO.TuristaDTO.TuristaInfo;
import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Entidades.TuristaXPreferencia;
import com.iwellness.preferences.Servicios.TuristaXPreferenciaServicio.ITuristaXPreferenciaServicio;

public class TuristaXPreferenciaControladorTest {

    private MockMvc mockMvc;

    @Mock
    private  ITuristaXPreferenciaServicio turistaXPreferenciaServicio;

    @InjectMocks
    private TuristaXPreferenciaControlador turistaXPreferenciaControlador;

    private ObjectMapper objectMapper = new ObjectMapper();

    private TuristaXPreferencia mockRelacion;

    @Mock
    private TuristaFeignClient turistaFeignClient;


 @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(turistaXPreferenciaControlador).build();

        // Crear la preferencia
        Preferencias preferencia = new Preferencias();
        preferencia.set_idPreferencias(1L);

        mockRelacion = new TuristaXPreferencia();
        // Aquí puedes configurar más campos si tienes
        mockRelacion.set_idTuristaXPreferencia(1L);
        mockRelacion.setIdUsuario(1L);
        mockRelacion.setPreferencia(preferencia);
    }

    @Test
    public void testObtenerTodas() throws Exception {
        when(turistaXPreferenciaServicio.buscarTodos()).thenReturn(Arrays.asList(mockRelacion));

        mockMvc.perform(get("/api/turistaXPreferencia/all"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0]._idTuristaXPreferencia").value(1L));
    }

    @Test
    public void testCrear_OK() throws Exception {
        when(turistaXPreferenciaServicio.guardar(mockRelacion)).thenReturn(mockRelacion);

        mockMvc.perform(post("/api/turistaXPreferencia/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRelacion)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$._idTuristaXPreferencia").value(1L));
    }

    @Test
    public void testCrear_BadRequest() throws Exception {
        when(turistaXPreferenciaServicio.guardar(mockRelacion)).thenThrow(new RuntimeException("Fallo"));

        mockMvc.perform(post("/api/turistaXPreferencia/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRelacion)))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testActualizar_OK() throws Exception {
        when(turistaXPreferenciaServicio.actualizar(mockRelacion)).thenReturn(mockRelacion);

        mockMvc.perform(put("/api/turistaXPreferencia/editar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRelacion)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$._idTuristaXPreferencia").value(1L));
    }

    @Test
    public void testActualizar_BadRequest() throws Exception {
        when(turistaXPreferenciaServicio.actualizar(mockRelacion)).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(put("/api/turistaXPreferencia/editar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRelacion)))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testEliminar_OK() throws Exception {
        doNothing().when(turistaXPreferenciaServicio).eliminar(1L);

        mockMvc.perform(delete("/api/turistaXPreferencia/eliminar/1"))
               .andExpect(status().isOk())
               .andExpect(content().string("turistaXpreferencia eliminado correctamente"));
    }

    @Test
    public void testEliminar_NotFound() throws Exception {
        // Simulamos excepción
        doThrow(new NoSuchElementException("No se encontró el ID")).when(turistaXPreferenciaServicio).eliminar(999L);  

        mockMvc.perform(delete("/api/turistaXPreferencia/eliminar/999"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testObtenerPorPreferencia_OK() throws Exception {
        when(turistaXPreferenciaServicio.findByPreferencia_IdPreferencias(1L)).thenReturn(Collections.singletonList(mockRelacion));

        mockMvc.perform(get("/api/turistaXPreferencia/preferencia/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].preferencia._idPreferencias").value(1L));
    }

    @Test
    public void testObtenerPorPreferencia_NotFound() throws Exception {
        when(turistaXPreferenciaServicio.findByPreferencia_IdPreferencias(1L)).thenThrow(new RuntimeException("No encontrado"));

        mockMvc.perform(get("/api/turistaXPreferencia/preferencia/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testEliminarPreferenciasPorTurista() throws Exception {
        doNothing().when(turistaXPreferenciaServicio).eliminarPreferenciasPorTurista(1L);

        mockMvc.perform(delete("/api/turistaXPreferencia/eliminarPorTurista/1"))
               .andExpect(status().isOk())
               .andExpect(content().string("Preferencias del servicio eliminadas correctamente"));
    }
}
