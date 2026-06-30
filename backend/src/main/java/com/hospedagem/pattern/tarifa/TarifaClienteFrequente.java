package com.hospedagem.pattern.tarifa;

import java.math.BigDecimal;

public class TarifaClienteFrequente implements EstrategiaTarifa {
    @Override
    public BigDecimal aplicar(BigDecimal valorDiaria) { return valorDiaria.multiply(new BigDecimal("0.90")); }
    @Override
    public String getNome() { return "CLIENTE_FREQUENTE"; }
}
