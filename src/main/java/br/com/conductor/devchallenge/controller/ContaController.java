package br.com.conductor.devchallenge.controller;

import br.com.conductor.devchallenge.controller.param.DateTimeParams;
import br.com.conductor.devchallenge.dto.ContaDTO;
import br.com.conductor.devchallenge.entity.Conta;
import br.com.conductor.devchallenge.entity.Transacao;
import br.com.conductor.devchallenge.service.ContaService;
import br.com.conductor.devchallenge.validator.ContaDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Set;

/**
 * Classe controladora responsável por tratar das requisições da entidade Conta
 */
@RestController
@RequestMapping("contas")
public class ContaController implements InterfaceController<Conta> {
    private final ContaService contaService;
    private final ContaDTOValidator contaDTOValidator;

    @Autowired
    public ContaController(ContaService contaService, ContaDTOValidator contaDTOValidator) {
        this.contaService = contaService;
        this.contaDTOValidator = contaDTOValidator;
    }

    @Override
    public ContaService getService() {
        return this.contaService;
    }

    /**
     * Função responsável por pesquisar uma entidade {@link Conta} a partir de seu ID
     * @param id Long contendo o ID da {@link Conta} a ser pesquisada
     * @return Código 200 com os dados da {@link Conta} pesquisada
     */
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.getService().findById(id));
    }

    /**
     * Função responsável por recuperar os dados de todas as Contas cadastradas
     * @return Código 200 com a lista contendo os dados de {@link Conta}
     */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.getService().findAll());
    }

    /**
     * Função responsavel por cadastrar uma entidade {@link Conta}
     * @param entity Objeto contendo os dados da nova {@link Conta}
     * @param bindingResult Objeto {@link BindingResult} responsável por armazenar possíveis erros de validação
     * @return Código 201 retornando no header da requisição o path onde se encontra o novo recurso
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ContaDTO entity, BindingResult bindingResult) {
        this.contaDTOValidator.validate(entity, bindingResult);

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());

        Conta conta = this.getService().registrar(entity);
        return ResponseEntity.created(URI.create("/contas/" + conta.getId())).build();
    }

    /**
     * Função responsável por realizar um depósito na {@link Conta} especificada
     * @param id Long contendo o ID da {@link Conta} a ter um valor depositado
     * @param valor {@link BigDecimal} contendo o valor da quantia a ser depositado
     * @return Código 204
     */
    @PutMapping("/{id:\\d+}/deposito")
    public ResponseEntity<?> depositar(@PathVariable Long id, @RequestParam("valor") BigDecimal valor) {
        this.getService().depositar(id, valor);
        return ResponseEntity.noContent().build();
    }

    /**
     * Função responsável por realizar um saque na {@link Conta} especificada
     * @param id Long contendo o ID da {@link Conta} a ter um valor sacado
     * @param valor {@link BigDecimal} contendo o valor da quantia a ser sacado
     * @return Código 204
     */
    @PutMapping("/{id:\\d+}/saque")
    public ResponseEntity<?> sacar(@PathVariable Long id, @RequestParam("valor") BigDecimal valor) {
        this.getService().sacar(id, valor);
        return ResponseEntity.noContent().build();
    }

    /**
     * Função responsável por consultar a quantia em saldo de uma determinada {@link Conta}
     * @param id Long contendo o ID da {@link Conta} a ser pesquisada
     * @return Código 200 com a informação de saldo
     */
    @GetMapping("/{id:\\d+}/saldo")
    public ResponseEntity<?> consultarSaldo(@PathVariable Long id) {
        BigDecimal saldo = this.getService().findSaldoById(id);
        return ResponseEntity.ok(saldo);
    }

    /**
     * Função responsável por consultar o hitórico de transações (extrato) de uma determinada {@link Conta}
     * @param id Long contendo o ID da {@link Conta} a ser pesquisada
     * @param params Parâmetros de requisição para fins de filtragem de dados, não obrigatórios, contendo dataInicio e dataFim
     * @return Código 200 com a lista de transações de acordo com o filtro especificado
     */
    @GetMapping("/{id:\\d+}/extrato")
    public ResponseEntity<?> consultarExtrato(@PathVariable Long id, DateTimeParams params) {
        Set<Transacao> transacoes = this.getService().findTransacoes(id, params.getDataInicio(), params.getDataFim());
        return ResponseEntity.ok(transacoes);
    }

    /**
     * Função responsável por bloquear uma conta para transações
     * @param id Long contendo o ID da {@link Conta} a ser pesquisada
     * @return Código 204
     */
    @PutMapping("/{id:\\d+}/bloqueio")
    public ResponseEntity<?> bloquear(@PathVariable Long id) {
        this.getService().bloquear(id);
        return ResponseEntity.noContent().build();
    }
}
