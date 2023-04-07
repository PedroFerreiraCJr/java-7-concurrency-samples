# Recipe 03 - Interrupting a Thread
Um programa Java com mais de uma Thread de execução somente termina quando a execução de todas
as suas threads terminam (mais especificamente, quando todas as suas threads não-daemon terminam
a sua execução ou quando uma das threads usa o método **System.exit()**). Muitas vezes, você precisa
terminar a thread, porque você deseja terminar o programa, ou quando um usuário do programa deseja
cancelar a execução da tarefa que o objeto Thread está executando.

O Java fornece o mecanismo de interrupção para indicar a uma thread que nós desejamos terminá-la.
Uma peculiaridade deste mecanismo é que a Thread tem que checar se ela foi interrompida ou não, e
ela pode decidir responder a solicitação de finalização ou não. A Thread pode ignorá-la e continuar
a sua execução.

Neste receita, nós iremos desenvolver um programa que cria uma Thread e, após cinco segundos, iremos
forçar a sua interrupção através do mecanismo de interrupção.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga esses passos para implementar o exemplo.
 1. Crie uma classe chamada **PrimeGenerator** que extenda a classe **Thread**;
```java
public class PrimeGenerator extends Thread {}
```

 2. Sobrescreva o método **run()** incluindo um loop irá rodar indefinidamente. Neste
loop, nós iremos processar números consecutivos começando em um (1). Para cada número,
nós iremos calcular se ele é um número primo e, neste caso, nós iremos escrever ele para o console;
```java
@Override
public void run() {
    long number = 1L;
    while (true) {
        if (isPrime(number)) {
            System.out.printf("Number %d is Prime", number);
        }
    }
}
```

 3. Após processar um número, checa se a thread foi interrompida através da chamada do método 
**isInterrupted()**. Se este método retornar **true**, nós escrevemos uma mensagem e terminamos a execução
da thread;
```java
if (isInterrupted()) {
    System.out.printf("The Prime Generator has been Interrupted");
    return;
}
number++;
```

 4. Implemente o método **isPrime()**. Ele retorna um valor booleano indicando se o número
que ele recebeu como um parâmetro é um número primo (true) ou não (false);
```java
private boolean isPrime(long number) {
    if (number <= 2) {
        return true;
    }
    for (long i = 2; i<number; i++) {
        if (number % i == 0) {
            return false;
        }
    }
    return true;
}
```

 5. Agora, implemente a classe **main** do exemplo através da implementação de uma classe chamada **Main**
e implemente o método **main()**;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 6. Crie e chame o método **start()** um objeto da classe **PrimeGenerator**;
```java
Thread task = new PrimeGenerator();
task.start();
```

 7. Aguarde por cinco segundos e interrompa a thread **PrimeGenerator**;
```java
try {
    Thread.sleep(5_000);
} catch(InterruptedException e) {
    e.printStackTrace();
}
task.interrupt();
```

 8. Rode o exemplo e veja os resultados.

## Como ele funciona (How it works...)
A seguinte screenshot mostra o resultado da execução do exemplo anterior. Nós podemos ver
como a thread **PrimeGenerator** escreve as mensagens e termina sua execução quando ela
detecta que foi interrompida.

![saída do programa](https://raw.githubusercontent.com/PedroFerreiraCJr/traducao-java-7-concurrency/master/images/recipe_03.png)

A classe Thread tem um atributo que armazena um valor booleano indicando se a thread foi interrompida ou não.
Quando você chama o método **interrupt()** de uma thread, você está setando este atributo para true. O método 
**isInterrupted()** somente retorna o valor deste atributo.

## Há mais (There is more...)
A classe Thread tem outro método para checar se a Thread foi interrompida ou não. Ele é um método estático, 
**interrupted()** que checa se a execução da thread corrente foi interrompida ou não.

## Nota (Note)
Há uma diferença importante entre os métodos **isInterrupted()** e **interrupted()**. O primeiro destes não
altera o valor do atributo **interrupted**, mas o segundo destes configura o valor para **false**. Como o
**interrupted()** é um método estático, a utilização do método **isInterrupted()** é recomendado.

Como eu mencionei anteriormente, a Thread pode ignorar a sua interrupção, mas este não é o comportamento
esperado.
