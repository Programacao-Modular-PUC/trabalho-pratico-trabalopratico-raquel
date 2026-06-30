package com.hospedagem.service;

import com.hospedagem.model.*;
import com.hospedagem.pattern.pagamento.ProcessadorPagamento;
import com.hospedagem.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {
    @Mock private PagamentoRepository repository;
    @Mock private AluguelService aluguelService;

    private PagamentoService service;

    @BeforeEach
    void setUp() {
        service = new PagamentoService(repository, aluguelService, new ProcessadorPagamento());
    }

    @Test
    void devePagarAluguelComPix() {
        Aluguel aluguel = new Aluguel();
        aluguel.setValorFinal(new BigDecimal("450.00"));

        when(aluguelService.buscar(1L)).thenReturn(aluguel);
        when(repository.save(any(Pagamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pagamento pagamento = service.pagar(1L, FormaPagamento.PIX);

        assertEquals(aluguel, pagamento.getAluguel());
        assertEquals(FormaPagamento.PIX, pagamento.getFormaPagamento());
        assertEquals(StatusPagamento.CONFIRMADO, pagamento.getStatus());
        assertEquals(0, new BigDecimal("450.00").compareTo(pagamento.getValor()));
        assertNotNull(pagamento.getCodigoConfirmacao());
    }
}
