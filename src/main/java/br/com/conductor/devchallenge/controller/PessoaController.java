package br.com.conductor.devchallenge.controller;

import br.com.conductor.devchallenge.entity.Pessoa;
import br.com.conductor.devchallenge.service.PessoaService;
import br.com.conductor.devchallenge.validator.PessoaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

/**
 * Classe controladora responsável por tratar das requisições da entidade Pessoa
 */
@RestController
@RequestMapping("/pessoas")
public class PessoaController implements InterfaceController<Pessoa> {
    private final PessoaService pessoaService;
    private final PessoaValidator pessoaValidator;

    @Autowired
    public PessoaController(PessoaService pessoaService, PessoaValidator pessoaValidator) {
        this.pessoaService = pessoaService;
        this.pessoaValidator = pessoaValidator;
    }

    @Override
    public PessoaService getService() {
        return this.pessoaService;
    }

    /**
     * Função responsável por pesquisar uma entidade {@link Pessoa} a partir de seu ID
     * @param id Long contendo o ID da {@link Pessoa} a ser pesquisada
     * @return Código 200 com os dados da {@link Pessoa} pesquisada
     */
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.getService().findById(id));
    }

    /**
     * Função responsável por recuperar os dados de todas as Pessoas cadastradas
     * @return Código 200 com a lista contendo os dados de {@link Pessoa}
     */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.getService().findAll());
    }

    /**
     * Função responsavel por cadastrar uma entidade {@link Pessoa}
     * @param entity Objeto contendo os dados da nova {@link Pessoa}
     * @param bindingResult Objeto {@link BindingResult} responsável por armazenar possíveis erros de validação
     * @return Código 201 retornando no header da requisição o path onde se encontra o novo recurso
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Pessoa entity, BindingResult bindingResult) {
        this.pessoaValidator.validate(entity, bindingResult);

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());

        Pessoa pessoa = this.getService().create(entity);
        return ResponseEntity.created(URI.create("/pessoas/" + pessoa.getId())).build();
    }

    /**
     * Função responsável por atualizar o registro de uma entidade {@link Pessoa}
     * @param id Long contendo o ID da {@link Pessoa} a ser atualizada
     * @param entity Objeto {@link Pessoa} contendo os dados a serem atualizados
     * @return Código 204
     */
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Pessoa entity) {
        this.getService().update(id, entity);
        return ResponseEntity.noContent().build();
    }

    /**
     * Função responsável por excluir o registro de uma entidade {@link Pessoa}
     * @param id Long contendo o ID da {@link Pessoa} a ser excluída
     * @return Código 204
     */
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.getService().delete(id);
        return ResponseEntity.noContent().build();
    }
}
