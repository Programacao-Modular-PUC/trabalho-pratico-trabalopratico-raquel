package com.hospedagem.service;

import com.hospedagem.exception.*;
import com.hospedagem.model.*;
import com.hospedagem.pattern.tarifa.GerenciadorTarifas;
import com.hospedagem.repository.AluguelRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AluguelService {
    private final AluguelRepository repository;
    private final ClienteService clienteService;
    private final ResidenciaService residenciaService;
    private final QuartoService quartoService;
    private final DiariaService diariaService;
    private final GerenciadorTarifas gerenciadorTarifas;

    public AluguelService(AluguelRepository repository, ClienteService clienteService, ResidenciaService residenciaService, QuartoService quartoService, DiariaService diariaService, GerenciadorTarifas gerenciadorTarifas) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.residenciaService = residenciaService;
        this.quartoService = quartoService;
        this.diariaService = diariaService;
        this.gerenciadorTarifas = gerenciadorTarifas;
    }

    public List<Aluguel> listar() { return repository.findAll(); }

    public Aluguel buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Aluguel nao encontrado."));
    }

    public List<Aluguel> historicoPorCliente(Long clienteId) { return repository.findByClienteId(clienteId); }

    public Aluguel criar(Long clienteId, Long residenciaId, Long quartoId, Aluguel aluguel, String estrategiaTarifa) {
        Cliente cliente = clienteService.buscar(clienteId);
        Residencia residencia = residenciaService.buscar(residenciaId);
        Quarto quarto = quartoService.buscar(quartoId);

        validarRegras(aluguel, quarto);
        validarDisponibilidade(quarto.getId(), aluguel);

        long diarias = diariaService.calcularQuantidadeDiarias(aluguel.getDataEntrada(), aluguel.getDataSaida());
        BigDecimal valorDiaria = quarto.calcularDiaria(aluguel.getQuantidadeHospedes(), aluguel.isSolicitaBerco());
        valorDiaria = gerenciadorTarifas.calcular(estrategiaTarifa, valorDiaria);

        aluguel.setCliente(cliente);
        aluguel.setResidencia(residencia);
        aluguel.setQuarto(quarto);
        aluguel.setQuantidadeDiarias(diarias);
        aluguel.setValorFinal(valorDiaria.multiply(BigDecimal.valueOf(diarias)));
        aluguel.setStatus(StatusAluguel.RESERVADO);
        return repository.save(aluguel);
    }

    public Aluguel cancelar(Long id) {
        Aluguel aluguel = buscar(id);
        aluguel.setStatus(StatusAluguel.CANCELADO);
        return repository.save(aluguel);
    }

    private void validarRegras(Aluguel aluguel, Quarto quarto) {
        if (!quarto.isAtivo()) throw new RecursoNaoPermitidoException("Quarto inativo.");
        if (aluguel.getQuantidadeHospedes() > quarto.calcularCapacidadeMaxima()) {
            throw new CapacidadeExcedidaException("Quantidade de hospedes excede a capacidade do quarto.");
        }
        if (aluguel.isSolicitaBerco() && quarto instanceof QuartoIndividual) {
            throw new RecursoNaoPermitidoException("Quarto individual nao permite berco.");
        }
        if (aluguel.isSolicitaBerco() && quarto instanceof QuartoDuplo duplo && !duplo.isPermiteBerco()) {
            throw new RecursoNaoPermitidoException("Este quarto duplo nao permite berco.");
        }
    }

    private void validarDisponibilidade(Long quartoId, Aluguel aluguel) {
        boolean existeConflito = !repository.findByQuartoIdAndDataEntradaLessThanAndDataSaidaGreaterThanAndStatusNot(
                quartoId, aluguel.getDataSaida(), aluguel.getDataEntrada(), StatusAluguel.CANCELADO).isEmpty();
        if (existeConflito) {
            throw new QuartoIndisponivelException("Quarto indisponivel no periodo informado.");
        }
    }
}
