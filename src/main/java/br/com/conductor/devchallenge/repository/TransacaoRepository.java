package br.com.conductor.devchallenge.repository;

import br.com.conductor.devchallenge.entity.Transacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Interface responsável por especificar operações padrão relacionadas a entidade {@link Transacao} com o banco de dados
 */
@Repository
public interface TransacaoRepository extends CrudRepository<Transacao, Long> {
    Set<Transacao> findAllByConta_Id(Long idConta);
    Set<Transacao> findAllByConta_IdAndDataGreaterThanEqualAndDataLessThanEqual(Long idConta, LocalDateTime start, LocalDateTime end);
    Set<Transacao> findAllByConta_IdAndDataGreaterThanEqual(Long idConta, LocalDateTime start);
    Set<Transacao> findAllByConta_IdAndDataLessThanEqual(Long idConta, LocalDateTime end);
    Set<Transacao> findAllByConta_IdAndDataGreaterThanEqualAndDataLessThanEqualAndTipo(Long idConta, LocalDateTime start, LocalDateTime end, Transacao.TipoTransacao tipo);
}
