package com.hospedagem.service;

import com.hospedagem.exception.RecursoNaoEncontradoException;
import com.hospedagem.model.Cliente;
import com.hospedagem.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    public ClienteService(ClienteRepository repository) { this.repository = repository; }
    public List<Cliente> listar() { return repository.findAll(); }
    public Cliente buscar(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado.")); }
    public Cliente salvar(Cliente cliente) { return repository.save(cliente); }
    public void excluir(Long id) { repository.delete(buscar(id)); }
}
