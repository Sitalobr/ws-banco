package br.com.conductor.devchallenge.dto;

import br.com.conductor.devchallenge.entity.Conta;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Classe responsável por especificar o escopo de dados para criação de uma nova {@link Conta}
 */
public class ContaDTO implements Serializable {
    private static final long serialVersionUID = -3037449763977537608L;

    private String cpf;
    private Conta.TipoConta tipoConta;
    private BigDecimal limiteSaqueDiario;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Conta.TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(Conta.TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public BigDecimal getLimiteSaqueDiario() {
        return limiteSaqueDiario;
    }

    public void setLimiteSaqueDiario(BigDecimal limiteSaqueDiario) {
        this.limiteSaqueDiario = limiteSaqueDiario;
    }
}
