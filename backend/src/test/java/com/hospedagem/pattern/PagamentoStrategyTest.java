package com.hospedagem.pattern;

import com.hospedagem.model.FormaPagamento;
import com.hospedagem.model.Pagamento;
import com.hospedagem.model.StatusPagamento;
import com.hospedagem.pattern.pagamento.ProcessadorPagamento;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class PagamentoStrategyTest {

    @Test
    void deveConfirmarPagamentoPix() {
        ProcessadorPagamento processador = new ProcessadorPagamento();
        Pagamento pagamento = new Pagamento();
        pagamento.setFormaPagamento(FormaPagamento.PIX);
        pagamento.setValor(new BigDecimal("500.00"));

        Pagamento processado = processador.processar(pagamento);

        assertEquals(StatusPagamento.CONFIRMADO, processado.getStatus());
        assertNotNull(processado.getDataPagamento());
        assertTrue(processado.getCodigoConfirmacao().startsWith("PIX-"));
    }
}
