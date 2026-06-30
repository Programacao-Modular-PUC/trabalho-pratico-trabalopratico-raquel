package com.hospedagem.service;

import com.hospedagem.exception.RecursoNaoEncontradoException;
import com.hospedagem.model.*;
import com.hospedagem.repository.QuartoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuartoService {
    private final QuartoRepository repository;
    private final ResidenciaService residenciaService;
    public QuartoService(QuartoRepository repository, ResidenciaService residenciaService) {
        this.repository = repository;
        this.residenciaService = residenciaService;
    }
    public List<Quarto> listar() { return repository.findAll(); }

    public List<Quarto> filtrarPorTipo(TipoQuarto tipo) {
        return repository.findAll()
                .stream()
                .filter(quarto -> quarto.getTipo() == tipo)
                .collect(Collectors.toList());
    }
    public Quarto buscar(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Quarto nao encontrado.")); }
    public Quarto salvar(Quarto quarto, Long residenciaId) {
        if (residenciaId != null) quarto.setResidencia(residenciaService.buscar(residenciaId));
        return repository.save(quarto);
    }
    public void excluir(Long id) { repository.delete(buscar(id)); }
}
