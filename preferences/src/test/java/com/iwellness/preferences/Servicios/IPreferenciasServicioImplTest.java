package com.iwellness.preferences.Servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Repositorios.IPreferenciasRepositorio;
import com.iwellness.preferences.Servicios.PreferenciasServicio.IPreferenciasServicioImpl;

public class IPreferenciasServicioImplTest {

    @InjectMocks
    private IPreferenciasServicioImpl preferenciasServicio;

    @Mock
    private IPreferenciasRepositorio preferenciasRepositorio;

     @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodos() {
        Preferencias preferencia1 = new Preferencias();
        Preferencias preferencia2 = new Preferencias();
        List<Preferencias> preferencias = Arrays.asList(preferencia1, preferencia2);

        when(preferenciasRepositorio.findAll()).thenReturn(preferencias);

        List<Preferencias> result = preferenciasServicio.buscarTodos();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(preferenciasRepositorio, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId() {
        Preferencias preferencias = new Preferencias();
        when(preferenciasRepositorio.findById(1L)).thenReturn(Optional.of(preferencias));

        Preferencias result = preferenciasServicio.BuscarPorId(1L);

        assertNotNull(result);
        verify(preferenciasRepositorio, times(1)).findById(1L);
    }
    @Test
    public void testBuscarPorId_NoExistente() {
        when(preferenciasRepositorio.findById(1L)).thenReturn(Optional.empty());
    
        try {
            preferenciasServicio.BuscarPorId(1L);
        } catch (Exception e) {
            assertEquals(NoSuchElementException.class, e.getClass());
        }
    
        verify(preferenciasRepositorio, times(1)).findById(1L);
    }
    
    @Test
    public void testGuardar() {
        Preferencias preferencias = new Preferencias();
        when(preferenciasRepositorio.save(preferencias)).thenReturn(preferencias);

        Preferencias result = preferenciasServicio.guardar(preferencias);

        assertNotNull(result);
        verify(preferenciasRepositorio, times(1)).save(preferencias);
    }

    @Test
    public void testActualizar() {
        Preferencias preferencias = new Preferencias();
        when(preferenciasRepositorio.save(preferencias)).thenReturn(preferencias);

        Preferencias result = preferenciasServicio.actualizar(preferencias);

        assertNotNull(result);
        verify(preferenciasRepositorio, times(1)).save(preferencias);
    }

    @Test
    public void testEliminar() {
        Long id = 1L;

        preferenciasServicio.eliminar(id);

        verify(preferenciasRepositorio, times(1)).deleteById(id);
    }

}
