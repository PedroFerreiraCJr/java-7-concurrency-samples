# Recipe 06 - Modifying Lock fairness
O construtor das classes **ReentrantLock** e **ReentrantReadWriteLock** admite um parâmetro booleano
chamado *fair* que permite a você controlar o comportamento de ambas as classes. O valor **false** é o
valor padrão e é chamado de **non-fair mode** (o que pode ser traduzido para **modo não justo**). Neste 
modo, quando houver alguma thread aguardando por uma trava (lock, **ReentrantLock**, ou 
**ReentrantReadWriteLock**), e a trava tem que selecionar alguma delas para obter acesso a seção 
crítica, ele seleciona uma delas sem nenhum critério. O valor *true* é chamado de **fair mode** 
(o que pode ser traduzido para, **modo justo**). Neste modo quando há algumas threads aguardando por 
uma trava (**ReentrantLock**, ou **ReentrantReadWriteLock**) e a trava tem que selecionar uma para obter 
acesso a seção crítica, ele seleciona a thread que tem aguardado por mais tempo. Leve em consideração 
que o comportamento explicado anteriormente é somente usado com os métodos **lock()** e **unlock()**. 
Como o método **tryLock()** não suspende a thread se a interface **Lock** for usada, o atributo *fair* 
não afeta esta funcionalidade.

Nesta receita, nós iremos modificar o exemplo implementado na receita **Synchronizing a block of code 
with a Lock** para usar este atributo e ver a diferença entre os modos *fair* e *non-fair*.

## Se preparando (Getting ready)
Nós iremos modificar o exemplo implementado na receita **Synchronizing a block of code with a Lock** 
então leia esta receita para implementar o exemplo.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Implemente o exemplo explicado na receita: **Synchronizing a block of code with a Lock**;
 2. Na classe **PrintQueue**, modifique a construção do objeto **Lock**. A nova instrução é dada como 
a seguir;
```java
private Lock queueLock = new ReentrantLock(true);
```

 3. Modifique o método **printJob()**. Separe a simulação da impressão em dois blocos de código, 
liberando a trava entre eles;
```java
public void printJob(Object document) {
    queueLock.lock();
    try {
        Long duration = (long) (Math.random() * 10_000);
        System.out.println(Thread.currentThread().getName() +
        ":PrintQueue: Printing a Job during " + (duration / 10_00) +
        " seconds");
        Thread.sleep(duration);
    }
    catch (InterruptedException e) {
        e.printStackTrace();
    }
    finally {
        queueLock.unlock();
    }

    queueLock.lock();

    try {
        Long duration = (long) (Math.random() * 10_000);
        System.out.println(Thread.currentThread().getName() +
        ":PrintQueue: Printing a Job during " + (duration / 10_00) +
        " seconds");
        Thread.sleep(duration);
    }
    catch (InterruptedException e) {
        e.printStackTrace();
    }
    finally {
        queueLock.unlock();
    }
}
```

 4. Modifique na classe **Main** o bloco de código que inicializa as threads. O novo bloco de código é
dado como o seguinte;
```java
for (int i = 0; i < 10; i++) {
    thread[i].start();
    
    try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

## Como ele funciona (How it works...)
Na seguinte captura de tela você consegue ver parte da saída da execução deste exemplo:

![saída do programa]()

Todas as threads são criados com uma diferença de 0.1 segundos. A primeira thread que solicita o controle
da trava é a **Thread-0**, e então a **Thread-1**, e assim por diante. Enquanto a **Thread-0** está 
rodando o primeiro bloco de código protegido pela trava, nós temos nove threads aguardando para executar 
o mesmo bloco de código. Quando a **Thread-0** libera a trava, imediatamente, ela solicita a trava 
novamente, então nós temos 10 threads tentando obter a trava. Como o modo *fair* está habilitado, a 
interface **Lock** irá escolher a **Thread-1**, então ela é a thread que está aguardando por mais tempo a 
trava. Então, ele escolhe a **Thread-2**, então, a **Thread-3**, e assim por diante. Até que todas as 
threads tenham passado o primeiro bloco protegido pela trava, nenhuma delas irá executar o segundo bloco 
protegido pela trava.

Uma vez que todas as threads tenham executado o primeiro bloco de código protegido pela trava, é a vez 
da **Thread-0** novamente. Então, é a vez da **Thread-1**, e assim por diante.

Para ver a diferença com o modo **non-fair**, altere o parâmetro passado para o construtor da trava 
e ponha o valor **false**. Na seguinte captura de tela, você pode ver o resultado da execução do 
exemplo modificado.

![saída do programa]()

Neste caso, as threads são executas na ordem em que foram criadas mas cada thread executa os dois blocos 
de código protegido. No entanto, este comportamento não é garantido porque, como explicado anteriormente, 
a trava poderia escolher qualquer thread para dar acesso ao bloco de código protegido. A JVM não garante, 
neste caso, a ordem de execução das threads.

## Há mais (There is more...)
As travas **Read/Write** também possuem o parâmetro **fair** no construtor delas. O comportamento deste 
parâmetro neste tipo de trava é o mesmo como explicado na introdução desta receita.

## Veja também (See also)
 - A receita, **Synchronizing a block of code with a Lock**, no capítulo 2, Sincronização básica de
threads;
 - A receita, **Synchronizing data access with read/write locks**, no capítulo 2, Sincronização básica de
threads;
 - A receita, **Implementing a custom Lock class**, no capítulo 7, Customizando classes de concorrência;
