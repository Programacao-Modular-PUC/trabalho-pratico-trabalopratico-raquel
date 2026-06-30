package com.hospedagem.pattern.tarifa;

import java.math.BigDecimal;

public class TarifaBaixaTemporada implements EstrategiaTarifa {
    @Override
    public BigDecimal aplicar(BigDecimal valorDiaria) { return valorDiaria.multiply(new BigDecimal("0.85")); }
    @Override
    public String getNome() { return "BAIXA_TEMPORADA"; }
}
