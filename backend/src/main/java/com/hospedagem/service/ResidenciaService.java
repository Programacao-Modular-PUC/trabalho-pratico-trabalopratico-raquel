package com.hospedagem.service;

import com.hospedagem.exception.RecursoNaoEncontradoException;
import com.hospedagem.model.Residencia;
import com.hospedagem.repository.ResidenciaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResidenciaService {
    private final ResidenciaRepository repository;
    public ResidenciaService(ResidenciaRepository repository) { this.repository = repository; }
    public List<Residencia> listar() { return repository.findAll(); }
    public Residencia buscar(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Residencia nao encontrada.")); }
    public Residencia salvar(Residencia residencia) { return repository.save(residencia); }
    public void excluir(Long id) { repository.delete(buscar(id)); }
}
