# Recipe 11 - Processing uncontrolled exceptions in a group of threads
Um aspecto muito importante em toda linguagem de programação é o mecanismo que fornece gerenciamento
de situações de erro em sua aplicação. A linguagem Java, assim como a maioria das linguagens de programação
modernas, implementa um mecanismo baseado em exceções para gerenciar situações de erro. Ela fornece muitas
classes diferentes para representar diferentes erros. Essas exceções são lançadas por uma classe Java, quando
uma situação de erro é detectada. Você pode também usar essas exceções, ou implementar suas próprias
exceções, para gerenciar erros produzidos por suas próprias classes.

A linguagem Java também fornece um mecanismo para capturar e processar estas exceções. Há exceções que devem
ser capturadas ou relançadas usando a cláusula **throws** de um método. Essas exceções são chamadas de
exceções verificadas. Há exceções que não precisam ser especificadas ou capturadas. Estas são as exceções
não verificadas.

Na receita, **Controlling the interruption of a Thread**, você aprendeu como usar um método genérico para
processar todas as exceções não cápturadas que forem lançadas em um objeto **Thread**.

Outra possibilidade é estabelecer um método que cáptura todas as exceções não tratadas lançadas por qualquer
objeto **Thread** de um objeto da classe **ThreadGroup**.

Nesta receita, nós iremos aprender a configurar este tratador usando um exemplo.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Primeiro, nós temos que estender a classe **ThreadGroup** criando uma classe chamada **MyThreadGroup** que
que é derivada de **ThreadGroup**. Nós temos que declarar um construtor com um parâmetro, porque a classe
**ThreadGroup** não tem um construtor sem este parâmetro;
```java
public class MyThreadGroup extends ThreadGroup {
    public MyThreadGroup(String name) {
        super(name);
    }
}
```

 2. Sobrescreva o método **uncaughtException()**. Este método é chamado quando uma exceção é lançada em uma
das **threads** da classe **ThreadGroup**. Neste caso, este método irá escrever no console informações sobre
a exceção e **Thread** que lançou ela e interromper o restante das outras threads na classe **ThreadGroup**;
```java
@Override
public void uncaughtException(Thread t, Throwable e) {
    System.out.printf("The thread %s has thrown an Exception%n", t.getId());
    e.printStackTrace(System.out);
    System.out.printf("Terminating the rest of the Threads%n");
    interrupt();
}
```

 3. Crie uma classe chamada **Task** e especifique que ela implementa a interface **Runnable**;
```java
public class Task implements Runnable {
    
}
```

 4. Implemente o método **run()**. Neste caso, ele irá provocar uma exceção **ArithmeticException**. Para
isto, nós iremos dividir **1000** entre números aleatórios até que o gerador aleatório gere zero e a exceção
seja lançada;
```java
@Override
public void run() {
    int result = 0;
    Random random = new Random(Thread.currentThread().getId());
    while (true) {
        result = 1_000 / ((int) random.nextDouble() * 1_000);
        System.out.printf("%s : %f%n", Thread.currentThread().getId(), result);
        if (Thread.currentThread().isInterrupted()) {
            System.out.printf("%d : Interrupted%n", Thread.currentThread().getId());
            return;
        }
    }
}
```

 5. Agora, nós iremos implementar a classe **main** do exemplo criando uma classe chamada **Main** e
implementando o método **main()**;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 6. Crie um objeto da classe **MyThreadGroup**;
```java
MyThreadGroup threadGroup = new MyThreadGroup("MyThreadGroup");
```

 7. Crie um objeto da classe **Task**;
```java
Task task = new Task();
```

 8. Crie dois objetos **Thread** com esta **Task** e as inicialize;
```java
for (int i = 0; i < 2; i++) {
    Thread t = new Thread(threadGroup, task);
    t.start();
}
```

 9. Rode o exemplo e veja os resultados.

## Como ele funciona (How it works...)
Quando você roda o exemplo, você vê que um dos objetos **Thread** lança uma exceção e a outra **Thread**
será interrompida.

Quando uma exceção não capturada é lançada no objeto **Thread**, a JVM procura por três possiveis tratadores
para esta exceção.

Primeiro, ela procura por um tratador de exceções não capturadas da **thread**, como foi explicado na receita
**Processing uncontrolled exceptions in a Thread**. Se este tratador não existir, então a JVM procura por um
tratador de exceções não capturadas para a classe **ThreadGroup** da **thread**, como nós aprendemos nesta
receita. Se este método não existir, a JVM procura pelo tratador de exceções não controladas **padrão**, como
foi explicado na receita **Processing uncontrolled exceptions in a Thread**.

Se nenhum destes tratadores existir, a JVM escreve o rastreamento da pilha da exceção no console e termina o
programa.

## Veja também (See also)
- Veja a receita, **Processing uncontrolled exceptions in a thread, no capítulo 1, Gerenciamento
de Threads.
