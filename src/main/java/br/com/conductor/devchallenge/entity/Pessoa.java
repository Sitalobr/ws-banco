package br.com.conductor.devchallenge.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe entidade contendo os dados de Pessoa
 */
@Entity(name = "pessoa")
public class Pessoa implements InterfaceEntity<Long> {
    private static final long serialVersionUID = 2578942674338083085L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    public Pessoa() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(this.id, pessoa.id) &&
                Objects.equals(this.nome, pessoa.nome) &&
                Objects.equals(this.cpf, pessoa.cpf) &&
                Objects.equals(this.dataNascimento, pessoa.dataNascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.nome, this.cpf, this.dataNascimento);
    }
}
