package com.iwellness.preferences.Servicios;


import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iwellness.preferences.Clientes.TuristaFeignClient;
import com.iwellness.preferences.DTO.TuristaDTO;
import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Entidades.TuristaXPreferencia;
import com.iwellness.preferences.Repositorios.IPreferenciasRepositorio;
import com.iwellness.preferences.Repositorios.ITuristaXPreferenciaRepositorio;
import com.iwellness.preferences.Servicios.TuristaXPreferenciaServicio.ITuristaXPreferenciaServicioImpl;

@ExtendWith(MockitoExtension.class)
class TuristaXPreferenciaServicioImplTest {

    @Mock
    private ITuristaXPreferenciaRepositorio turistaXPreferenciaRepositorio;

    @Mock
    private IPreferenciasRepositorio preferenciasRepositorio;

    @Mock
    private TuristaFeignClient turistaFeignClient;

    @InjectMocks
    private ITuristaXPreferenciaServicioImpl servicio;

    @Test
    void testGuardar_TuristaXPreferenciaValida_DevuelveGuardado() {
        // Arrange
        Long idPreferencia = 1L;
        Long idUsuario = 10L;

        Preferencias preferencia = new Preferencias();
        preferencia.set_idPreferencias(idPreferencia);
        preferencia.setNombre("Cultura");

        TuristaXPreferencia txp = new TuristaXPreferencia();
        txp.setIdUsuario(idUsuario);
        txp.setPreferencia(preferencia);

        TuristaDTO turistaDTO = new TuristaDTO(); // puede estar vacío para este test

        when(preferenciasRepositorio.findById(idPreferencia)).thenReturn(Optional.of(preferencia));
        when(turistaFeignClient.obtenerTurista(idUsuario)).thenReturn(turistaDTO);
        when(turistaXPreferenciaRepositorio.save(any())).thenReturn(txp);

        // Act
        TuristaXPreferencia resultado = servicio.guardar(txp);

        // Assert
        assertNotNull(resultado);
        assertEquals(idUsuario, resultado.getIdUsuario());
        verify(turistaXPreferenciaRepositorio, times(1)).save(any());
    }

    @Test
    void testGuardar_PreferenciaNoExiste_LanzaExcepcion() {
        // Arrange
        Preferencias preferencia = new Preferencias();
        preferencia.set_idPreferencias(1L);

        TuristaXPreferencia txp = new TuristaXPreferencia();
        txp.setPreferencia(preferencia);

        when(preferenciasRepositorio.findById(1L)).thenReturn(Optional.empty());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.guardar(txp);
        });
    }

    @Test
    void testGuardar_TuristaNoExiste_LanzaExcepcion() {
        // Arrange
        Preferencias preferencia = new Preferencias();
        preferencia.set_idPreferencias(1L);

        TuristaXPreferencia txp = new TuristaXPreferencia();
        txp.setIdUsuario(999L);
        txp.setPreferencia(preferencia);

        when(preferenciasRepositorio.findById(1L)).thenReturn(Optional.of(preferencia));
        when(turistaFeignClient.obtenerTurista(999L)).thenReturn(null);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.guardar(txp);
        });
    }

    @Test
    void testGuardar_ErrorFeignClient_LanzaRuntimeException() {
        // Arrange
        Preferencias preferencia = new Preferencias();
        preferencia.set_idPreferencias(1L);

        TuristaXPreferencia txp = new TuristaXPreferencia();
        txp.setIdUsuario(999L);
        txp.setPreferencia(preferencia);

        when(preferenciasRepositorio.findById(1L)).thenReturn(Optional.of(preferencia));
        when(turistaFeignClient.obtenerTurista(999L)).thenThrow(new RuntimeException("Feign error"));

        // Assert
        assertThrows(RuntimeException.class, () -> {
            servicio.guardar(txp);
        });
    }
}