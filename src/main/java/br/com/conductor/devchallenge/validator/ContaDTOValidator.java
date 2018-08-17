package br.com.conductor.devchallenge.validator;

import br.com.conductor.devchallenge.dto.ContaDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Classe responsável por determinar como será realizada a validação dos dados ao cadastrar uma {@link br.com.conductor.devchallenge.entity.Conta}
 */
@Component
public class ContaDTOValidator extends AbstractValidator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ContaDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ContaDTO conta = (ContaDTO) o;

        if (conta.getCpf() == null)
            errors.rejectValue("pessoa", "", "CPF da pessoa não informado");
        if (conta.getTipoConta() == null)
            errors.rejectValue("tipoConta", "", "Tipo de conta não informado");
        if (conta.getLimiteSaqueDiario() == null)
            errors.rejectValue("limiteSaqueDiario", "", "Limite de saque diario não informado");
    }
}
