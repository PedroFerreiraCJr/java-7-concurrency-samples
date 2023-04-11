# Recipe 08 - Processing uncontrolled exceptions in a thread
Há dois tipos de exceções em Java:
 - Exceções Verificadas: Estas exceções devem ser especificadas na cláusula **throws** de um método ou 
capturada dentro dele. Por exemplo, **IOException** ou **ClassNotFoundException**;
 - Exceções Não Verificadas: Estas exceções não tem que ser especificadas ou capturadas. Por exemplo,
**NumberFormatException**.

Quando uma exceção verificada é lançada dentro do método **run()** de um objeto **Thread**, nós temos que
capturá-la e tratá-la, porque o método **run()** não aceita a cláusula **throws**. Quando uma exceção não
verificada é lançada dentro do método **run()** de um objeto **Thread**, o comportamento padrão é escrever
a **stack trace** (rastreamento da pilha) no console e terminar a execução do programa.

Felizmente, o Java nos fornece um mecanismo para capturar e tratar exceções não verificadas lançadas em um
objeto **Thread** para evitar o término do programa.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Primeiro de tudo, nós temos que implementar uma classe para tratar as exceções não verificadas. Esta 
classe deve implementar a interface **UncaughtExceptionHandler** e implementar o método **uncaughtException**
declarado na interface. Em nosso caso, chame esta classe de **ExceptionHandler** e faça o método escrever 
infomações sobre a **Exception** na **Thread** que a lançou. A seguir o código;
```java
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An exception has been captured%n");
        System.out.printf("Thread: %s%n", t.getId());
        System.out.printf("Exception: %s: %s%n", e.getClass().
                getName(),e.getMessage());
        System.out.printf("Stack Trace: %n");
        e.printStackTrace(System.out);
        System.out.printf("Thread status: %s%n", t.getState());
    }
}
```

 2. Agora, implemente a classe que lança uma exceção não verificada. Chame esta classe de **Task**, especifique
que esta classe implementa a interface **Runnable**, implemente o método **run()**, e force a exceção, por
exemplo, para converter um valor **java.lang.String** em um valor inteiro;
```java
public class Task implements Runnable {
    @Override
    public void run() {
        int number = Integer.parseInt("valor não numérico");
    }
}
```

 3. Agora, implemente a classe **main** do exemplo. Implemente uma classe chamada **Main** com um método
**main()**;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 4. Crie um objeto **Task** e uma **Thread** para rodá-lo. Configure o tratador de exceções não verificadas
usando o método **setUncaughtExceptionHandler()** e inicie a execução da **Thread**;
```java
Task task = new Task();
Thread thread = new Thread(task);
thread.setUncaughtExceptionHandler(new ExceptionHandler());
thread.start();
```

 5. Rode o exemplo e veja os resultados.

## Como ele funciona (How it works...)
Na imagem a seguir, você pode ver os resultados da execução do exemplo. A exceção é lançada e capturada pelo
**handler** (tratador) que escreve a informação no console sobre a **Exception** e **Thread** que a lançou.
Segue a imagem:

![saída do programa](https://raw.githubusercontent.com/PedroFerreiraCJr/traducao-java-7-concurrency/master/images/recipe_08.png)

Quando uma exceção é lançada em uma **thread** e não é capturada (ela tem que ser uma exceção não verificada),
 a JVM checa se a **thread** tem um tratador de exceção não capturada configurada pelo método correspondente.
Se ela tem, a JVM invoca o método com o objeto **Thread** e a **Exception** como argumentos.

Se a thread não tem um tratador de exceções não capturadas, a JVM imprime a **stack trace** (rastreamento 
da pilha) no console e termina o programa.

## Há mais (There is more...)
A classe **Thread** tem outro método relacionado ao processo de exceções não capturadas. É o método estático
**setDefaultUncaughtExceptionHandler()** que estabelece um tratador de exceções para todos os objetos 
**Thread** na aplicação.

Quando uma exceção não capturada é lançada em uma **Thread**, a **JVM** procura por 3 tratadores possíveis 
para essa exceção.

Primeiro, ela procura pelo tratador de exceções não capturadas para o objeto **Thread** como nós aprendemos 
nesta receita. Se este tratador não existir, então a **JVM** procura pelo tratador de exceções não capturadas 
para o **ThreadGroup** do objeto **Thread** como foi explicado na receita: **Processing uncontrolled 
exceptions in a group of a threads**. Se este tratador não existir, a **JVM** procura pelo tratador de exceções
não capturadas padrão, como nós aprendemos nesta receita.

Se nenhum destes tratadores existir, a **JVM** escreve o rastreamento da pilha da exceção no console e termina
o programa.

## Veja também (See also)
- Veja a receita, Processando exceções não controladas em um grupo de threads, no capítulo 1, Gerenciamento
de Threads.
