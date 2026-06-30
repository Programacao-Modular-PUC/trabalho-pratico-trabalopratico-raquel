package com.hospedagem.service;

import com.hospedagem.exception.CapacidadeExcedidaException;
import com.hospedagem.exception.QuartoIndisponivelException;
import com.hospedagem.exception.RecursoNaoPermitidoException;
import com.hospedagem.model.*;
import com.hospedagem.pattern.tarifa.GerenciadorTarifas;
import com.hospedagem.repository.AluguelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AluguelServiceTest {
    @Mock private AluguelRepository repository;
    @Mock private ClienteService clienteService;
    @Mock private ResidenciaService residenciaService;
    @Mock private QuartoService quartoService;

    private AluguelService service;

    @BeforeEach
    void setUp() {
        service = new AluguelService(
                repository,
                clienteService,
                residenciaService,
                quartoService,
                new DiariaService(),
                new GerenciadorTarifas()
        );
    }

    @Test
    void deveCriarAluguelComValorFinalCalculado() {
        Cliente cliente = clientePadrao();
        Residencia residencia = residenciaPadrao();
        QuartoIndividual quarto = quartoIndividual(1L, 2, new BigDecimal("100.00"));
        Aluguel aluguel = aluguelPadrao(2, false);

        when(clienteService.buscar(1L)).thenReturn(cliente);
        when(residenciaService.buscar(1L)).thenReturn(residencia);
        when(quartoService.buscar(1L)).thenReturn(quarto);
        when(repository.findByQuartoIdAndDataEntradaLessThanAndDataSaidaGreaterThanAndStatusNot(
                anyLong(), any(), any(), eq(StatusAluguel.CANCELADO))).thenReturn(List.of());
        when(repository.save(any(Aluguel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Aluguel resultado = service.criar(1L, 1L, 1L, aluguel, "PADRAO");

        assertEquals(cliente, resultado.getCliente());
        assertEquals(residencia, resultado.getResidencia());
        assertEquals(quarto, resultado.getQuarto());
        assertEquals(1, resultado.getQuantidadeDiarias());
        assertEquals(StatusAluguel.RESERVADO, resultado.getStatus());
        assertEquals(0, new BigDecimal("125.00").compareTo(resultado.getValorFinal()));
    }

    @Test
    void deveLancarErroQuandoQuartoEstiverIndisponivelNoPeriodo() {
        when(clienteService.buscar(1L)).thenReturn(clientePadrao());
        when(residenciaService.buscar(1L)).thenReturn(residenciaPadrao());
        when(quartoService.buscar(1L)).thenReturn(quartoIndividual(1L, 2, new BigDecimal("100.00")));
        when(repository.findByQuartoIdAndDataEntradaLessThanAndDataSaidaGreaterThanAndStatusNot(
                anyLong(), any(), any(), eq(StatusAluguel.CANCELADO))).thenReturn(List.of(new Aluguel()));

        assertThrows(QuartoIndisponivelException.class, () ->
                service.criar(1L, 1L, 1L, aluguelPadrao(2, false), "PADRAO"));
    }

    @Test
    void deveLancarErroQuandoCapacidadeForExcedida() {
        when(clienteService.buscar(1L)).thenReturn(clientePadrao());
        when(residenciaService.buscar(1L)).thenReturn(residenciaPadrao());
        when(quartoService.buscar(1L)).thenReturn(quartoIndividual(1L, 2, new BigDecimal("100.00")));

        assertThrows(CapacidadeExcedidaException.class, () ->
                service.criar(1L, 1L, 1L, aluguelPadrao(3, false), "PADRAO"));
    }

    @Test
    void deveLancarErroQuandoSolicitarBercoEmQuartoIndividual() {
        when(clienteService.buscar(1L)).thenReturn(clientePadrao());
        when(residenciaService.buscar(1L)).thenReturn(residenciaPadrao());
        when(quartoService.buscar(1L)).thenReturn(quartoIndividual(1L, 2, new BigDecimal("100.00")));

        assertThrows(RecursoNaoPermitidoException.class, () ->
                service.criar(1L, 1L, 1L, aluguelPadrao(2, true), "PADRAO"));
    }

    @Test
    void deveCancelarAluguel() {
        Aluguel aluguel = aluguelPadrao(2, false);
        aluguel.setStatus(StatusAluguel.RESERVADO);

        when(repository.findById(10L)).thenReturn(Optional.of(aluguel));
        when(repository.save(any(Aluguel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Aluguel cancelado = service.cancelar(10L);

        assertEquals(StatusAluguel.CANCELADO, cancelado.getStatus());
    }

    @Test
    void deveListarHistoricoPorCliente() {
        when(repository.findByClienteId(5L)).thenReturn(List.of(new Aluguel(), new Aluguel()));

        List<Aluguel> historico = service.historicoPorCliente(5L);

        assertEquals(2, historico.size());
        verify(repository).findByClienteId(5L);
    }

    private Cliente clientePadrao() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Maria Silva");
        cliente.setCpf("12345678900");
        cliente.setEmail("maria@email.com");
        return cliente;
    }

    private Residencia residenciaPadrao() {
        Residencia residencia = new Residencia();
        residencia.setId(1L);
        residencia.setEndereco("Rua das Praias");
        return residencia;
    }

    private QuartoIndividual quartoIndividual(Long id, int camas, BigDecimal valorBase) {
        QuartoIndividual quarto = new QuartoIndividual();
        quarto.setId(id);
        quarto.setQuantidadeCamasSolteiro(camas);
        quarto.setValorBase(valorBase);
        return quarto;
    }

    private Aluguel aluguelPadrao(int hospedes, boolean berco) {
        Aluguel aluguel = new Aluguel();
        aluguel.setQuantidadeHospedes(hospedes);
        aluguel.setSolicitaBerco(berco);
        aluguel.setDataEntrada(LocalDateTime.of(2026, 6, 20, 14, 0));
        aluguel.setDataSaida(LocalDateTime.of(2026, 6, 21, 10, 0));
        return aluguel;
    }
}
