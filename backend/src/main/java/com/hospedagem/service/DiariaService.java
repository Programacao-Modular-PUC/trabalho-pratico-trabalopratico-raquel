package com.hospedagem.service;

import com.hospedagem.exception.DataInvalidaException;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class DiariaService {
    public long calcularQuantidadeDiarias(LocalDateTime entrada, LocalDateTime saida) {
        if (entrada == null || saida == null || !saida.isAfter(entrada)) {
            throw new DataInvalidaException("A data de saida deve ser posterior a data de entrada.");
        }
        LocalDateTime inicio = entrada.withHour(12).withMinute(0).withSecond(0).withNano(0);
        if (entrada.toLocalTime().isAfter(java.time.LocalTime.NOON)) {
            inicio = inicio.plusDays(1);
        }
        LocalDateTime fim = saida.withHour(12).withMinute(0).withSecond(0).withNano(0);
        if (saida.toLocalTime().isAfter(java.time.LocalTime.NOON)) {
            fim = fim.plusDays(1);
        }
        long dias = Duration.between(inicio, fim).toDays();
        return Math.max(1, dias);
    }
}
