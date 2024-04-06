# Boogle

### VISÃO GERAL DO SISTEMA
Boogle é um aplicativo desenvolvido para gerenciamento e pesquisa de livros, possuindo como base a utilização da API do Google Livros, uma poderosa ferramenta que permite incorporar recursos do Google Livros diretamente no sistema. A API Google Books oferece uma abordagem programática para realizar uma ampla gama de operações que normalmente seriam realizadas interativamente no site do Google Livros. Com a capacidade de trazer conteúdo de qualidade para o nosso aplicativo, o Boogle se propõe a facilitar a busca, organização e gerenciamento de livros para os usuários, tornando a experiência de descoberta de obras mais eficiente e personalizada.

### Requisitos Funcionais 

| ID   | Descrição                                           | Versão | Data   | Responsável | Regras de Negócio | Status      |
|------|-----------------------------------------------------|--------|--------|-------------|-------------------|-------------|
| RF001| O sistema deve manter usuários                     | 1.0    | 02/03  |             | RN001             | Em andamento|
| RF002| O sistema deve permitir login anônimo              | 1.0    | 02/03  |             | RN001             | Em andamento|
| RF003| O sistema deve permitir buscar por títulos de livros| 1.0    | 02/03  |             | RN004             | Em andamento|
| RF004| O sistema deve listar os livros encontrados        | 1.0    | 02/03  |             |                   | Em andamento|
| RF005| O sistema deve permitir favoritar/desfavoritar um livro| 1.0 | 02/03  |             | RN002             | Em andamento|
| RF006| O sistema deve permitir acessar a lista de favoritos| 1.0  | 02/03  |             | RN003             | Em andamento|
| RF007| O sistema deverá gerenciar uma conexão com a API de Livros da Google| 1.0 | 02/03|              |                   | Em andamento|
| RF008| Criar uma categoria (uma listagem de sugestões)   | 1.0    | 02/03  |             |                   | Em andamento|
| RF009| O sistema deve usar Firebase para a persistência de dados| 1.0| 03/04  |             |                   | Em andamento|
| RF010| O sistema deve receber notificações usando Firebase Cloud Message| 1.0| 03/04  |             |                   | Em andamento|

### Requisitos Não Funcionais 

| ID   | Categoria  | Descrição                                                                 | Versão | Data   | Status      |
|------|------------|---------------------------------------------------------------------------|--------|--------|-------------|
| RNF001| Segurança  | A encriptação de senha do usuário deve ser realizada usando um algoritmo de hash seguro, seguindo as melhores práticas de segurança.| 1.0| 03/03  | Em andamento|
| RNF002| Portabilidade  | O sistema deve operar em celular.                                           | 1.0| 03/03  | Em andamento|
| RNF003| Usabilidade  | A linguagem do sistema deve ser de fácil compreensão.                        | 1.0| 03/03  | Em andamento|
| RNF004| Interoperabilidade  | O sistema deverá integrar com a API de livros da Google.                      | 1.0| 03/03  | Em andamento|
| RNF005| Manutenibilidade  | O uso do padrão MVVM é obrigatório para facilitar a manutenção e a extensibilidade do aplicativo.| 1.1| 03/04  | Em andamento|

### Regras de Negócio 

| ID   | Descrição                                           | Versão | Status      |
|------|-----------------------------------------------------|--------|-------------|
| RN001| O cadastro de usuário deve conter nome, e-mail e senha| 1.0  | Em andamento|
| RN002| O usuário só poderá favoritar um livro se estiver logado| 1.0 | Em andamento|
| RN003| O usuário só poderá acessar a lista de favoritos se estiver logado| 1.0 | Em andamento|
| RN004| O campo de busca aceitará buscar tanto em letras maiúsculas quanto minúsculas| 1.0 | Em andamento|

### Figma

https://www.figma.com/file/LdOqmhJkTbGZduiL06a5dB/Boogle?type=design&node-id=0%3A1&mode=design&t=soeXmJQ12OG6hcIt-1
