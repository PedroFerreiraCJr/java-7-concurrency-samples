# Recipe 02 - Getting and setting thread information
A classe Thread salva alguns atributos de informação que podem nos ajudar a identificar a thread,
saber o seu status, or controlar a sua prioridade. Esses atributos são:
 - ID: Este atributo armazena um identificar único para cada *Thread*;
 - Name: Este atributo armazena o nome da *Thread*;
 - Priority: Este atributo armazena a prioridade do objeto *Thread*. Thread podem ter uma
 prioridade entre 1 e 10, onde 1 é a menor prioridade e 10 é a mais alta prioridade.
Não é recomendado alterar a prioridade das threads, mas é uma possíbilidade que você pode usar se você desejar.
 - Status: Este atributo armazena o status da Thread. Em Java uma Thread pode estar em um dos
seis estados: *new*, *runnable*, *blocked*, *waiting*, *time waiting*, ou *terminated*.

Nesta receita, nós iremos desenvolver um programa que estabelece o nome e prioridade para 10 threads e então
mostra informações sobre seus status até que elas terminem. As threads irão calcular a tabela de multiplicação
de um número.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **Calculator** e especifique que ela implementa a interface Runnable;
```java
public class Calculator implements Runnable {}
```

 2. Declare um atributo **int private** chamado **number**, e implemente o construtor da classe
que inicializa este atributo;
```java
private int number;
public Calculator(int number) {
    this.number = number;
}
```

 3. Implemente o método **run()**. Este método irá executar as instruções da thread que nós estamos criando,
então este método irá calcular e imprimir a tabela de multiplicação deste número;
```java
@Override
public void run() {
    for (int i=1; i<=10; i++) {
        System.out.printf("%s: %d * %d = %d%n", Thread.currentThread()
            .getName(), number, i, i*number);
    }
}
```

 4. Agora, implemente a classe main deste exemplo. Crie uma classe chamada **Main** e implemente o
método **main()**;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 5. Crie um array de **10 Threads** e um array de **10 Thread.State** para armazenar as threads que vamos executar
e os seus status;
```java
Thread[] threads = new Threads[10];
Thread.State[] status = new Thread.State[10];
```

 6. Crie **10 objetos** da classe **Calculator**, cada um inicializa com um número diferente, e
10 threads para executá-las. Configure o valor da prioridade de 5 delas para o valor máximo e configure a prioridade
do resto para o valor mínimo;
```java
for (int i=0; i<10; i++) {
    threads[i] = new Thread(new Calculator(i));
    if (i % 2 == 0) {
        threads[i].setPriority(Thread.MAX_PRIORITY);
    }
    else {
        threads[i].setPriority(Thread.MIN_PRIORITY);
    }
    threads[i].setName("Thread " + i);
}
```

 7. Crie um objeto **PrintWriter** para escrever para um arquivo a evolução do status das threads;
```java
try (FileWriter file = new FileWriter(".\\data\\log.txt");
        PrintWriter pw = new PrintWriter(file);) {
}
```

 8. Escreve para este arquivo o status das 10 threads. Agora, ele se torna **NEW**;
```java
for (int i=0; i<10; i++) {
    pw.println("Main: Status of Thread" + i + " : " + threads[i].getState());
    status[i] = threads[i].getState();
}
```

 9. Inicie a execução das **10 threads**;
```java
for (int i=0; i<10; i++) {
    threads[i].start();
}
```

 10. Até que as **10 threads** terminem, nós iremos checar o status delas. Se nós detectarmos uma alteração
no status de alguma thread, nós escrevemos ele para o arquivo;
```java
boolean finish = false;
while (!finish) {
    for (int i=0; i<10; i++) {
        if (threads[i].getState() != status[i]) {
        writeThreadInfo(pw, threads[i], status[i]);
        status[i] = threads[i].getState();
        }
    }
    
    finish = true;
    for (int i=0; i<10; i++) {
        finish = finish && threads[i].getState() == Thread.State.TERMINATED;
    }
}
```

 11. Implemente o método **writeThreadInfo()** que escrever o **ID**, **name**, **prioridade**
, **status antigo**, e o **novo status** da Thread;
```java
private static void writeThreadInfo(PrintWriter pw, Thread thread, State state) {
    pw.printf("Main : Id %d - %s%n", thread.getId(), thread.getName());
    pw.printf("Main : Priority: %d%n", thread.getPriority());
    pw.printf("Main : Old State: %s%n", state);
    pw.printf("Main : New State: %s%n", thread.getState());
    pw.printf("Main : ************************************%n");
}
```

 12. Execute o exemplo e abra o arquivo **log.txt** para ver a evolução das **10 threads**.

## Como ele funciona (How it works...)
A seguinte screenshot mostra as linhas do arquivo **log.txt** na execução deste programa.
Neste arquivo, nós podemos ver que as threads com mais alta prioridade terminar antes
daquelas com baixa prioridade. Nós podemos ver a evolução dos status de cada thread.

![saída do programa](https://raw.githubusercontent.com/PedroFerreiraCJr/traducao-java-7-concurrency/master/images/recipe_02.png)

O programa mostrado no console é a tabela de multiplicação calculada pelas threads e a evolução
dos status de diferentes threads no arquivo **log.txt**. Deste forma, você consegue melhor visualizar
a evolução das threads.
A classe Thread tem atributos para armazenar todas as informações de uma thread. A JVM usa a prioridade
das threads para selecionar aquela que usa a CPU em cada momento e atualiza o status de cada thread 
de acordo com sua situação.
Se você não especifica o nome da thread, a JVM automaticamente atribui um com um formato, Thread-XX
onde XX é um número. Você não pode modificar o **ID** ou status de uma thread.
A classe Thread não implementa os métodos **setId()** e **setStatus()** para permitir a sua motificação.

## Há mais (There is more...)
In this recipe, you learned how to access the information attributes using a Thread object.
But you can also access these attributes from an implementation of the Runnable interface.
You can use the static method currentThread() of the Thread class to access the
Thread object that is running the Runnable object.
You have to take into account that the setPriority() method can throw an
IllegalArgumentException exception if you try to establish a priority that isn't
between one and 10.

Nesta receita, você aprendeu a como acessar os atributos de informação usando o objeto Thread.
Mas você também pode acessar esses atributos de uma implementação da interface Runnable.
Você pode usar o método estático **currentThread()** da classe Thread para acessar o objeto Thread
que está executando o objeto **Runnable**.
Você tem que levar em consideração que o método **setPriority()** pode lançar 
uma exceção **IllegarArgumentException** se você tentar estabelcer a priridade que não está nos intervalos
 de 1 e 10.

## Veja também (See also)
- Veja a receita, Interrompendo uma thread, no capítulo 1, Gerenciamento de Threads.
