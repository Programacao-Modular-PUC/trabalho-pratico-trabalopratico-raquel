package com.hospedagem.controller;

import com.hospedagem.model.Residencia;
import com.hospedagem.service.ResidenciaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/residencias")
@CrossOrigin(origins = "*")
public class ResidenciaController {
    private final ResidenciaService service;
    public ResidenciaController(ResidenciaService service) { this.service = service; }
    @GetMapping public List<Residencia> listar() { return service.listar(); }
    @GetMapping("/{id}") public Residencia buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping public Residencia salvar(@RequestBody @Valid Residencia residencia) { return service.salvar(residencia); }
    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { service.excluir(id); }
}
