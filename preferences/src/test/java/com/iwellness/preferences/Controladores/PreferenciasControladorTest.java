package com.iwellness.preferences.Controladores;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Servicios.PreferenciasServicio.IPreferenciasServicio;

public class PreferenciasControladorTest {

    private MockMvc mockMvc;

    @Mock
    private  IPreferenciasServicio preferenciasServicio;

    @InjectMocks
    private PreferenciasControlador preferenciasControlador;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(preferenciasControlador).build();
    }

    // GET /api/preferencias/all
    @Test
    public void testBuscarTodos_Sucess() throws Exception{
        List<Preferencias> preferencias = Arrays.asList(new Preferencias(), new Preferencias());
        when(preferenciasServicio.buscarTodos()).thenReturn(preferencias);

        mockMvc.perform(get("/api/preferencias/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

     // GET /api/preferencias/buscar/{id} - Éxito
    @Test
    public void testBuscarPorId_Success() throws Exception {
        Preferencias preferencias = new Preferencias();
        when(preferenciasServicio.BuscarPorId(1L)).thenReturn(preferencias);

        mockMvc.perform(get("/api/preferencias/buscar/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // GET /api/preferencias/buscar/{id} - No encontrado
    @Test
    public void testBuscarPorId_NotFound() throws Exception {
        when(preferenciasServicio.BuscarPorId(1L))
            .thenThrow(new NoSuchElementException("Servicio no encontrado con ID: 1"));

        mockMvc.perform(get("/api/preferencias/search/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

     // POST /api/preferencias/save/{preferencia} - Éxito
    @Test
    public void testGuardar_Success() throws Exception {
        Preferencias preferencias = new Preferencias();
        when(preferenciasServicio.guardar(any(Preferencias.class))).thenReturn(preferencias);

        String preferenciaJson = objectMapper.writeValueAsString(preferencias);

        // Se utiliza un valor "dummy" en la URL para completar la ruta
        mockMvc.perform(post("/api/preferencias/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(preferenciaJson))
                .andExpect(status().isOk());
    }

    // POST /api/preferencias/save/{preferencia} - Error al guardar
    @Test
    public void testGuardar_BadRequest() throws Exception {
        when(preferenciasServicio.guardar(any(Preferencias.class)))
            .thenThrow(new RuntimeException("Error al guardar el servicio"));

        Preferencias preferencias = new Preferencias();
        String preferenciaJson = objectMapper.writeValueAsString(preferencias);

        mockMvc.perform(post("/api/preferencias/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(preferenciaJson))
                .andExpect(status().isBadRequest());
    }

     // PUT /api/preferencias/editar/{preferencia} - Éxito
    @Test
    public void testActualizar_Success() throws Exception {
        Preferencias preferencias = new Preferencias();
        when(preferenciasServicio.actualizar(any(Preferencias.class))).thenReturn(preferencias);

        String preferenciaJson = objectMapper.writeValueAsString(preferencias);

        mockMvc.perform(put("/api/preferencias/editar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(preferenciaJson))
                .andExpect(status().isOk());
    }

    // PUT /api/preferencias/editar/{preferencia} - Error al actualizar
    @Test
    public void testActualizar_BadRequest() throws Exception {
        when(preferenciasServicio.actualizar(any(Preferencias.class)))
            .thenThrow(new RuntimeException("Error al actualizar el servicio"));

            Preferencias preferencias = new Preferencias();
        String preferenciaJson = objectMapper.writeValueAsString(preferencias);

        mockMvc.perform(put("/api/preferencias/editar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(preferenciaJson))
                .andExpect(status().isBadRequest());
    }

    // DELETE /api/preferencias/eliminar/{id} - Éxito
    @Test
    public void testEliminar_Success() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/preferencias/eliminar/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(preferenciasServicio).eliminar(id);
    }

    // DELETE /api/preferencias/eliminar/{id} - No encontrado
    @Test
    public void testEliminar_NotFound() throws Exception {
        Long id = 1L;

        doThrow(new RuntimeException("No se encontró la entidad con ID: " + id))
            .when(preferenciasServicio).eliminar(id);

        mockMvc.perform(delete("/api/preferencias/eliminar/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
