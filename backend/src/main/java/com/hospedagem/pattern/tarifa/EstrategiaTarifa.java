package com.hospedagem.pattern.tarifa;

import java.math.BigDecimal;

public interface EstrategiaTarifa {
    BigDecimal aplicar(BigDecimal valorDiaria);
    String getNome();
}
