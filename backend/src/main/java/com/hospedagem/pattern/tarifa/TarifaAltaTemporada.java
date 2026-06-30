package com.hospedagem.pattern.tarifa;

import java.math.BigDecimal;

public class TarifaAltaTemporada implements EstrategiaTarifa {
    @Override
    public BigDecimal aplicar(BigDecimal valorDiaria) { return valorDiaria.multiply(new BigDecimal("1.30")); }
    @Override
    public String getNome() { return "ALTA_TEMPORADA"; }
}
