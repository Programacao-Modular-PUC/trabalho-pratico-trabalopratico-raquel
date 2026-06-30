package com.hospedagem.pattern.pagamento;

import com.hospedagem.model.Pagamento;
import com.hospedagem.model.StatusPagamento;
import java.time.LocalDateTime;
import java.util.UUID;

public class PagamentoCartaoCredito implements EstrategiaPagamento {
    @Override
    public Pagamento processar(Pagamento pagamento) {
        pagamento.setStatus(StatusPagamento.CONFIRMADO);
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setCodigoConfirmacao("CRED-" + UUID.randomUUID());
        return pagamento;
    }
}
