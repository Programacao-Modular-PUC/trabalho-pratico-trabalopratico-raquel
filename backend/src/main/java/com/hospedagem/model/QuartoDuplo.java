package com.hospedagem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("DUPLO")
public class QuartoDuplo extends Quarto {
    @Enumerated(EnumType.STRING)
    private TipoCamaCasal tipoCama = TipoCamaCasal.COMUM;
    private boolean permiteBerco = true;

    @Override
    public TipoQuarto getTipo() { return TipoQuarto.DUPLO; }

    @Override
    public int calcularCapacidadeMaxima() { return permiteBerco ? 3 : 2; }

    @Override
    public BigDecimal calcularDiaria(int quantidadeHospedes, boolean solicitaBerco) {
        BigDecimal total = calcularAdicionaisFixos();
        if (tipoCama == TipoCamaCasal.QUEEN) total = total.add(new BigDecimal("60.00"));
        if (tipoCama == TipoCamaCasal.KING) total = total.add(new BigDecimal("90.00"));
        if (solicitaBerco) total = total.add(new BigDecimal("40.00"));
        return total;
    }

    public TipoCamaCasal getTipoCama() { return tipoCama; }
    public void setTipoCama(TipoCamaCasal tipoCama) { this.tipoCama = tipoCama; }
    public boolean isPermiteBerco() { return permiteBerco; }
    public void setPermiteBerco(boolean permiteBerco) { this.permiteBerco = permiteBerco; }
}
