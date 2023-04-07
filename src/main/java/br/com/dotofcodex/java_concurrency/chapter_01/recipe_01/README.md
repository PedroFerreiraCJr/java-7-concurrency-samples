# Recipe 01 - Creating and running a thread
Nesta receita, nós iremos aprender a como criar e rodar uma thread em uma aplicação Java. Como todo
elemento em uma linguagem de programação, threads são **objetos**. Nós temos duas formas de criar uma
thread em Java:
 - Estendendo a classe Thread e sobrescrevendo o método **run()**;
 - Construindo uma classe que implementa a interface **Runnable** e então criando um objeto da classe
Thread passando o objeto **Runnable** como parâmetro;

Nesta receita, nós iremos usar a segunda abordagem para criar um simples programa que cria e roda 10
threads. Cada thread calcula e imprime a tabela de multiplicação de um número entre 1 e 10.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **Calculator** que implementa a interface **Runnable**.
```java
public class Calculator implements Runnable {}
```

 2. Declare um atributo **private int** chamado **number** e implemente o construtor da classe que
inicicaliza o seu valor.
```java
private int number;
public Calculator(int number) {
    this.number = number;
}
```

 3. Implemente o método **run()**. Este método irá executar as instruções da thread que nós estamos
criando, então esse método irá calcular a tabela de multiplicação deste número.
```java
@Override
public void run() {
    for (int i=1; i<=10; i++) {
        System.out.printf("%s: %d * %d = %d%n", Thread.currentThread()
            .getName(), number, i, i*number);
    }
}
```

 4. Agora, implemente a classe principal da aplicação. Crie uma classe chamada **Main** que contém
o método **main()**.
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 5. Dentro do método **main()**, crie um loop **for** com 10 iterações. Dentro do loop, crie um
objeto da classe **Calculator**, um objeto da classe Thread, passe o objeto **Calculator** como
um parâmetro, e chame o método **start()** do objeto thread.
```java
for (int i=1; i<=10; i++) {
    Calculator calculator = new Calculator(i);
    Thread thread = new Thread(calculator);
    thread.start();
}
```

 6. Rode o programa e veja as diferentes thread trabalham em paralelo.

## Como ele funciona (How it works...)
A seguinte tela mostra parte da saída do programa. Nós podemos ver que todas as threads que nós
criamos, rodam em paralelo para fazer o seu trabalho, como mostrado na seguinte tela:

![saída do programa](https://raw.githubusercontent.com/PedroFerreiraCJr/traducao-java-7-concurrency/master/images/recipe_01.png)

Todo programa Java tem no mínimo uma thread de execução. Quando você roda o programa, a JVM roda
a thread de execução que chama o método **main()** do programa.

Quando nós chamamos o método **start()** do objeto Thread, nós estamos criando outra thread de
execução. Nosso programa irá ter tantas threads de execução quantas forem feitas a chamada ao
método **start()**.

Um programa Java termina quando todas as suas threads terminam (mais precisamente, quando todas
as thread non-daemon terminam). Se a thread inicial (aquela que executa o método **main()**)
termina, o restante das threads irão continuar com a execução delas até que elas terminem. Se
uma das threads usar a instrução **System.exit()** para terminar a execução do programa, todas
as threads terminam as sua execuções.

Criando um objeto da classe Thread não cria um nova thread de execução. E também, chamando o
método **run()** de uma classe que implementa a interface **Runnable** não cria uma nova thread
de execução. Somente chamando o método **start()** que cria uma nova thread de execução.

## Há mais (There is more...)
Como nós mencionamos na introdução desta receita, há outra forma de criar uma nova thread de
execução. Você pode implementar uma classe que estenda a classe **Thread** e sobrescrever o
método **run()** desta classe. Então, você pode criar um objeto desta classe e chamar o 
método **start()** para ter uma nova thread de execução.

## Veja também (See also)
 - Veja a receita, Criando threads através de uma factory, no capítulo 1, Gerenciamento de
Threads.
