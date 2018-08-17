package br.com.conductor.devchallenge.repository;

import br.com.conductor.devchallenge.entity.Pessoa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface responsável por especificar operações padrão relacionadas a entidade {@link Pessoa} com o banco de dados
 */
@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
    Pessoa findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
