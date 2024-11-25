# Automação de Testes - Tela de Login OrangeHRM

Este projeto é uma automação de testes para a tela de login do sistema **OrangeHRM**, utilizando **Java 17**, **Selenium WebDriver** e **JUnit 5**. O objetivo é validar o comportamento da aplicação em diferentes cenários de uso, conforme descrito no plano de testes.  

## Tecnologias e Ferramentas Utilizadas

- **Java 17**: Linguagem de programação.
- **JUnit 5**: Framework de testes.
- **Selenium WebDriver**: Automação de interações no navegador.
- **ChromeDriver**: Driver para execução dos testes no navegador Google Chrome.
- **Maven**: Gerenciador de dependências e build.
- **Extent Reporter**: Biblioteca para reports HTML.
- **ATUTestRecorder**: Biblioteca adicional para gravação de vídeos dos testes.

---

## Plano de Testes

### 1. Objetivo
Garantir que a funcionalidade de login do OrangeHRM opere corretamente para diferentes cenários de uso, validando entradas, mensagens de erro e comportamentos.

### 2. Escopo
- Validação de campos obrigatórios.
- Autenticação com credenciais válidas e inválidas.
- Comportamento do sistema em tentativas excessivas de login falho.

### 3. Critérios de Aceite
- **Acesso autorizado**: Redirecionar à página inicial com credenciais corretas.
- **Mensagens de erro claras**: Informar o usuário sobre erros específicos.
- **Links externos e recuperação de senha**: Garantir o correto redirecionamento.

### 4. Casos de Teste

| ID    | Cenário                                  | Resultado Esperado                                               |
|-------|------------------------------------------|-------------------------------------------------------------------|
| CT01  | Login bem-sucedido                      | Redireciona à página inicial.                                    |
| CT02  | Login com senha incorreta               | Exibe a mensagem “Invalid credentials”.                          |
| CT03  | Login com usuário não registrado        | Exibe a mensagem “Invalid credentials”.                          |
| CT04  | Campos obrigatórios não preenchidos     | Mensagem “Required” e bordas em vermelho para campos vazios.     |
| CT05  | Acessar “Forgot your password?”         | Redireciona à página “Reset Password”.                           |
| CT06  | Acessar LinkedIn                        | Redireciona à página do LinkedIn da OrangeHRM em nova guia.      |
| CT07  | Acessar YouTube                         | Redireciona à página do YouTube da OrangeHRM em nova guia.       |
| CT08  | Acessar Facebook                        | Redireciona à página do Facebook da OrangeHRM em nova guia.      |
| CT09  | Acessar Twitter                         | Redireciona à página do Twitter da OrangeHRM em nova guia.       |
| CT10  | Acessar “OrangeHRM.Inc”                 | Redireciona ao site oficial em nova guia.                        |

---

## Configuração do Ambiente

### Pré-requisitos
1. **Java 17** instalado.
2. **Maven** configurado no sistema.
3. **ChromeDriver** compatível com a versão do navegador Chrome.

### Instalação
1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd seu-repositorio
   ```
3. Configure as dependências do Maven:
   ```bash
   mvn install
   ```
4. Certifique-se de adicionar manualmente a biblioteca **ATUTestRecorder** ao projeto, pois ela não é gerenciada pelo Maven. 
Link do tutorial: 
<a>https://medium.com/@alanpaulooficial/como-gravar-evid%C3%AAncias-de-automa%C3%A7%C3%A3o-de-testes-em-v%C3%ADdeos-utilizando-o-selenium-webdriver-c12a0dab8452</a>

---

## Execução dos Testes

1. Execute os testes usando o Maven:
   ```bash
   mvn test
   ```
2. Os evidências dos testes serão salvas na pasta `evidences`.

---

## Autor

Desenvolvido por **João Pedro Soares de Brito**.

---

## Considerações Finais

Este projeto serve como exemplo prático de automação de testes para validar funcionalidades de login. A biblioteca **ATUTestRecorder** adiciona um diferencial ao projeto, permitindo a gravação dos testes para análises futuras.
