# Recipe 06 - Waiting for the finalization of a thread
Em algumas situações, nós teremos que esperar pela finalização de uma thread. Por exemplo, nós talvez
tenhamos um programa que irá inicializar os recursos que ele precisa antes de proceder com o restante
da execução. Nós podemos rodar as tarefas de inicialização como um thread e aguardar por sua finalização
antes de continuar com o resto do programa.

Para este propósito, nós podemos usar o método **join()** da classe **Thread**. Quando nós invocamos este
método usando um objeto thread, ele suspende a execução da thread que o invocou até que o objeto chamado
termine sua execução.

Nesta receita, nós iremos aprender a usar este método com um exemplo de inicialização.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **DataSourceLoader** e especifique que ela implementa a interface **Runnable**;
```java
public class DataSourceLoader implements Runnable {}
```

 2. Implemente o método **run()**. Ele escreve uma mensagem para indicar que ele começou sua execução, 
suspende por 4 segundos, e escreve outra mensagem para indicar que ele terminou a sua execução;
```java
@Override
public void run() {
    System.out.printf("Beginning data sources loading: %s%n", new Date());
    try {
        TimeUnit.SECONDS.sleep(4);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.printf("Data sources loading has finished: %s%n", new Date());
}
```

 3. Crie uma classe chamada **NetworkConnectionsLoader** e especifique que ela implementa a interface 
**Runnable**. Implemente o método **run()**. Ele irá ser igual ao método **run()** da classe 
**DataSourceLoader**, mas ele irá suspender por 6 segundos;
```java
public class NetworkConnectionsLoader implements Runnable {
    @Override
    public void run() {
        System.out.printf("Beginning network connection: %s%n", new Date());
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Connection has finished: %s%n", new Date());
    }
}
```
 4. Agora, crie uma classe chamada **Main** que contém o método **main()**;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 5. Crie um objeto da classe **DataSourceLoader** e uma **Thread** para rodá-lo;
```java
DataSourceLoader dsLoader = new DataSourceLoader();
Thread thread1 = new Thread(dsLoader, "DataSourceThread");
```

 6.Crie um objeto da classe **DataSourceLoader** e uma **Thread** para rodá-lo;
```java
NetworkConnectionsLoader ncLoader = new NetworkConnectionsLoader();
Thread thread2 = new Thread(ncLoader, "NetworkConnectionThread");
```

 7. Chame o método **start()** de ambos os objetos thread.
```java
thread1.start();
thread2.start();
```

 8. Aguarde pela finalização de ambas as thread utilizando o método **join()**. Este método pode lançar
uma exceção **InterruptedException**, então nós temos que incluir o código para capturá-la;
```java
try {
    thread1.join();
    thread2.join();
} catch(InterruptedException e) {
    e.printStackTrace();
}
```

 9. Escreva uma mensagem para indicar que o programa terminou;
```java
System.out.printf("Main: Configuration has been loaded: %s%n", new Date());
```

 10. Rode o programa e veja os resultados.

## Como ele funciona (How it works...)
Quando você roda este programa, você consegue ver como ambos os objetos **Thread** iniciam as suas
execuções. Primeiro, a **Thread** **DataSourceLoader** termina a sua execução. Então, a classe
**NetworkConnectionsLoader** termina a sua execução e, neste momento, o objeto da **Thread** main
continua a sua execução e escreve a mensagem final.

## Há mais (There is more...)
O Java fornece suas formas adicionais do método **join()**.
 - **join(long milessegundos)**;
 - **join(long milessegundos, long nanossegundos)**.
Na primeira versão do método **join()**, ao invés de aguardar indefinidamente pela finalização da thread
chamada, a thread invocadora aguarda pelos milessegundos especificados como o parâmetro do método.
Por exemplo, se o objeto **thread1** tem o código **thread2.join(1_000)**, a thread1 suspende a execução
dela até que uma das condições seja verdadeira:
 - **thread2** termina a sua execução;
 - 1000 milessegundos se passaram.
Quando uma destas duas condições forem verdadeiras o método **join()** retorna.

A segunda versão do método **join()** é similar a primeira, mas recebe o número de milessegundos e 
o número de nanossegundos como parâmetro.
