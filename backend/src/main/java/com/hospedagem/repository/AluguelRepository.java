package com.hospedagem.repository;

import com.hospedagem.model.Aluguel;
import com.hospedagem.model.StatusAluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
    List<Aluguel> findByClienteId(Long clienteId);
    List<Aluguel> findByQuartoIdAndStatusNot(Long quartoId, StatusAluguel status);
    List<Aluguel> findByQuartoIdAndDataEntradaLessThanAndDataSaidaGreaterThanAndStatusNot(Long quartoId, LocalDateTime dataSaida, LocalDateTime dataEntrada, StatusAluguel status);
}
