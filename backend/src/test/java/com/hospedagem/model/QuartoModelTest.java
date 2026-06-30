package com.hospedagem.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class QuartoModelTest {

    @Test
    void deveCalcularDiariaDoQuartoIndividualComAdicionalPorCamaExtra() {
        QuartoIndividual quarto = new QuartoIndividual();
        quarto.setValorBase(new BigDecimal("100.00"));
        quarto.setQuantidadeCamasSolteiro(3);

        BigDecimal valor = quarto.calcularDiaria(3, false);

        assertEquals(0, new BigDecimal("150.00").compareTo(valor));
        assertEquals(3, quarto.calcularCapacidadeMaxima());
    }

    @Test
    void deveCalcularDiariaDoQuartoDuploComCamaKingEBerco() {
        QuartoDuplo quarto = new QuartoDuplo();
        quarto.setValorBase(new BigDecimal("150.00"));
        quarto.setTipoCama(TipoCamaCasal.KING);
        quarto.setPermiteBerco(true);

        BigDecimal valor = quarto.calcularDiaria(2, true);

        assertEquals(0, new BigDecimal("280.00").compareTo(valor));
        assertEquals(3, quarto.calcularCapacidadeMaxima());
    }

    @Test
    void deveCalcularCapacidadeDoQuartoFamilia() {
        QuartoFamilia quarto = new QuartoFamilia();
        quarto.setCamasSolteiro(2);
        quarto.setCamasCasal(1);
        quarto.setCamasQueenKing(1);

        assertEquals(6, quarto.calcularCapacidadeMaxima());
    }

    @Test
    void deveCalcularDiariaDoQuartoFamiliaComDescontoParaGrupo() {
        QuartoFamilia quarto = new QuartoFamilia();
        quarto.setValorBase(new BigDecimal("300.00"));
        quarto.setQuantidadeAmbientes(1);

        BigDecimal valor = quarto.calcularDiaria(4, false);

        assertEquals(0, new BigDecimal("367.20").compareTo(valor));
    }
}
