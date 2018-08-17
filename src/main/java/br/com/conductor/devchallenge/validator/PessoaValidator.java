package br.com.conductor.devchallenge.validator;

import br.com.conductor.devchallenge.entity.Pessoa;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Classe responsável por determinar como será realizada a validação dos dados ao cadastrar uma {@link Pessoa}
 */
@Component
public class PessoaValidator extends AbstractValidator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Pessoa.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Pessoa pessoa = (Pessoa) o;

        if (this.checkEmptyString(pessoa.getCpf()))
            errors.rejectValue("cpf", "", "CPF não informado");
        if (this.checkEmptyString(pessoa.getNome()))
            errors.rejectValue("nome", "", "Nome não informado");
        if (pessoa.getDataNascimento() == null)
            errors.rejectValue("dataNascimento", "", "Data de nascimento não informada");
    }
}
