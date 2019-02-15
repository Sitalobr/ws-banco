# Web service gerenciador de contas bancárias (Desafio Conductor de Seleção)

## Desenvolvimento de uma API Rest que realize operações bancárias

- Considerando a entidade conta a seguir:

    | Contas | Tipo |
    |-|-|
    | idConta | Numérico |
    | idPessoa | Numérico |
    | saldo | Monetário |
    | limiteSaqueDiario | Monetário |
    | flagAtivo | Condicional |
    | tipoConta | Numérido |
    | dataCriacao | Data |

- Tabela de transações realizadas na conta:

    | Transacoes | Tipo |
    |-|-|
    | idTransacao | Numérico |
    | idConta | Numérico |
    | valor | Monetário |
    | dataTransacao | Data |

- P.S.: Não é necessário realizar operações com a tabela pessoa, mas é necessária a criação da tabela para mapeamento da relação com a conta e enviar script de criação de pelo menos uma pessoa.

    | Pessoas | Tipo |
    |-|-|
    | idPessoa | Numérico |
    | nome | Texto |
    | cpf | Texto |
    | dataNascimento | Data |

- Escopo mínimo:
    ```
    * Implementar path que realiza a criação de uma conta;
    * Implementar path que realiza operação de depósito em uma conta;
    * Implementar path que realiza operação de consulta de saldo em determinada conta;
    * Implementar path que realiza operação de saque em uma conta;
    * Implementar path que realiza o bloqueio de uma conta;
    * Implementar path que recupera o extrato de transações de uma conta;
    ```

## Instruções para execução do projeto:

   1 - Por se tratar de um projeto Maven, é necessário realizar o download de todas as dependências do projeto a partir do arquivo pom.xml;
   
   2 - Configurar informações de seu banco de dados no arquivo "application.properties" dentro da pasta resource;
      
      2.1 - Trocar URL do datasource, informando também o nome do schema;
      2.2 - Trocar nome de usuário e senha nos campos username e password, respectivamente.
   
   3 - Executar script para criação do schema no banco de dados (arquivo "create-schema" na pasta docs);
   
   4 - Executar o projeto a partir do método main na classe Application.
   
OBS.: Na pasta docs encontra-se um arquivo json para importar no Postman com testes da API.
