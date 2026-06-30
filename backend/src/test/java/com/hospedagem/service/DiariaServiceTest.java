package com.hospedagem.service;

import com.hospedagem.exception.DataInvalidaException;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class DiariaServiceTest {
    private final DiariaService service = new DiariaService();

    @Test
    void deveCalcularUmaDiariaQuandoPeriodoForMenorQue24Horas() {
        LocalDateTime entrada = LocalDateTime.of(2026, 6, 20, 14, 0);
        LocalDateTime saida = LocalDateTime.of(2026, 6, 21, 10, 0);

        long diarias = service.calcularQuantidadeDiarias(entrada, saida);

        assertEquals(1, diarias);
    }

    @Test
    void deveAdicionarNovaDiariaQuandoSaidaForAposMeioDia() {
        LocalDateTime entrada = LocalDateTime.of(2026, 6, 20, 12, 0);
        LocalDateTime saida = LocalDateTime.of(2026, 6, 21, 13, 0);

        long diarias = service.calcularQuantidadeDiarias(entrada, saida);

        assertEquals(2, diarias);
    }

    @Test
    void deveLancarDataInvalidaQuandoSaidaForAntesDaEntrada() {
        LocalDateTime entrada = LocalDateTime.of(2026, 6, 22, 12, 0);
        LocalDateTime saida = LocalDateTime.of(2026, 6, 20, 12, 0);

        assertThrows(DataInvalidaException.class, () ->
                service.calcularQuantidadeDiarias(entrada, saida));
    }
}
