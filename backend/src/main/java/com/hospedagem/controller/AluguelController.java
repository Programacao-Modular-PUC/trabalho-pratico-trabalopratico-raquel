package com.hospedagem.controller;

import com.hospedagem.model.Aluguel;
import com.hospedagem.service.AluguelService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/alugueis")
@CrossOrigin(origins = "*")
public class AluguelController {
    private final AluguelService service;
    public AluguelController(AluguelService service) { this.service = service; }
    @GetMapping public List<Aluguel> listar() { return service.listar(); }
    @GetMapping("/{id}") public Aluguel buscar(@PathVariable Long id) { return service.buscar(id); }
    @GetMapping("/cliente/{clienteId}") public List<Aluguel> historico(@PathVariable Long clienteId) { return service.historicoPorCliente(clienteId); }
    @PostMapping public Aluguel criar(@RequestParam Long clienteId, @RequestParam Long residenciaId, @RequestParam Long quartoId, @RequestParam(defaultValue = "PADRAO") String tarifa, @RequestBody Aluguel aluguel) {
        return service.criar(clienteId, residenciaId, quartoId, aluguel, tarifa);
    }
    @PatchMapping("/{id}/cancelar") public Aluguel cancelar(@PathVariable Long id) { return service.cancelar(id); }
}
