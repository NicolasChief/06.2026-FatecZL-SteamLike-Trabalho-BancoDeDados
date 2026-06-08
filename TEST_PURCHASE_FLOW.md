# Teste do Sistema de Compra e Saldo

## Objetivo
Validar que o sistema de saldo funciona em todas as ações do usuário, e que ao adquirir um jogo, ele aparece na biblioteca da aba da esquerda em BuscaUI.

## Fluxo de Teste Manual

### 1. Login
- Usuario: `cliente`
- Senha: `123`
- Saldo esperado: 500 (padrão)

### 2. Navegar para BuscaUI
- Deve mostrar jogo "A" com preço 100
- Biblioteca (esquerda) deve estar vazia (não há compras ainda)

### 3. Primeira Compra
- **Ação**: Double-click no jogo "A" para abrir PedidoUI
- **Esperado**:
  - Preço: R$ 100.00
  - Saldo atual: R$ 500.00
  - Após clicar "Realizar Compra":
    - Alerta de sucesso com novo saldo: R$ 400.00
    - Biblioteca deve mostrar "A" na esquerda
    - Console deve mostrar logs de atualização do banco

### 4. Segunda Compra (mesmo jogo)
- **Ação**: Tentar comprar "A" novamente
- **Esperado**: Mostrar alerta de jogo já comprado (se verificação existir) ou permitir múltiplas compras com novo saldo: R$ 300.00

### 5. Teste de Saldo Insuficiente
- Se houver jogo com preço > saldo restante:
  - Deve mostrar alerta: "Saldo insuficiente! Preço: R$ XXX, Seu saldo: R$ YYY"
  - Compra não deve ser processada

## Verificações Implementadas ✅
- [x] BuscaUI carrega jogos do banco ao iniciar
- [x] PedidoUI recebe BuscaUC para recarregar dados
- [x] Saldo desce corretamente: `novoSaldo = saldoUsuario - precoJogo`
- [x] Saldo é persistido no banco: `UsuarioDAOImpl.atualizarSaldo()`
- [x] Jogo marcado como adquirido: `jogoFinal.setStatusAquicicao(true)`
- [x] Biblioteca se recarrega após compra: `buscaUCFinal.atualizarTabela()`

## Próximas Melhorias
- [ ] Persistir statusAquicicao em banco (usar tabela Jogo_Biblioteca)
- [ ] Impedir dupla compra do mesmo jogo
- [ ] Sincronizar biblioteca com banco após compra
- [ ] Testar com app restart para verificar persistência

## Status Atual
✅ Sistema de compra pronto para teste manual
