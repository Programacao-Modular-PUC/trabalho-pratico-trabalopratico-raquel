package com.hospedagem.pattern.pagamento;

import com.hospedagem.model.Pagamento;

public interface EstrategiaPagamento {
    Pagamento processar(Pagamento pagamento);
}
