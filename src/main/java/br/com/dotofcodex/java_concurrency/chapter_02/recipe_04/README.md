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
 1. Crie uma classe chamada **PrintQueue** que irá implementar a fila de impressão;
```java
public class PrintQueue {
    
}
```

 2. Declare um objeto **Lock** e inicialize ele com um objeto da classe **ReentrantLock**;
```java
private final Lock queueLock = new ReentrantLock();
```

 3. Implemente o método **printJob()**. Ele irá receber um **Object** como parâmetro e ele não irá retornar
nenhum valor;
```java
public void printJob(Object document) {
    
}
```

 4. Dentro do método **printJob()**, obtenha o controle do objeto **Lock** chamando o método **lock()**;
```java
queueLock.lock();
```

 5. Então, inclua o seguinte código para simular a impressão de um documento;
```java
try {
    Long duration = (long) Math.random() * 10_000;
    System.out.println(Thread.currentThread().getName() +
        ":PrintQueue: Printing a Job during " + 
        (duration/1000) + " seconds");
    Thread.sleep(duration);
} catch(InterruptedException e) {
    e.printStackTrace();
}
```

 6. Finalmente, libere o controle do objeto **lock** com o método **unlock()**;
```java
finally {
    queueLock.unlock();
}
```

 7. Crie uma classe chamada **Job** e especifique que ela implementa a interface **Runnable**;
```java
public class Job implements Runnable {
    
}
```

 8. Declare um objeto da classe **PrintQueue** e implemente o construtor da classe que inicializa este 
objeto;
```java
private PrintQueue printQueue;

public Job(PrintQueue printQueue) {
    super();
    this.printQueue = printQueue;
}
```

 9. Implemente o método **run()**. Ele usa o objeto **PrintQueue** para enviar um objeto para impressão;
```java
@Override
public void run() {
    System.out.printf("%s: Going to print a document%n", Thread.currentThread().getName());
    printQueue.printJob(new Object());
    System.out.printf("%s: The document has been printed%n", Thread.currentThread().getName());
}
```

 10. Crie a classe **main** da aplicação implementando um classe chamada **Main** e adicionar o método
**main()** a ela;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 11. Crie um objeto **PrintQueue** compartilhado;
```java
PrintQueue printQueue = new PrintQueue();
```

 12. Crie 10 objetos **Job** e crie 10 **Thread** para rodá-los;
```java
Thread[] thread = new Thread[10];
for (int i=0; i<10; i++) {
    thread[i] = new Thread(new Job(printQueue), "Thread " + i);
}
```

 13. Inicie as 10 **threads**;
```java
for (int i=0; i<10; i++) {
    thread[i].start();
}
```

## Como ele funciona (How it works...)
Na seguinte captura de tela, você consegue ver parte da saída em uma execução, deste exemplo:

![saída do programa]()

A parte principal deste exemplo é o método **printJob()** da classe **PrintQueue**. Quando nós desejamos 
implementar uma seção crítica usando **locks** e garantir que somente uma thread de execução rode um 
bloco de código, nós temos que criar um objeto **ReentrantLock**. No começo da seção crítica, nós temos
que obter o controle do bloqueio (**lock**) usando o método **lock()**. Quando uma **thread** (A) chame 
este método, se nenhuma outra **thread** tem o controle do **lock**, o método dá para a **thread** (A)
o controle do **lock** e retorna imediatamente para permitir a execução da seção crítica por esta 
**thread**. Por outro lado, se houver outra **thread** (B) executando a seção crítica controlada por este
**lock**, o método **lock()** poe a **thread** (A) para dormir (suspende ela) até que a **thread** (B)
termine a execução da seção crítica.

No final da seção crítica, nós temos que usar o método **unlock()** para liberar o controle do **lock**
e permitir a outras thread rodarem esta seção crítica. Se você não chamar o método **unlock()** ao final 
da seção crítica, as outras **threads** que estão aguardando por este bloqueio irão aguardar para sempre,
causando uma situação de **deadlock**. Se você usa um bloco **try-catch** na sua seção crítica, não se 
esqueça de colocar a sentença do método **unlock()** dentro da seção **finally**.

## Há mais (There is more...)
A interface **Lock** (e a classe **ReentrantLock**) incluem outro método para obter o controle do 
**lock**. É o método **tryLock()**. A grande diferença para o método **lock()**, é que este método, se a
thread que usa ele não puder obter o controle do bloqueio, da interface **Lock**, ele retorna imediatamente
e não suspende a **thread**. Este método retorna um valor **boolean**, **true**, se a thread obteve o 
controle do bloqueio, e **false** caso contrário.

## Nota (Note)
Leve em consideração que é responsabilidade do programador considerar o resultado deste método e agir de 
acordo. Se o método retornar o valor **false**, é esperado que seu programa não exccute a seção crítica.
Caso contrário, você irá provavelmente ter resultados incorretos na sua aplicação.

## Há mais (There is more...) continuação....
A classe **ReentrantLock** também permite a você usar chamadas recursivas. Quando uma thread tem o controle
do bloqueio e faz uma chamada recursiva, ele continua com o bloqueio, então a chamada do método **lock()**
irá retornar imediatamente e a thread irá continuar com a execução da chamada recursiva. Além disso também
podemos chamar outros métodos.

## Mais informações (More info)
Você tem que ser cuidadoso com o uso de **locks** para evitar **deadlocks**. Esta situação ocorre quando
duas ou mais **threads** são bloqueadas aguardando pelo bloqueio que nunca será liberado. Por exemplo,
a **thread** (A) bloqueia a trava (X) e a **thread** (B) bloqueia a trava (Y). Agora, se a **thread** (A)
tentar bloquear a trava (Y) e a thread (B) simultaneamente tentar bloquear a trava (X), ambas as threads 
irão sem bloqueadas indefinidamente, porque elas estão aguardando por um bloqueio que nunca será liberado.
Note que o problema ocorre, porque ambas as threads tentam obter os bloqueios na ordem oposta. O Apêndice,
Concurrent Programming Design, explica algumas dicas para projetar aplicações concorrentes adequadamente e
evitar estes problemas de **deadlock**.

## Veja também (See also)
 - A receita Sincronizando um método, no capítulo 2, Sincronização básica de threads;
 - A receita, Usando múltiplas condições em um Lock, no capítulo 2, Sincronização básica de threads;
 - A receita, Monitorando a interface Lock, no capítulo 8, Testando aplicações concorrentes.
