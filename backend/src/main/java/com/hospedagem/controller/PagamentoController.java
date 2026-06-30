package com.hospedagem.controller;

import com.hospedagem.model.FormaPagamento;
import com.hospedagem.model.Pagamento;
import com.hospedagem.service.PagamentoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin(origins = "*")
public class PagamentoController {
    private final PagamentoService service;
    public PagamentoController(PagamentoService service) { this.service = service; }
    @GetMapping public List<Pagamento> listar() { return service.listar(); }
    @PostMapping("/aluguel/{aluguelId}") public Pagamento pagar(@PathVariable Long aluguelId, @RequestParam FormaPagamento formaPagamento) {
        return service.pagar(aluguelId, formaPagamento);
    }
}
