package br.com.conductor.devchallenge.service;

import br.com.conductor.devchallenge.entity.Pessoa;
import br.com.conductor.devchallenge.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Classe responsável por tratar as regras de negócio relacionadas a entidade {@link Pessoa}
 */
@Service
public class PessoaService extends AbstractService<Pessoa, Long> {
    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public PessoaRepository getRepository() {
        return this.pessoaRepository;
    }

    public Iterable<Pessoa> findAll() {
        return this.getRepository().findAll();
    }

    /**
     * Função responsável por registrar uma {@link Pessoa}; caso o ID especificado seja null, um novo registro é cadastrado;
     * caso o ID exista, o registro existente é atualizado
     * @param entity Objeto {@link Pessoa} contendo os dados a serem registrados
     * @return Objeto {@link Pessoa} recém-registrado
     */
    public Pessoa save(Pessoa entity) {
        return this.getRepository().save(entity);
    }

    /**
     * Função responsável por cadastrar um novo registro de {@link Pessoa}
     * @param request Objeto contendo os dados da {@link Pessoa}
     * @return Objeto {@link Pessoa} recém-cadastrado
     */
    public Pessoa create(Pessoa request) {
        if (this.getRepository().existsByCpf(request.getCpf()))
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "O CPF informado já existe.");

        return this.save(request);
    }

    /**
     * Função responsável por atualizar um registro de {@link Pessoa}
     * @param id Long contendo o ID da {@link Pessoa} a ser atualizada
     * @param request Objeto contendo os dados da {@link Pessoa}
     * @return Objeto {@link Pessoa} recém-atualizado
     */
    public Pessoa update(Long id, Pessoa request) {
        Pessoa pessoa = this.findById(id);

        pessoa.setCpf(request.getCpf());
        pessoa.setNome(request.getNome());
        pessoa.setDataNascimento(request.getDataNascimento());

        return this.save(pessoa);
    }

    /**
     * Função responsável por excluir uma {@link Pessoa}
     * @param id Long contendo o ID da {@link Pessoa} a ser excluída
     */
    public void delete(Long id) {
        this.getRepository().deleteById(id);
    }

    /**
     * Função responsável por recuperar os dados de uma {@link Pessoa} a partir do CPF
     * @param cpf String contendo o CPF da {@link Pessoa} a ser pesquisada
     * @return Objeto {@link Pessoa}
     */
    public Pessoa findByCpf(String cpf) {
        Pessoa response = this.getRepository().findByCpf(cpf);

        if (response == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "A pessoa solicitada não foi encontrada a partir do CPF informado.");
        return response;
    }
}
