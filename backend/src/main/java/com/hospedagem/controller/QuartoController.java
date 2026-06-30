package com.hospedagem.controller;

import com.hospedagem.model.*;
import com.hospedagem.service.QuartoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/quartos")
@CrossOrigin(origins = "*")
public class QuartoController {
    private final QuartoService service;
    public QuartoController(QuartoService service) { this.service = service; }
    @GetMapping public List<Quarto> listar() { return service.listar(); }
    @GetMapping("/tipo/{tipo}") public List<Quarto> filtrarPorTipo(@PathVariable TipoQuarto tipo) { return service.filtrarPorTipo(tipo); }
    @GetMapping("/{id}") public Quarto buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping("/individual") public QuartoIndividual criarIndividual(@RequestParam Long residenciaId, @RequestBody QuartoIndividual quarto) { return (QuartoIndividual) service.salvar(quarto, residenciaId); }
    @PostMapping("/duplo") public QuartoDuplo criarDuplo(@RequestParam Long residenciaId, @RequestBody QuartoDuplo quarto) { return (QuartoDuplo) service.salvar(quarto, residenciaId); }
    @PostMapping("/familia") public QuartoFamilia criarFamilia(@RequestParam Long residenciaId, @RequestBody QuartoFamilia quarto) { return (QuartoFamilia) service.salvar(quarto, residenciaId); }
    @DeleteMapping("/{id}") public void excluir(@PathVariable Long id) { service.excluir(id); }
}
