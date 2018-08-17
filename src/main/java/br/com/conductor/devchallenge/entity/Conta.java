package br.com.conductor.devchallenge.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe entidade contendo os dados de Conta
 */
@Entity(name = "conta")
public class Conta implements InterfaceEntity<Long> {
    private static final long serialVersionUID = -9013636525338200302L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "saldo", nullable = false, precision = 11, scale = 2)
    private BigDecimal saldo;

    @Column(name = "limite_saque_diario", nullable = false, precision = 11, scale = 2)
    private BigDecimal limiteSaqueDiario;

    @Column(name = "ativo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean ativo;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Enumerated
    @Column(name = "tipo", nullable = false)
    private TipoConta tipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Conta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getLimiteSaqueDiario() {
        return limiteSaqueDiario;
    }

    public void setLimiteSaqueDiario(BigDecimal limiteSaqueDiario) {
        this.limiteSaqueDiario = limiteSaqueDiario;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public enum TipoConta {
        @JsonProperty("Corrente") CORRENTE,
        @JsonProperty("Poupança") POUPANCA,
        @JsonProperty("Universitária") UNIVERSITARIA,
        @JsonProperty("Salário") SALARIO,
        @JsonProperty("Internacional") INTERNACIONAL
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return Objects.equals(id, conta.id) &&
                Objects.equals(saldo, conta.saldo) &&
                Objects.equals(limiteSaqueDiario, conta.limiteSaqueDiario) &&
                Objects.equals(ativo, conta.ativo) &&
                Objects.equals(dataCriacao, conta.dataCriacao) &&
                tipo == conta.tipo &&
                Objects.equals(pessoa, conta.pessoa);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, saldo, limiteSaqueDiario, ativo, dataCriacao, tipo, pessoa);
    }
}
