package com.hospedagem.pattern;

import com.hospedagem.pattern.tarifa.GerenciadorTarifas;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TarifaStrategyTest {

    @Test
    void deveAplicarTarifaAltaTemporada() {
        GerenciadorTarifas gerenciador = new GerenciadorTarifas();

        BigDecimal valor = gerenciador.calcular("ALTA_TEMPORADA", new BigDecimal("100.00"));

        assertEquals(0, new BigDecimal("130.00").compareTo(valor));
    }

    @Test
    void deveAplicarTarifaBaixaTemporada() {
        GerenciadorTarifas gerenciador = new GerenciadorTarifas();

        BigDecimal valor = gerenciador.calcular("BAIXA_TEMPORADA", new BigDecimal("100.00"));

        assertEquals(0, new BigDecimal("85.00").compareTo(valor));
    }

    @Test
    void deveUsarTarifaPadraoQuandoNomeForInvalido() {
        GerenciadorTarifas gerenciador = new GerenciadorTarifas();

        BigDecimal valor = gerenciador.calcular("PROMOCAO_INEXISTENTE", new BigDecimal("100.00"));

        assertEquals(0, new BigDecimal("100.00").compareTo(valor));
    }
}
