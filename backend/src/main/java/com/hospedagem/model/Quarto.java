package com.hospedagem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_quarto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin("0.0")
    private BigDecimal valorBase = BigDecimal.ZERO;

    private boolean possuiAr;
    private boolean possuiHidro;
    private boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residencia_id")
    @JsonIgnoreProperties("quartos")
    @JsonIgnore
    private Residencia residencia;

    public abstract TipoQuarto getTipo();
    public abstract int calcularCapacidadeMaxima();
    public abstract BigDecimal calcularDiaria(int quantidadeHospedes, boolean solicitaBerco);

    protected BigDecimal calcularAdicionaisFixos() {
        BigDecimal total = valorBase == null ? BigDecimal.ZERO : valorBase;
        if (possuiAr) total = total.add(new BigDecimal("35.00"));
        if (possuiHidro) total = total.add(new BigDecimal("80.00"));
        return total;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getValorBase() { return valorBase; }
    public void setValorBase(BigDecimal valorBase) { this.valorBase = valorBase; }
    public boolean isPossuiAr() { return possuiAr; }
    public void setPossuiAr(boolean possuiAr) { this.possuiAr = possuiAr; }
    public boolean isPossuiHidro() { return possuiHidro; }
    public void setPossuiHidro(boolean possuiHidro) { this.possuiHidro = possuiHidro; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public Residencia getResidencia() { return residencia; }
    public void setResidencia(Residencia residencia) { this.residencia = residencia; }
}
