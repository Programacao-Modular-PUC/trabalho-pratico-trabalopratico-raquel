package com.hospedagem.pattern.tarifa;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Component
public class GerenciadorTarifas {
    private final Map<String, EstrategiaTarifa> estrategias = new HashMap<>();

    public GerenciadorTarifas() {
        registrar(new TarifaPadrao());
        registrar(new TarifaAltaTemporada());
        registrar(new TarifaBaixaTemporada());
        registrar(new TarifaClienteFrequente());
    }

    public void registrar(EstrategiaTarifa estrategia) {
        estrategias.put(estrategia.getNome(), estrategia);
    }

    public BigDecimal calcular(String nomeEstrategia, BigDecimal valorDiaria) {
        EstrategiaTarifa estrategia = estrategias.getOrDefault(nomeEstrategia == null ? "PADRAO" : nomeEstrategia, estrategias.get("PADRAO"));
        return estrategia.aplicar(valorDiaria).setScale(2, RoundingMode.HALF_UP);
    }
}
