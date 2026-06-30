package com.hospedagem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("INDIVIDUAL")
public class QuartoIndividual extends Quarto {
    private int quantidadeCamasSolteiro = 1;

    @Override
    public TipoQuarto getTipo() { return TipoQuarto.INDIVIDUAL; }

    @Override
    public int calcularCapacidadeMaxima() { return quantidadeCamasSolteiro; }

    @Override
    public BigDecimal calcularDiaria(int quantidadeHospedes, boolean solicitaBerco) {
        BigDecimal total = calcularAdicionaisFixos();
        if (quantidadeCamasSolteiro > 1) {
            total = total.add(new BigDecimal("25.00").multiply(BigDecimal.valueOf(quantidadeCamasSolteiro - 1L)));
        }
        return total;
    }

    public int getQuantidadeCamasSolteiro() { return quantidadeCamasSolteiro; }
    public void setQuantidadeCamasSolteiro(int quantidadeCamasSolteiro) { this.quantidadeCamasSolteiro = quantidadeCamasSolteiro; }
}
