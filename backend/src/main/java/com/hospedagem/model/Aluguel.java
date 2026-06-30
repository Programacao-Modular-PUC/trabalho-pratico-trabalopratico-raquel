package com.hospedagem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    private Residencia residencia;

    @ManyToOne(optional = false)
    private Quarto quarto;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataEntrada;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataSaida;

    private int quantidadeHospedes;
    private boolean solicitaBerco;
    private long quantidadeDiarias;
    private BigDecimal valorFinal;

    @Enumerated(EnumType.STRING)
    private StatusAluguel status = StatusAluguel.RESERVADO;

    @OneToOne(mappedBy = "aluguel", cascade = CascadeType.ALL)
    @JsonIgnore
    private Pagamento pagamento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Residencia getResidencia() { return residencia; }
    public void setResidencia(Residencia residencia) { this.residencia = residencia; }
    public Quarto getQuarto() { return quarto; }
    public void setQuarto(Quarto quarto) { this.quarto = quarto; }
    public LocalDateTime getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDateTime dataEntrada) { this.dataEntrada = dataEntrada; }
    public LocalDateTime getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDateTime dataSaida) { this.dataSaida = dataSaida; }
    public int getQuantidadeHospedes() { return quantidadeHospedes; }
    public void setQuantidadeHospedes(int quantidadeHospedes) { this.quantidadeHospedes = quantidadeHospedes; }
    public boolean isSolicitaBerco() { return solicitaBerco; }
    public void setSolicitaBerco(boolean solicitaBerco) { this.solicitaBerco = solicitaBerco; }
    public long getQuantidadeDiarias() { return quantidadeDiarias; }
    public void setQuantidadeDiarias(long quantidadeDiarias) { this.quantidadeDiarias = quantidadeDiarias; }
    public BigDecimal getValorFinal() { return valorFinal; }
    public void setValorFinal(BigDecimal valorFinal) { this.valorFinal = valorFinal; }
    public StatusAluguel getStatus() { return status; }
    public void setStatus(StatusAluguel status) { this.status = status; }
    public Pagamento getPagamento() { return pagamento; }
    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }
}
