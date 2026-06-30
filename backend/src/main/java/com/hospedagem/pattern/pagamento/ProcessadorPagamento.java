package com.hospedagem.pattern.pagamento;

import com.hospedagem.exception.RecursoNaoPermitidoException;
import com.hospedagem.model.FormaPagamento;
import com.hospedagem.model.Pagamento;
import org.springframework.stereotype.Component;
import java.util.EnumMap;
import java.util.Map;

@Component
public class ProcessadorPagamento {
    private final Map<FormaPagamento, EstrategiaPagamento> estrategias = new EnumMap<>(FormaPagamento.class);

    public ProcessadorPagamento() {
        estrategias.put(FormaPagamento.PIX, new PagamentoPix());
        estrategias.put(FormaPagamento.CARTAO_CREDITO, new PagamentoCartaoCredito());
        estrategias.put(FormaPagamento.CARTAO_DEBITO, new PagamentoCartaoDebito());
        estrategias.put(FormaPagamento.DINHEIRO, new PagamentoDinheiro());
    }

    public Pagamento processar(Pagamento pagamento) {
        EstrategiaPagamento estrategia = estrategias.get(pagamento.getFormaPagamento());
        if (estrategia == null) {
            throw new RecursoNaoPermitidoException("Forma de pagamento nao suportada.");
        }
        return estrategia.processar(pagamento);
    }
}
