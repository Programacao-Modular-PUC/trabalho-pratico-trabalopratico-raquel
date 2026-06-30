package com.hospedagem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@DiscriminatorValue("FAMILIA")
public class QuartoFamilia extends Quarto {
    private int camasSolteiro = 2;
    private int camasCasal = 1;
    private int camasQueenKing = 0;
    private int quantidadeAmbientes = 1;

    @Override
    public TipoQuarto getTipo() { return TipoQuarto.FAMILIA; }

    @Override
    public int calcularCapacidadeMaxima() {
        return camasSolteiro + (camasCasal * 2) + (camasQueenKing * 2);
    }

    @Override
    public BigDecimal calcularDiaria(int quantidadeHospedes, boolean solicitaBerco) {
        BigDecimal total = calcularAdicionaisFixos();
        BigDecimal acrescimoPorHospede = total.multiply(new BigDecimal("0.12")).multiply(BigDecimal.valueOf(Math.max(0, quantidadeHospedes - 1)));
        total = total.add(acrescimoPorHospede);
        if (quantidadeHospedes >= 4) {
            total = total.multiply(new BigDecimal("0.90"));
        }
        if (quantidadeAmbientes > 1) {
            total = total.add(new BigDecimal("45.00").multiply(BigDecimal.valueOf(quantidadeAmbientes - 1L)));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public int getCamasSolteiro() { return camasSolteiro; }
    public void setCamasSolteiro(int camasSolteiro) { this.camasSolteiro = camasSolteiro; }
    public int getCamasCasal() { return camasCasal; }
    public void setCamasCasal(int camasCasal) { this.camasCasal = camasCasal; }
    public int getCamasQueenKing() { return camasQueenKing; }
    public void setCamasQueenKing(int camasQueenKing) { this.camasQueenKing = camasQueenKing; }
    public int getQuantidadeAmbientes() { return quantidadeAmbientes; }
    public void setQuantidadeAmbientes(int quantidadeAmbientes) { this.quantidadeAmbientes = quantidadeAmbientes; }
}
