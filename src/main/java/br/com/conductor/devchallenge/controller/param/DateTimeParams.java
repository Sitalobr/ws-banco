package br.com.conductor.devchallenge.controller.param;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Classe responsável por determinar parâmetros de requisição para filtragem do extrato
 */
public class DateTimeParams {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataInicio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataFim;

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
}
