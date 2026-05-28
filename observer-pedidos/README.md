# Padrão Observer em um Sistema de Pedidos

Projeto acadêmico em Java com Maven que demonstra o padrão de projeto comportamental **Observer** usando um sistema simples de pedidos de uma loja virtual.

## Objetivo

Demonstrar, de forma clara e executável pelo terminal, como um objeto observado pode notificar automaticamente vários interessados quando seu estado muda.

Neste projeto, a mudança de status de um pedido dispara notificações para e-mail, SMS, dashboard administrativo e histórico.

## Contexto do problema

Em uma loja virtual, um pedido passa por diferentes status, como `EM_PROCESSAMENTO`, `PAGO`, `ENVIADO` e `ENTREGUE`.

Quando essa informação muda, várias partes do sistema precisam reagir:

- o cliente pode receber um e-mail;
- o cliente pode receber um SMS;
- o painel administrativo precisa ser atualizado;
- a alteração deve ficar registrada no histórico.

Sem o padrão Observer, a classe `Pedido` poderia ficar acoplada diretamente a cada uma dessas classes. Com o Observer, `Pedido` conhece apenas a interface `Observer`, mantendo o código mais flexível e didático.

## Padrão Observer

O Observer define uma relação um-para-muitos entre objetos. Quando o estado do objeto principal muda, todos os objetos cadastrados como observadores são avisados automaticamente.

No projeto, o ponto central está no método `setStatus(...)` da classe `Pedido`. Se o novo status for diferente do status atual, o pedido atualiza seu estado e chama `notifyObservers(...)`.

## Papéis no projeto

| Papel no padrão | Implementação no projeto | Responsabilidade |
| --- | --- | --- |
| `Subject` | `subject.Subject` | Define os métodos para adicionar, remover e notificar observadores. |
| `ConcreteSubject` | `model.Pedido` | Mantém o estado do pedido e notifica os observadores quando o status muda. |
| `Observer` | `observer.Observer` | Define o contrato `update(...)` para objetos interessados nas mudanças. |
| `ConcreteObserver` | `EmailNotifier` | Simula envio de e-mail ao cliente. |
| `ConcreteObserver` | `SmsNotifier` | Simula envio de SMS ao cliente. |
| `ConcreteObserver` | `DashboardUpdater` | Simula atualização do painel administrativo. |
| `ConcreteObserver` | `HistoryLogger` | Registra e exibe o histórico de mudanças de status. |

## Estrutura de pastas

```text
observer-java/
├── .gitignore
└── observer-pedidos/
    ├── pom.xml
    ├── README.md
    └── src/
        └── main/
            └── java/
                ├── app/
                │   └── Main.java
                ├── model/
                │   ├── Pedido.java
                │   └── StatusPedido.java
                ├── observer/
                │   ├── DashboardUpdater.java
                │   ├── EmailNotifier.java
                │   ├── HistoryLogger.java
                │   ├── Observer.java
                │   └── SmsNotifier.java
                └── subject/
                    └── Subject.java
```

## Como compilar e executar

Requisitos:

- JDK 21 ou superior;
- Apache Maven instalado e disponível no terminal pelo comando `mvn`.

A partir da raiz do repositório, entre na pasta do projeto:

```bash
cd observer-pedidos
```

Compile o projeto:

```bash
mvn compile
```

Execute a aplicação:

```bash
mvn exec:java
```

Se quiser gerar um `.jar` executável:

```bash
mvn package
java -jar target/observer-pedidos-1.0.0.jar
```

## Exemplo de saída esperada

```text
=== 1. Criacao do pedido ===
Pedido criado: #1001 - Cliente: Ana Silva - Status inicial: EM_PROCESSAMENTO

=== 2. Cadastro dos observadores ===
EmailNotifier cadastrado.
SmsNotifier cadastrado.
DashboardUpdater cadastrado.
HistoryLogger cadastrado.

=== 3. Alteracoes de status ===

Alterando status para PAGO:
[E-MAIL] Ola, Ana Silva! Seu pedido #1001 mudou de EM_PROCESSAMENTO para PAGO.
[SMS] Ana Silva, atualizacao do pedido #1001: PAGO.
[DASHBOARD] Pedido #1001 agora esta com status PAGO.
[HISTORICO] Pedido #1001: EM_PROCESSAMENTO -> PAGO

Alterando status para ENVIADO:
[E-MAIL] Ola, Ana Silva! Seu pedido #1001 mudou de PAGO para ENVIADO.
[SMS] Ana Silva, atualizacao do pedido #1001: ENVIADO.
[DASHBOARD] Pedido #1001 agora esta com status ENVIADO.
[HISTORICO] Pedido #1001: PAGO -> ENVIADO

=== 4. Tentativa de repetir o mesmo status ===
Status atual: ENVIADO
Tentando alterar novamente para ENVIADO.
Nenhuma notificacao foi exibida porque o status nao mudou.

=== 5. Remocao de um observador ===
SmsNotifier removido. Ele nao recebera as proximas notificacoes.

=== 6. Nova alteracao apos a remocao ===

Alterando status para ENTREGUE:
[E-MAIL] Ola, Ana Silva! Seu pedido #1001 mudou de ENVIADO para ENTREGUE.
[DASHBOARD] Pedido #1001 agora esta com status ENTREGUE.
[HISTORICO] Pedido #1001: ENVIADO -> ENTREGUE

=== 7. Historico registrado pelo HistoryLogger ===

=== Historico completo ===
1. Pedido #1001: EM_PROCESSAMENTO -> PAGO
2. Pedido #1001: PAGO -> ENVIADO
3. Pedido #1001: ENVIADO -> ENTREGUE
```

## Benefícios do padrão no projeto

- Reduz o acoplamento entre `Pedido` e as classes que reagem a mudanças.
- Facilita adicionar novos observadores sem alterar a regra principal do pedido.
- Centraliza a mudança de estado em `Pedido`.
- Deixa explícita a relação entre Subject, Observer, ConcreteSubject e ConcreteObservers.
- Mantém o projeto simples, adequado para estudo e apresentação acadêmica.

## Possíveis melhorias futuras

- Criar novos observadores, como `EstoqueUpdater` ou `PagamentoAuditor`.
- Adicionar testes automatizados simples para validar as regras de notificação.
- Permitir novos status, como `CANCELADO` ou `AGUARDANDO_PAGAMENTO`.

## Conclusão

O projeto demonstra o padrão Observer de maneira objetiva: `Pedido` representa o objeto observado, os observadores se cadastram nele e todos são notificados automaticamente quando o status muda.

A implementação continua simples, mas agora usa Maven para padronizar a compilação e execução. Ela inclui validações básicas, proteção contra observadores duplicados, histórico imutável para consulta externa e uma demonstração mais completa para apresentação em sala.
