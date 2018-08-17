package br.com.conductor.devchallenge.service;

import br.com.conductor.devchallenge.entity.Conta;
import br.com.conductor.devchallenge.entity.Transacao;
import br.com.conductor.devchallenge.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

/**
 * Classe responsável por tratar as regras de negócio relacionadas a entidade {@link Transacao}
 */
@Service
public class TransacaoService extends AbstractService<Transacao, Long> {
    private final TransacaoRepository transacaoRepository;

    @Autowired
    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public TransacaoRepository getRepository() {
        return this.transacaoRepository;
    }

    /**
     * Função responsável por registrar uma {@link Transacao};
     * caso o ID especificado seja null, um novo registro é cadastrado;
     * caso o ID exista, o registro existente é atualizado
     * @param entity Objeto contendo os dados necessários para registro de uma {@link Transacao}
     * @return Objeto {@link Transacao} recém-registrado
     */
    public Transacao save(Transacao entity) {
        return this.getRepository().save(entity);
    }

    /**
     * Função responsável por recuperar todas as Transações a partir de uma {@link Conta} específica
     * @param idConta Long contendo o ID da {@link Conta} a ter as transações pesquisadas
     * @return Lista de {@link Transacao}
     */
    public Set<Transacao> findAllByConta(Long idConta) {
        return this.getRepository().findAllByConta_Id(idConta);
    }

    /**
     * Função responsável por recuperar todas as Transações a partir de uma {@link Conta} específica,
     * assim como restrições de data inicial e data final
     * @param idConta Long contendo o ID da {@link Conta} a ter as transações pesquisadas
     * @param initialDate {@link LocalDate} contendo a informação da data inicial da restrição
     * @param finalDate {@link LocalDate} contendo a informação da data final da restrição
     * @return Lista de {@link Transacao}
     */
    public Set<Transacao> findAllByContaAndPeriodo(Long idConta, LocalDate initialDate, LocalDate finalDate) {
        LocalDateTime initialDateTime = initialDate.atStartOfDay();
        LocalDateTime finalDateTime = finalDate.atTime(LocalTime.MAX);
        return this.getRepository().findAllByConta_IdAndDataGreaterThanEqualAndDataLessThanEqual(idConta, initialDateTime, finalDateTime);
    }

    /**
     * Função responsável por recuperar todas as Transações a partir de uma {@link Conta} específica,
     * restringindo pela data inicial
     * @param idConta Long contendo o ID da {@link Conta} a ter as transações pesquisadas
     * @param initialDate {@link LocalDate} contendo a informação da data inicial da restrição
     * @return Lista de {@link Transacao}
     */
    public Set<Transacao> findAllByContaAndDataInicial(Long idConta, LocalDate initialDate) {
        LocalDateTime initialDateTime = initialDate.atStartOfDay();
        return this.getRepository().findAllByConta_IdAndDataGreaterThanEqual(idConta, initialDateTime);
    }

    /**
     * Função responsável por recuperar todas as Transações a partir de uma {@link Conta} específica,
     * restringindo pela data final
     * @param idConta Long contendo o ID da {@link Conta} a ter as transações pesquisadas
     * @param finalDate {@link LocalDate} contendo a informação da data final da restrição
     * @return Lista de {@link Transacao}
     */
    public Set<Transacao> findAllByContaAndDataFinal(Long idConta, LocalDate finalDate) {
        LocalDateTime finalDateTime = finalDate.atTime(LocalTime.MAX);
        return this.getRepository().findAllByConta_IdAndDataLessThanEqual(idConta, finalDateTime);
    }

    /**
     * Função responsável por verificar a possibilidade de realização de saque, de acordo com a regra de negócio
     * que determina uma quantia limite para saque diário
     * @param conta Objeto {@link Conta} a ser verificada
     * @param valorSaque Valor da quantia que se deseja sacar da conta
     * @return true em caso de ser possível a realização do saque, false caso contrário
     */
    public boolean isSaquePermitidoByConta(Conta conta, BigDecimal valorSaque) {
        LocalDateTime dataInicio = LocalDate.now().atStartOfDay();
        LocalDateTime dataFim = LocalDate.now().atTime(LocalTime.MAX);

        Set<Transacao> saquesDeHoje = this.getRepository()
                .findAllByConta_IdAndDataGreaterThanEqualAndDataLessThanEqualAndTipo(conta.getId(), dataInicio, dataFim,
                        Transacao.TipoTransacao.SAQUE);

        BigDecimal somaSaques = BigDecimal.ZERO;
        for (Transacao transacao : saquesDeHoje) {
            somaSaques = somaSaques.add(transacao.getValor());
        }

        return conta.getLimiteSaqueDiario().subtract(somaSaques.add(valorSaque)).doubleValue() >= 0;
    }
}
