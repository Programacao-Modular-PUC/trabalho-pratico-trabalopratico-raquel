package com.hospedagem.service;

import com.hospedagem.model.*;
import com.hospedagem.pattern.pagamento.ProcessadorPagamento;
import com.hospedagem.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PagamentoService {
    private final PagamentoRepository repository;
    private final AluguelService aluguelService;
    private final ProcessadorPagamento processadorPagamento;

    public PagamentoService(PagamentoRepository repository, AluguelService aluguelService, ProcessadorPagamento processadorPagamento) {
        this.repository = repository;
        this.aluguelService = aluguelService;
        this.processadorPagamento = processadorPagamento;
    }

    public List<Pagamento> listar() { return repository.findAll(); }

    public Pagamento pagar(Long aluguelId, FormaPagamento formaPagamento) {
        Aluguel aluguel = aluguelService.buscar(aluguelId);
        Pagamento pagamento = new Pagamento();
        pagamento.setAluguel(aluguel);
        pagamento.setValor(aluguel.getValorFinal());
        pagamento.setFormaPagamento(formaPagamento);
        pagamento = processadorPagamento.processar(pagamento);
        return repository.save(pagamento);
    }
}
