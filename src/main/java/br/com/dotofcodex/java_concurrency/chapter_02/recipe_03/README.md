# Recipe 03 - Using conditions in synchronized code
Um problema clássico na programação concorrente é o problema do produtor-consumidor. Nós temos um buffer
de dados, um ou mais produtores de dados que salva ele em um buffer e um ou mais consumidores de dados 
que retiram eles do buffer.

Como o buffer é uma estrutura de dados compartilhada, nós temos que controlar o acesso a ela usando um 
mecanismo de sincronização como a palavra reservada **synchronized**, mas nós temos mais limitações. Um 
produtor não pode salvar dados em um buffer se ele estiver cheio e o consumidor não pode retirar um dado
do buffer se ele estiver vazio.

Para este tipo de situações, o Java fornece os métodos **wait()**, **notify()**, e **notifyAll()** 
implementados na classe **Object**. Uma **thread** pode chamar o método **wait()** dentro de um bloco de 
código sincronizado (**synchronized**). Se a **thread** chamar o método **wait()** fora de um bloco de 
código sincronizado, a JVM lança uma exceção do tipo **IllegalMonitorStateException**. Quando a **thread** 
chama o método **wait()**, a JVM coloca a **thread** para dormir e libera o objeto que controla o bloco 
de código sincronizado que está executando e permite a outras **threads** executarem outros blocos de 
código sincronizados protegido por aquele objeto. Para acordar a **thread** que foi suspensa, é preciso
chamar o método **notify()**, ou **notifyAll()** dentro de um bloco de código protegido pelo mesmo 
objeto.

Nesta receita, você irá aprender a como implementar o problema produtor-consumidor usando a palavra 
reservada **synchronized** e os métodos **wait()**, **notify()**, e **notifyAll()**.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **EventStorage**. Ela tem dois atributos: um atributo **int** chamado de
**maxSize**, e um **LinkedList<Date>** chamado de **storage**;
```java
public class EventStorage {
    private int maxSize;
    private LinkedList<Date> storage;
}
```

 2. Implemente o construtor da classe que inicializa os atributos da classe;
```java
public EventStorage() {
    super();
    this.maxSize = 10;
    this.storage = new LinkedList<>();
}
```

 3. Implemente o método sincronizado **set()** que armazena um evento no **storage**. Primeiro checa se o 
storage está cheio ou não. Se ele estiver cheio, ele chama o método **wait()** até que o storage tenha 
espaço vazio. No final do método, nós chamamos o método **notifyAll()** para acordar todas as **threads**
que estão suspensas no método **wait()**;
```java
public synchronized void set() {
    while (this.storage.size() == maxSize) {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    this.storage.offer(new Date());
    System.out.printf("Set: %d", storage.size());
    notifyAll();
}
```

 4. Implemente o método sincronizado **get()** para obter um evento do **storage**. Primeiro, checa se o 
storage tem evento ou não. Se ele não tiver eventos, ele chama o método **wait()** até que o storage tenha
algum evento. E ao final do método, nós chamamos o método **notifyAll()** para acordar todas as **threads**
que estão adormecidas no método **wait()**;
```java
public synchronized void get() {
    while (storage.size() == 0) {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    System.out.printf("Get: %d: %s", storage.size(), ((LinkedList<?>) storage).poll());
    notifyAll();
}
```

 5. Crie uma classe chamada **Producer** e especifique que ela implementa a interface **Runnable**. Ela 
irá implementar o produtor do exemplo;
```java
public class Producer implements Runnable {
    
}
```

 6. Declare um objeto **EventStorage** e implemente o construtor que inicializa este objeto;
```java
private EventStorage storage;

public Producer(EventStorage storage) {
    super();
    this.storage = storage;
}
```

 7. Implemento o método **run()** que chama 100 vezes o método **set()** do objeto **EventStorage**; 
```java
@Override
public void run() {
    for (int i=0; i<100; i++) {
        storage.set();
    }
}
```

 8. Crie uma classe chamada **Consumer** e especifique que ela implementa a interface **Runnable**. Ela irá 
implementar o consumidor deste exemplo;
```java
public class Consumer implements Runnable {
    
}
```

 9. Declare um objeto **EventStorage** e implemente o construtor que inicializa este objeto;
```java
private EventStorage storage;

public Consumer(EventStorage storage) {
    super();
    this.storage = storage;
}
```

 10. Implemente o método **run()**. Ele chama 100 vezes o método **get()** do objeto **EventStorage**;
```java
@Override
public void run() {
    for (int i=0; i<100; i++) {
        storage.get();
    }
}
```

 11. Crie a classe **main** do exemplo implementando uma classe nomeada **Main** e adicione o método 
**main** a ela;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 12. Crie um objeto **EventStorage**;
```java
EventStorage storage = new EventStorage();
```

 13. Crie um objeto **Producer** e uma **Thread** para rodá-lo;
```java
Producer producer = new Producer(storage);
Thread thread1 = new Thread(producer);
```

 14. Crie um objeto **Consumer** e uma **Thread** para rodá-lo;
```java
Consumer consumer = new Consumer(storage);
Thread thread2 = new Thread(consumer);
```

 15. Start ambas as threads;
```java
thread1.start();
thread2.start();
```

## Como ele funciona (How it works...)
A chave deste exemplo são os métodos **set()** e **get()** da classe **EventStorage**. Primeiro de tudo, o 
método **set()** checa se há espaço vazio no atributo **storage**. Se ele estiver cheio, ele chama o método
**wait()** para esperar por espaço vazio. quando as outras threads chamam o método **notifyAll()**, a thread
acorda e checa a condição novamente. O método **notifyAll()** não garante que a thread irá acordar. Este 
processo é repetido até que haja espaço livre no **storage** e ele possa gerar um novo evento e armazená-lo.

O comportamento do método **get()** é similar. Primeiro, ele checa se há um evento no **storage**. Se a 
classe **EventStorage** estiver vazia, ele chama o método **wait()** para esperar por eventos. Quando as 
outra threads chamam **notifyAll()**, a thread acorda e checa a condição novamente até que haja algum evento
no storage para consumir.

## Nota (Note)
Você tem que se manter checando a condição e chamando o método **wait()** em um laço **while**. Você não 
pode continuar até que a condição seja verdadeira.

Se você rodar este exemplo, você irá ver como o **producer** e **consumer** estão definindo e obtendo os 
eventos, mas o storage nunca tem mais que 10 eventos.

## Há mais (There is more...)
Existem outros usos importantes da palavra-chave **synchronized**. Veja a seção "Veja também" para outras
receitas que explicam o uso deste palavra reservada.

## Veja também (See also)
- Organizando atributos independentes em classes sincronizadas, no capítulo 2, Sincronização básica de
threads.
