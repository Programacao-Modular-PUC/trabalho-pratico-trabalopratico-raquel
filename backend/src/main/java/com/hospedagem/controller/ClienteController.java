package com.hospedagem.controller;

import com.hospedagem.model.Cliente;
import com.hospedagem.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    private final ClienteService service;
    public ClienteController(ClienteService service) { this.service = service; }
    @GetMapping public List<Cliente> listar() { return service.listar(); }
    @GetMapping("/{id}") public Cliente buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping public Cliente salvar(@RequestBody @Valid Cliente cliente) { return service.salvar(cliente); }
    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { service.excluir(id); }
}
