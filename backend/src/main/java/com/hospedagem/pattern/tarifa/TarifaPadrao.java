package com.hospedagem.pattern.tarifa;

import java.math.BigDecimal;

public class TarifaPadrao implements EstrategiaTarifa {
    @Override
    public BigDecimal aplicar(BigDecimal valorDiaria) { return valorDiaria; }
    @Override
    public String getNome() { return "PADRAO"; }
}
