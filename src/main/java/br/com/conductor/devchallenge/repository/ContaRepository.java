package br.com.conductor.devchallenge.repository;

import br.com.conductor.devchallenge.entity.Conta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface responsável por especificar operações padrão relacionadas a entidade {@link Conta} com o banco de dados
 */
@Repository
public interface ContaRepository extends CrudRepository<Conta, Long> {
}
