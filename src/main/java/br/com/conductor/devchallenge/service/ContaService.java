package br.com.conductor.devchallenge.service;

import br.com.conductor.devchallenge.dto.ContaDTO;
import br.com.conductor.devchallenge.entity.Conta;
import br.com.conductor.devchallenge.entity.Pessoa;
import br.com.conductor.devchallenge.entity.Transacao;
import br.com.conductor.devchallenge.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Classe responsável por tratar as regras de negócio relacionadas a entidade {@link Conta}
 */
@Service
public class ContaService extends AbstractService<Conta, Long> {
    private final ContaRepository contaRepository;
    private final PessoaService pessoaService;
    private final TransacaoService transacaoService;

    @Autowired
    public ContaService(ContaRepository contaRepository, PessoaService pessoaService, TransacaoService transacaoService) {
        this.contaRepository = contaRepository;
        this.pessoaService = pessoaService;
        this.transacaoService = transacaoService;
    }

    @Override
    public ContaRepository getRepository() {
        return this.contaRepository;
    }

    /**
     * Função responsável por recuperar os dados de todas as Contas registradas
     * @return Lista de {@link Conta}
     */
    public Iterable<Conta> findAll() {
        return this.getRepository().findAll();
    }

    /**
     * Função responsável por registrar uma {@link Conta}; caso o ID especificado seja null, um novo registro é cadastrado;
     * caso o ID exista, o registro existente é atualizado
     * @param entity Objeto {@link Conta} contendo os dados da Conta a serem registrados
     * @return Objeto {@link Conta} recém-registrado
     */
    public Conta save(Conta entity) {
        return this.getRepository().save(entity);
    }

    /**
     * Função responsável por cadastrar uma nova {@link Conta}
     * @param contaDTO Objeto {@link ContaDTO} contendo os dados necessários para cadastrar uma nova {@link Conta}
     * @return Objeto {@link Conta} recém-cadastrada
     */
    public Conta registrar(ContaDTO contaDTO) {
        Pessoa pessoa = this.pessoaService.findByCpf(contaDTO.getCpf());

        Conta conta = new Conta();
        conta.setAtivo(true);
        conta.setDataCriacao(LocalDateTime.now());
        conta.setLimiteSaqueDiario(contaDTO.getLimiteSaqueDiario());
        conta.setSaldo(BigDecimal.valueOf(0.0));
        conta.setTipo(contaDTO.getTipoConta());
        conta.setPessoa(pessoa);

        return this.save(conta);
    }

    /**
     * Função responsável por depositar um determinado valor em uma {@link Conta}
     * @param idConta Long contendo o ID da {@link Conta} a ter uma quantia depositada
     * @param valor {@link BigDecimal} contendo o valor da quantia a ser depositada
     */
    @Transactional
    public void depositar(Long idConta, BigDecimal valor) {
        Conta conta = this.findById(idConta);

        if (!conta.getAtivo())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Não é possível depositar quantias em uma conta bloqueada.");
        if (valor.doubleValue() <= 0.0)
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Não é possível depositar um valor menor ou igual a zero.");

        this.transacaoService.save(new Transacao(valor, Transacao.TipoTransacao.DEPOSITO, conta));
        conta.setSaldo(conta.getSaldo().add(valor));
        this.save(conta);
    }

    /**
     * Função responsável por sacar um determinado valor de uma {@link Conta}
     * @param idConta Long contendo o ID da {@link Conta} a ter uma quantia sacada
     * @param valor {@link BigDecimal} contendo o valor da quantia a ser sacada
     */
    @Transactional
    public void sacar(Long idConta, BigDecimal valor) {
        Conta conta = this.findById(idConta);

        if (!conta.getAtivo())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Não é possível sacar quantias em uma conta bloqueada.");
        if (!this.transacaoService.isSaquePermitidoByConta(conta, valor))
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Este valor extrapola a quantia máxima de saques diários desta conta.");
        if (valor.doubleValue() <= 0.0)
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Não é possível sacar um valor menor ou igual a zero.");
        if (valor.doubleValue() > conta.getSaldo().doubleValue())
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "O valor que está tentando sacar é maior que o saldo atual.");

        this.transacaoService.save(new Transacao(valor, Transacao.TipoTransacao.SAQUE, conta));
        conta.setSaldo(conta.getSaldo().subtract(valor));
        this.save(conta);
    }

    /**
     * Função responsável por bloquear uma {@link Conta}
     * @param idConta Long contendo o ID da {@link Conta} a ser bloqueada
     */
    public void bloquear(Long idConta) {
        Conta conta = this.findById(idConta);

        if (!conta.getAtivo())
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "A conta informada já está bloqueada.");

        conta.setAtivo(false);
        this.save(conta);
    }

    /**
     * Função responsável por recuperar a informação do saldo atual de uma {@link Conta}
     * @param idConta Long contendo o ID de uma {@link Conta}
     * @return Valor do saldo atual
     */
    public BigDecimal findSaldoById(Long idConta) {
        Conta conta = this.findById(idConta);
        return conta.getSaldo();
    }

    /**
     * Função responsável por recuperar o histórico de transações de uma {@link Conta};
     * podendo ser filtrado, opcionalmente por data inicial e data final
     * @param idConta Long contendo o ID da {@link Conta} a ter seu extrato pesquisado
     * @param initialDate Data de início do filtro por período
     * @param finalDate Data final do filtro por período
     * @return Lista de {@link Transacao}
     */
    public Set<Transacao> findTransacoes(Long idConta, LocalDate initialDate, LocalDate finalDate) {
        if (initialDate == null && finalDate == null)
            return this.transacaoService.findAllByConta(idConta);
        else if (initialDate != null && finalDate == null)
            return this.transacaoService.findAllByContaAndDataInicial(idConta, initialDate);
        else if (initialDate == null)
            return this.transacaoService.findAllByContaAndDataFinal(idConta, finalDate);
        else
            return this.transacaoService.findAllByContaAndPeriodo(idConta, initialDate, finalDate);
    }
}
