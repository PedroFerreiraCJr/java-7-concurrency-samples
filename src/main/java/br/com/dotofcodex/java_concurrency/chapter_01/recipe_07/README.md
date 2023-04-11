# Recipe 07 - Creating and running a daemon thread
O Java tem um tipo especial de thread que é chamada **daemon**. Este tipo de thread tem uma prioridade
muito baixa e normalmente somente executa quando nenhuma outra thread do mesmo programa está rodando.
Quando as thread **daemon** são as únicas threads rodando em um programa, a **JVM** termina o programa
terminando, assim, essas threads.

Com essas características, as threads **daemon** são normalmente usadas como provedoras de serviços para 
threads normais (também chamadas de usuário) threads rodando no mesmo programa. Elas usualmente possuem 
um loop infinito que aguarda pela solicitaçao do serviço ou executar tarefas da thread. Elas não podem 
desempenhar trabalho importante porque nós não sabemos quando elas irão ter tempo de CPU e elas podem 
terminar a qualquer momento se não houver outras threads rodando. Um exemplo típico deste tipo de threads
são as threads de coleta de lixo (Garbase Collector).

Nesta receita, nós iremos aprender a como criar uma thread **daemon** desenvolvendo um exemplo com duas 
threads; uma thread do usuário que escreve eventos em uma **queue** (fila), e uma thread **daemon** que 
limpa aquela fila, removendo os eventos que foram gerados há mais de 10 segundos atrás.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **Event**. Esta classe somente armazena informações sobre os eventos que o nosso
programa trabalha. Declare dois atributos privados, um chamado **date** do tipo **java.util.Date** e o outro 
chamado **event** do tipo **java.lang.String**. Gere os métodos para escrita e leitura destes valores;
```java
public class Event {
    private Date date;
    private String event;
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getEvent() {
        return event;
    }
    
    public void setEvent(String event) {
        this.event = event;
    }
}
```

 2. Crie uma classe chamada **WriterTask** e especifique que ela implementa a interface **Runnable**;
```java
public class WriterTask implements Runnable { }
```

 3. Declare uma **queue** (fila) que armazena os eventos e implemente o construtor desta classe, que 
inicializa esta **queue** (fila);
```java
private Deque<Event> deque;
public WriterTask(Deque<Event> deque) {
    this.deque = deque;
}
```

 4. Implemente o método **run()** desta tarefa. Este método terá um loop com 100 iterações. Em cada iteração,
nós criamos um novo **Event**, salvamos ele na **queue** (fila), e suspendemos por 1 segundo;
```java
@Override
public void run() {
    for (int i=1; i<100; i++) {
        Event event = new Event();
        event.setDate(new Date());
        event.setEvent(String.format("The thread %s has generated an event", 
            Thread.currentThread().getId()));
        deque.addFirst(event);
        
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

 5. Crie uma classe chamada **CleanerTask** e especifique que ela deriva de **Thread**;
```java
public class CleanerTask extends Thread { }
```

 6. Declare uma **queue** (fila) que armazena os eventos e implemente o construtor da classe, que inicializa 
esta **queue** (fila). Neste construtor, marque esta **Thread** como uma thread **daemon** com o método
**setDaemon()**;
```java
private Deque<Event> deque;
public CleanerTask(Deque<Event> deque) {
    this.deque = deque;
    setDaemon(true);
}
```

 7. Implemente o método **run()**. Ele tem um loop infinito que obtém um **date** e chama o método 
**clean()**;
```java
@Override
public void run() {
    while (true) {
        Date date = new Date();
        clean(date);
    }
}
```

 8. Implemente o método **clean()**. Ele obtém o último evento e, se ele foi criado há mais de 10 segundos
atrás, ele deleta este e checa o próximo evento. Se um evento for deletado, ele escreve a mensagem do evento 
e o novo tamanho da **queue** (fila), então será possível ver a sua evolução;
```java
private void clean(Date date) {
    long difference = 0;
    boolean delete;
    
    if (deque.size() == 0) {
        return;
    }
    
    delete = false;
    do {
        Event e = deque.getLast();
        difference = date.getTime() - e.getDate().getTime();
        if (difference > 10_000) {
            System.out.printf("Cleaner: %s%n", e.getEvent());
            deque.removeLast();
            delete = true;
        }
    } while (difference > 10_000);
    
    if (delete){
        System.out.printf("Cleaner: Size of the queue: %d%n", deque.size());
    }
}
```

 9. Agora, implemente a classe **Main**. Crie uma classe chamada **Main** com o método **main()**;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 10. Crie uma **queue** (fila) para armazenar os eventos usando a classe **Deque**;
```java
Deque<Event> deque = new ArrayDeque<Event>();
```

 11. Crie e inicie 3 threads **WriterTask** e uma thread **CleanerTask**;
```java
WriterTask writer = new WriterTask(deque);
for (int i=0; i<3; i++) {
    Thread thread = new Thread(writer);
    thread.start();
}

CleanerTask cleaner = new CleanerTask(deque);
cleaner.start();
```

 12. Rode o programa e veja os resultados.

## Como ele funciona (How it works...)
Se você analisou a saída da execução do programa, você pode ver como a **queue** (fila) começa a crescer até
que ela tenha 30 eventos e então, o tamanho dela começa a variar de 27 a 30 eventos até o final da execução.

O programa começa com as 3 threads **WriterTask**. Cada thread escreve um evento e suspende por 1 segundo.
Após os primeiros 10 segundos, nós temos 30 eventos na **queue** (fila). Durante estes 10 segundos, a thread
**CleanerTask** esteve executando enquanto as 3 threads **WriterTask** estavam suspensas, mas ela não deletou 
nenhum evento, porque todos eles foram gerados menos de 10 segundos atrás. Durante o resto da execução, 
a thread **CleanerTask** deleta 3 eventos a cada segundo e as 3 threads **WriterTask** escrevem mais 3, então
o tamanho da fila varia de 27 a 30 eventos.

Você pode brincar com o tempo até que as threads **WriterTask** estejam suspensas. Se você usar um menor valor
, você irá ver que a **ClenaerTask** tem menos tempo de CPU e o tamanho da fila irá crescer porque a 
**CleanerTask** não deleta nenhum evento.

## Há mais (There is more...)
Você somente pode chamar o método **setDaemon()** antes que você invoque o método **start()**. Uma vez que a
thread já esteja rodando você não pode mudar o status dela para **daemon**.

Você pode usar o método **isDaemon()** para checar se uma thread é uma thread **daemon** (o método devolve
**true**) ou uma thread do usuário (o método devolve **false**).
