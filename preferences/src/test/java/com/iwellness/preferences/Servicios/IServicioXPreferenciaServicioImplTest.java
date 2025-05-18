package com.iwellness.preferences.Servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iwellness.preferences.Clientes.ServicioFeignClient;
import com.iwellness.preferences.DTO.ServicioDTO;
import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Entidades.ServicioXPreferencia;
import com.iwellness.preferences.Repositorios.IPreferenciasRepositorio;
import com.iwellness.preferences.Repositorios.IServicioXPreferenciaRepositorio;
import com.iwellness.preferences.Servicios.ServicioXPreferenciaServicio.IServicioXPreferenciaServicioImpl;

public class IServicioXPreferenciaServicioImplTest {
    
    @InjectMocks
    private IServicioXPreferenciaServicioImpl servicio;

    @Mock
    private IServicioXPreferenciaRepositorio servicioRepo;

    @Mock
    private IPreferenciasRepositorio preferenciasRepo;

    @Mock
    private ServicioFeignClient servicioFeignClient;

    private Preferencias preferencia;
    private ServicioXPreferencia relacion;
    private ServicioDTO servicioDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        preferencia = new Preferencias();
        preferencia.set_idPreferencias(1L);

        relacion = new ServicioXPreferencia();
        relacion.set_idServicioXPreferencia(1L);
        relacion.setPreferencia(preferencia);
        relacion.setIdServicio(2L);

        servicioDTO = new ServicioDTO();
        servicioDTO.setNombre("Ejemplo Servicio");
    }

    @Test
    public void testBuscarTodos() {
        when(servicioRepo.findAll()).thenReturn(List.of(relacion));

        List<ServicioXPreferencia> resultado = servicio.buscarTodos();
        assertEquals(1, resultado.size());
        verify(servicioRepo).findAll();
    }

    @Test
    public void testObtenerPorIdServicio_Existe() {
        when(servicioRepo.findByIdServicio(2L)).thenReturn(List.of(relacion));

        List<ServicioXPreferencia> resultado = servicio.obtenerPorIdServicio(2L);
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void testObtenerPorIdServicio_NoExiste() {
        when(servicioRepo.findByIdServicio(99L)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.obtenerPorIdServicio(99L);
        });
        assertTrue(exception.getMessage().contains("No se encontraron relaciones"));
    }

    @Test
    public void testFindByPreferenciaId_Existe() {
        when(servicioRepo.findByPreferencia_IdPreferencias(1L)).thenReturn(List.of(relacion));

        List<ServicioXPreferencia> resultado = servicio.findByPreferencia_IdPreferencias(1L);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testFindByPreferenciaId_NoExiste() {
        when(servicioRepo.findByPreferencia_IdPreferencias(123L)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.findByPreferencia_IdPreferencias(123L);
        });
        assertTrue(exception.getMessage().contains("No se encontraron relaciones"));
    }

    @Test
    public void testGuardar_Exitoso() {
        when(preferenciasRepo.findById(1L)).thenReturn(Optional.of(preferencia));
        when(servicioFeignClient.obtenerServicio(2L)).thenReturn(servicioDTO);
        when(servicioRepo.save(any())).thenReturn(relacion);

        ServicioXPreferencia resultado = servicio.guardar(relacion);
        assertNotNull(resultado);
        verify(servicioRepo).save(any());
    }

    @Test
    public void testGuardar_PreferenciaNula() {
        ServicioXPreferencia r = new ServicioXPreferencia();
        r.setIdServicio(2L);
        r.setPreferencia(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.guardar(r);
        });
        assertTrue(exception.getMessage().contains("idPreferencias válido"));
    }

    @Test
    public void testGuardar_PreferenciaNoExiste() {
        when(preferenciasRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.guardar(relacion);
        });
        assertTrue(exception.getMessage().contains("La preferencia no existe"));
    }

    @Test
    public void testGuardar_ServicioNoExiste() {
        when(preferenciasRepo.findById(1L)).thenReturn(Optional.of(preferencia));
        when(servicioFeignClient.obtenerServicio(2L)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.guardar(relacion);
        });
        assertTrue(exception.getMessage().contains("no existe"));
    }

    @Test
    public void testActualizar_Exitoso() {
        when(servicioRepo.existsById(1L)).thenReturn(true);
        when(servicioRepo.save(any())).thenReturn(relacion);

        ServicioXPreferencia resultado = servicio.actualizar(relacion);
        assertEquals(1L, resultado.get_idServicioXPreferencia());
    }

    @Test
    public void testActualizar_NoExiste() {
        when(servicioRepo.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.actualizar(relacion);
        });
        assertTrue(exception.getMessage().contains("No se encontró la relación"));
    }

    @Test
    public void testEliminar_Exitoso() {
        when(servicioRepo.existsById(1L)).thenReturn(true);

        servicio.eliminar(1L);
        verify(servicioRepo).deleteById(1L);
    }

    @Test
    public void testEliminar_NoExiste() {
        when(servicioRepo.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.eliminar(1L);
        });
        assertTrue(exception.getMessage().contains("No se encontró la relación"));
    }

    @Test
    public void testEliminarPreferenciasPorServicio() {
        servicio.eliminarPreferenciasPorServicio(2L);
        verify(servicioRepo).deleteByidServicio(2L);
    }
}
