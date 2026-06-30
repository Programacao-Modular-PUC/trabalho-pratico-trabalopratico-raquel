package com.hospedagem.service;

import com.hospedagem.model.*;
import com.hospedagem.repository.QuartoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuartoServiceTest {
    @Mock private QuartoRepository repository;
    @Mock private ResidenciaService residenciaService;

    private QuartoService service;

    @BeforeEach
    void setUp() {
        service = new QuartoService(repository, residenciaService);
    }

    @Test
    void deveBuscarQuartoPorId() {
        QuartoIndividual quarto = new QuartoIndividual();
        quarto.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(quarto));

        Quarto encontrado = service.buscar(1L);

        assertEquals(1L, encontrado.getId());
    }

    @Test
    void deveFiltrarQuartosPorTipo() {
        QuartoIndividual individual = new QuartoIndividual();
        QuartoDuplo duplo = new QuartoDuplo();
        QuartoFamilia familia = new QuartoFamilia();
        when(repository.findAll()).thenReturn(List.of(individual, duplo, familia));

        List<Quarto> filtrados = service.filtrarPorTipo(TipoQuarto.DUPLO);

        assertEquals(1, filtrados.size());
        assertEquals(TipoQuarto.DUPLO, filtrados.get(0).getTipo());
    }
}
