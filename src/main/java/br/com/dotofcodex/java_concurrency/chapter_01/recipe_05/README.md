# Recipe 05 - Sleeping and resuming a thread
Algumas vezes, você vai estar interessado em interromper a execução de uma **Thread** durante um determinado
período de tempo. Por exemplo, uma thread em um programa que checa o estado de um sensor a cada minuto. No
restante do tempo, a thread não faz nada. Durante este tempo, a thread não usa nenhum recurso do computador.
Após este tempo, a thread vai estar pronta para continuar com a sua execução quando a JVM escolher ela 
para ser executada. Você pode usar o método **sleep()** da classe **Thread** para esse propósito. Este método
recebe um inteiro como um parâmetro para indicar o número de milessegundos que a thread deve suspender a sua
execução. Quando o tempo de espera termina, a thread continua com sua execução das instruções, após a chamada
do método **sleep()**, quando a JVM atribui tempo de CPU para ela.

Outra possibilidade é usar o método **sleep()** de um elemento da enumeração **TimeUnit**.

Nesta receita, nós iremos desenvolver um programa que usa o método **sleep()** para escrever a data atual
a cada segundo.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **FileClock** e especifique que ela implementa a interface **Runnable**;
```java
public class FileClock implements Runnable {}
```

 2. Implemente o método **run()**;
```java
@Override
public void run() {
    
}
```
 3. Escreva um loop com 10 iterações. Em cada iteração, crie um objeto **Date**, escreva ela para o
arquivo, e chame o método **sleep()** do atributo **SECONDS** da classe **TimeUnit** para suspender a 
execução da thread por 1 segundo. Com este valor, a thread irá suspender a execução por aproximadamente
1 segundo. Como o método **sleep()** pode lançar a exceção **InterruptedException**, nós temos que incluir 
o código para capturá-la. É uma boa prática incluir código que liberar ou fechar os recursos que uma thread
está usando quando ela é interrompida;
```java
for (int i=0; i<10; i++) {
    System.out.printf("%s%n", new Date());
    try {
        TimeUnit.SECONDS.sleep(1);
    } catch(InterruptedException e) {
        System.out.printf("The FileClock has been interrupted");
    }
}
```

 4. Nós implementamos a thread. Agora, vamos implementar a classe **main** do exemplo. Crie uma classe chamada
**FileMain** que contém o método **main()**;
```java
public class FileMain {
    public static void main(String[] args) {
        
    }
}
```

 5. Crie um objeto da classe **FileClock** e a **Thread** para executá-lo;
```java
FileClock clock = new FileClock();
Thread thread = new Thread(clock);
thread.start();
```

 6. Chame o método **sleep()** do atributo **SECONDS** da classe **TimeUnit** na thread principal para
aguardar 5 segundos;
```java
try {
    TimeUnit.SECONDS.sleep(5);
} catch(InterruptedException e) {
    e.printStackTrace();
}
```

 7. Interrompa a thread **FileClock**;
```java
thread.interrupt();
```

 8. Rode os exemplos e veja os resultados.

## Como ele funciona (How it works...)
Quando você roda o exemplo, você pode ver como o programa escreve o objeto **Date** por segundo e então, a
mensagem indicando que a thread **FileClock** foi interrompida.

Quando você chama o método **sleep()**, a **Thread** deixa a CPU e para a sua execução por um período de 
tempo, durante este tempo, ela não está consumindo tempo de CPU, então a CPU pode executar outras tarefas.

Quando a **Thread** está suspensa e é interrompida, o método lança uma exceção **InterruptedException**
imediatamente e não aguarda até que o tempo de suspensão termine.

## Há mais (There is more...)
A API de concorrência do Java tem outro método que faz com que o objeto **Thread** deixe de usar a CPU.
Este é o método **yield()**, que indica para a JVM que o objeto **Thread** pode deixar a CPU para outras
tarefas. A JVM não assegura que irá aceitar essa solicitação. Normalmente, este é usado somente para 
propósitos de debug.
