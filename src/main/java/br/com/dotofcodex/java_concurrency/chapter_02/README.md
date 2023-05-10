# Basic Thread Synchronization - Sincronização Básica de Threads

Neste capítulo, nós iremos cobrir:
- Synchronizing a method - Sincronizando um método;
- Arranging independent attributes in synchronized classes - Organizando atributos independentes em
  classes sincrinizadas;
- Using conditions in synchronized code - Usando condições em código sincronizado;
- Synchronizing a block of code with a Lock - Sincronizando um bloco de código com um bloqueio;
- Synchronizing data access with a read/write locks - Sincronizando o acesso a dados com bloqueios de
  leitura/escrita;
- Modifying Lock fairness - Modificando a imparcialidade do bloqueio;
- Using multiple conditions in a Lock - Usando mútiplas condições em um bloqueio;

## Introdução
Uma das situações mais comuns em Programação Concorrente ocorre quando mais de uma **thread** de execução
compartilha um recurso. Em uma aplicação concorrente, é normal que múltiplas threads leiam ou escrevam o
mesmo dado ou ter acesso ao mesmo arquivo ou conexão de banco de dados. Estes recursos compartilhados podem
provocar situações de erro ou inconsistência de dados e nós temos que implementar mecanismos para evitar
estes erros.

A solução para estes problemas vem com o conceito de **seção crítica**. Uma **seção crítica** é um bloco de
código que acessa um recurso compartilhado e não pode ser executado por mais de uma **thread** ao mesmo
tempo.

Para ajudar programadores a implementar **seções críticas**, a linguagem Java (e a maioria de todas as
linguagens) oferece mecanismos de sincronização. Quando uma **thread** deseja acessar uma **seção crítica**
, ela usa um destes mecanismos de sincronização para descobrir se há outra thread executando a **seção
crítica**. Se não houver, a **thread** entra na **seção crítica**. Caso contrário, a **thread** é suspensa
pelo mecanismo de sincronização até que a **thread** que está executando a **seção crítica** termine.
Quando mais de uma thread está aguardando por uma thread terminar a execução da **seção crítica**, a JVM
escolhe uma delas, e o resto aguarda por sua vez.

Este capítulo apresenta diversas receitas que ensinam como usar os dois mecanismos de sincronização básicos
oferecido pela linguagem Java:
- A palavra reservada **synchronized**;
- A interface **Lock** e suas implementações.
