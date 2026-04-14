# Padrão Observer em Sistema de Pedidos

Projeto acadêmico simples em Java puro para demonstrar o padrão comportamental **Observer** em um cenário de loja virtual.

## Tema

**Padrão de Projeto Comportamental Observer: aplicação em um sistema de atualização de status de pedidos**

## Ideia do projeto

Em uma loja virtual, quando o status de um pedido muda, várias partes do sistema precisam reagir automaticamente:

- enviar e-mail ao cliente;
- enviar SMS ao cliente;
- atualizar o dashboard administrativo;
- registrar a alteração no histórico.

Neste projeto, a classe `Pedido` representa o **Subject**. Ela mantém uma lista de observadores e notifica todos quando seu status é alterado.

Os observadores concretos são:

- `EmailNotifier`
- `SmsNotifier`
- `DashboardUpdater`
- `HistoryLogger`

## Onde o Observer aparece

- `Subject`: interface com os métodos para adicionar, remover e notificar observadores.
- `Observer`: interface com o método `update(...)`, chamado automaticamente quando o pedido muda de status.
- `Pedido`: classe concreta que implementa `Subject`.
- Observadores concretos: classes que implementam `Observer` e reagem de formas diferentes.

O coração da demonstração está no método `setStatus(...)` da classe `Pedido`: quando o status muda, ele chama `notifyObserversAboutStatusChange(...)` e todos os observadores cadastrados são avisados.

Esse desenho reduz o acoplamento porque `Pedido` não conhece os detalhes internos de cada observador. Ele apenas chama o método definido pela interface.

## Estrutura de pastas

```text
observer-pedidos/
├── README.md
└── src/
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

Na pasta `observer-pedidos`, execute:

```bash
javac -d out src/app/Main.java src/model/*.java src/observer/*.java src/subject/*.java
java -cp out app.Main
```

## Saída esperada

A saída pode variar apenas em pequenos detalhes de formatação, mas deve seguir esta ideia:

```text
Pedido criado: #1001 - Cliente: Ana Silva - Status inicial: EM_PROCESSAMENTO

--- Alterando status para PAGO ---
[E-MAIL] Ola, Ana Silva! Seu pedido #1001 mudou de EM_PROCESSAMENTO para PAGO.
[SMS] Ana Silva, atualizacao do pedido #1001: PAGO.
[DASHBOARD] Pedido #1001 agora esta com status PAGO.
[HISTORICO] Pedido #1001: EM_PROCESSAMENTO -> PAGO

--- Alterando status para ENVIADO ---
[E-MAIL] Ola, Ana Silva! Seu pedido #1001 mudou de PAGO para ENVIADO.
[SMS] Ana Silva, atualizacao do pedido #1001: ENVIADO.
[DASHBOARD] Pedido #1001 agora esta com status ENVIADO.
[HISTORICO] Pedido #1001: PAGO -> ENVIADO

--- Alterando status para ENTREGUE ---
[E-MAIL] Ola, Ana Silva! Seu pedido #1001 mudou de ENVIADO para ENTREGUE.
[SMS] Ana Silva, atualizacao do pedido #1001: ENTREGUE.
[DASHBOARD] Pedido #1001 agora esta com status ENTREGUE.
[HISTORICO] Pedido #1001: ENVIADO -> ENTREGUE

=== Historico completo ===
1. Pedido #1001: EM_PROCESSAMENTO -> PAGO
2. Pedido #1001: PAGO -> ENVIADO
3. Pedido #1001: ENVIADO -> ENTREGUE
```

## Conclusão

A solução representa corretamente o padrão Observer porque existe uma relação um-para-muitos entre `Pedido` e seus observadores. Quando o estado do pedido muda, todos os objetos cadastrados são notificados automaticamente, sem que `Pedido` dependa das classes concretas de notificação.
