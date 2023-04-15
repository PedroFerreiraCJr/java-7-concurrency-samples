# Recipe 10 - Grouping threads in a group[
Uma funcionalidade interessante oferecida pela API de Concorrência do Java é a habilidade de agrupar threads.
Isto nos permite tratar threads de um grupo como uma única unidade e fornece acesso aos objetos **Thread**
que pertencem a um grupo para fazer uma operação com elas. Por exemplo, você tem algumas threads fazendo 
alguma tarefa e você deseja controlá-las, independente de quantas threads estão rodando, o status de cada uma
delas irá interromper todas elas com uma única invocação.

O Java fornece a classe **ThreadGroup** para trabalhar com grupos de threads. Um objeto **ThreadGroup** pode
ser formado por objetos **Thread** e por outro objeto **ThreadGroup**, gerando uma estrutura de árvore de 
objetos thread.

Nesta receita, nós iremos aprender a trabalhar com objetos **ThreadGroup** desenvolvendo um exemplo simples.
Nós iremos ter 10 threads suspendidas por um período aleatório de tempo (simulando uma busca, por exemplo) e,
quando uma delas terminar, nós iremos interromper o resto.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Primeiro, crie uma classe chamada **Result**. Ela irá armazenar o nome da **Thread** que terminou 
primeiro. Declare um atributo **private String**, chamado **name** e os métodos para ler e configurar o valor;
```java
public class Result {
    private String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
```

 2. Crie uma classe chamada **SearchTask** e especifique que ela implementa a interface **Runnable**;
```java
public class SearchTask implements Runnable {
    
}
```

 3. Declare um atributo **private** da classe **Result** e implemente o construtor da classe que inicializa 
este atributo;
```java
private Result result;
public SearchTask(Result result) {
    this.result = result;
}
```

 4. Implemente o método **run()**. Ele irá chamar o método **doTask()** e aguardar por ele para finalizar ou
por uma exceção **InterruptedException**. O método irá escrever mensagens para indicar o início, fim, ou a 
interrupção desta **Thread**;
```java
@Override
public void run() {
    String name = Thread.currentThread().getName();
    System.out.printf("Thread %s: Start%n", name);
    try {
        doTask();
        result.setName(name);
    } catch (InterruptedException e) {
        System.out.printf("Thread %s: Interrupted%n", name);
        return;
    }
    System.out.printf("Thread %s: End%n", name);
}
```

 5. Implemente o método **doTask()**. Ele irá criar um objeto **Random** para gerar um número aleatório e 
chamar o método **sleep()** com aquele número;
```java
private void doTask() throws InterruptedException {
    Random random = new Random(new Date().getTime());
    int value = (int) (random.nextDouble() * 100);
    System.out.printf("Thread %s: %d%n", Thread.currentThread()
        .getName(), value);
    TimeUnit.SECONDS.sleep(value);
}
```

 6. Agora, crie a classe **main** do exemplo, criando uma classe **Main** e implemente o método **main()**;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 7. Primeiro, crie um objeto **ThreadGroup** e chame ele **Searcher**;
```java
ThreadGroup threadGroup = new ThreadGroup("Searcher");
```

 8. Então, crie um objeto **SearchTask** e um objeto **Result**;
```java
Result result = new Result();
SearchTask searchTask = new SearchTask(result);
```

 9. Agora, crie 10 objetos **Thread** usando o objeto **SearchTask**. Quando você chama o construtor da classe
**Thread**, passe como primeiro argumento o objeto **ThreadGroup**;
```java
for (int i = 0; i < 10; i++) {
    Thread thread = new Thread(threadGroup, searchTask);
    thread.start();
    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

 10. Escreva informações sobre o objeto **ThreadGroup** usando o método **list()**;
```java
System.out.printf("Number of Threads: %d%n", threadGroup.activeCount());
System.out.printf("Information about the Thread Group%n");
threadGroup.list();
```

 11. Use os métodos **activeCount()** e **enumerate()** para saber quantos objetos **Thread** estão associados
com o objeto **ThreadGroup** e obtenha uma lista delas. Nós podemos usar este método para obter, por exemplo,
o estado de cada **Thread**;
```java
Thread[] threads = new Therad[threadGroup.activeCount()];
threadGroup.enumerate(threads);
for (int i = 0; i < threadGroup.activeCount(); i++) {
    System.out.printf("Thread %s: %s%n", threads[i]
        .getName(), threads[i].getState());
}
```

 12. Chame o método **waitFinish()**. Nós iremos implementar este método posteriormente. Ele irá aguardar até
que uma das threads do **ThreadGroup** termine;
```java
waitFinish(threadGroup);
```

 13. Interrompa o resto das threads do grupo usando o método **interrupt()**;
```java
threadGroup.interrupt();
```

 14. Implemente o método **waitFinish()**. Ele irá usar o método **activeCount()** para controlar o final de 
uma das threads;
```java
private static void waitFinish(ThreadGroup threadGroup) {
    while (threadGroup.activeCount() > 9) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

 15. Rode o exemplo e veja os resultados.

## Como ele funciona (How it works...)
Na seguinte captura de tela, vocÊ pode ver a saída do método **list()** e a saída gerada quando nós escrevemos
o status de cada objeto **Thread**, como mostrado na seguinte captura de tela.

![saída do programa]()

A classe **ThreadGroup** armazena os objetos **Thread** e outros objeto **ThreadGroup** associados com ele,
então ele pode acessar todas as informações (status, por exemplo) e executar operações sobre todos os seus
membros (interromper, por exemplo).

## Há mais (There is more...)
A classe **ThreadGroup** tem mais métodos. Cheque a documentação da API para ter uma completa explicação sobre
todos estes métodos.
