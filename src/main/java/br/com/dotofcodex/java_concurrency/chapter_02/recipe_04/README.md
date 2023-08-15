# Recipe 04 - Synchronizing a block of code with a Lock
O Java fornece outro mecanismo para sincronização de blocos de código. Este é um mecanismo mais poderoso e
flexível do que a palavra-chave **synchronized**. Ele é baseado na interface **Lock** e classes que 
implementam ela (**ReentrantLock**). Este mecanismo apresenta algumas vantagens, como as que se seguem:
 - Permite a estruturação de blocos sincronizados de forma mais flexível. Com a palavra-chave
**synchronized**, você precisa obter e liberar o controle sobre o bloco de código sincronizado de forma
estruturada. As interfaces **Lock** permitem obter estruturas mas complexas para implementar sua seção
crítica;
 - As interface **Lock** fornecem funcionalidades adicionais sobre a palavra-chave **synchronized**. Uma
das novas funcionalidades é implementada pelo método **tryLock()**. Este método tenta obter o controle
sobre o bloqueio e se ele não puder, porque ela está sendo usada por outra **thread**, ela retorna o 
bloqueio. Com a palavra-chave **synchronized, quando uma **thread** (A) tenta executar um bloco de código
sincronizado, se houver outra **thread** executando ele, a **thread** (A) é suspensa até que a **thread** 
(B) termine a execução do bloco de código sincronizado. Com **locks**, você pode executar o método 
**tryLock()**. Este método retorna um valor **Boolean** indicando se existe outra **thread** rodando o 
código protegido pela trava (bloqueio, ou lock);
 - As interfaces **Lock** permitem a separação de operações de escrita e leitura tendo múltiplos 
leitores, e um único modificador;
 - As interface **Lock** oferecem uma melhor performance com relação a palavra-chave **synchronized**.

Nesta receita, você irá aprender como usar **locks** para sincronizar um bloco de código e criar uma seção
crítica usando a interface **Lock** e a classe **ReentrantLock** que implementa ela, implementando um 
programa que simula uma fila de impressão (print queue).


## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. 
```java

```



## Como ele funciona (How it works...)


## Nota (Note)


## Há mais (There is more...)


## Veja também (See also)
- Organizando atributos independentes em classes sincronizadas, no capítulo 2, Sincronização básica de
threads.
