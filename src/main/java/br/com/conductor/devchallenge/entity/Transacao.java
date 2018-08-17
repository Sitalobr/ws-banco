package br.com.conductor.devchallenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe entidade contendo os dados de Transação
 */
@Entity(name = "transacao")
public class Transacao implements InterfaceEntity<Long> {
    private static final long serialVersionUID = 6819774844811283795L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor", nullable = false, precision = 11, scale = 2)
    private BigDecimal valor;

    @Column(name = "data", nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    @Enumerated
    @Column(name = "tipo", nullable = false)
    private TipoTransacao tipo;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public Transacao() {
    }

    public Transacao(BigDecimal valor, TipoTransacao tipo, Conta conta) {
        this.valor = valor;
        this.tipo = tipo;
        this.conta = conta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public enum TipoTransacao {
        @JsonProperty("Saque") SAQUE,
        @JsonProperty("Depósito") DEPOSITO,
        @JsonProperty("Transferência") TRANSFERENCIA
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Objects.equals(id, transacao.id) &&
                Objects.equals(valor, transacao.valor) &&
                Objects.equals(data, transacao.data) &&
                tipo == transacao.tipo &&
                Objects.equals(conta, transacao.conta);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, valor, data, tipo, conta);
    }
}
