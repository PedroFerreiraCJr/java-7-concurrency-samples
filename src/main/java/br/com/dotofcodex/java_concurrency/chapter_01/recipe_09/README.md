# Recipe 09 - Using local thread variables
Um dos aspectos mais críticos de uma aplicação concorrente são os dados compartilhados. Este tem uma 
importância especial naqueles objetos que estendem a classe **Thread** ou implementam a interface 
**Runnable**.

Se você cria um objeto de uma classe que implementa a interface **Runnable** e então inicia vários objetos
**Thread** usando o mesmo objeto **Runnable**, todas as threads compartilham os mesmos atributos. Isto 
significa que, se você alterar um atributo em uma thread, todas as outras threads irão ser afetadas por 
estas alterações.

Algumas vezes, você vai estar interessado em ter um atributo que não é compartilhado entre todas as threads
que rodam com o mesmo objeto. A API de Concorrência Java fornece um mecanismo limpo chamado de variáveis
locais à thread com uma performance muito boa.

Nesta receita, nós iremos desenvolver um programa que tenha o problema exposto no primeiro parágrafo e outro
programa que resolve este problema usando o mecanismo de variáveis locais à thread.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Primeiro, nós iremos implementar um programa que tenha o problema exposto anteriormente. Crie uma classe
chamada **UnsafeTask** e especifique que ela implemente a interface **Runnable**. Declare um atributo 
privado do tipo **java.util.Date**.
```java
public class UnsafeTask implements Runnable {
    private Date startDate;
}
```

 2. Implemente o método **run()** do objeto **UnsafeTask**. Este método irá inicializar o atributo 
**startDate**, escrever o valor dele no console, suspender por um período aleatório de tempo, e novamente,
escrever o valor do atributo **startDate**;
```java
@Override
public void run() {
    startDate = new Date();
    System.out.printf("Starting Thread: %s : %s%n", Thread.currentThread().getId(), startDate);
    try {
        TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.printf("Thread Finished: %s : %s%n", Thread.currentThread().getId(), startDate);
}
```

 3. Agora, vamos implementar a classe principal desta aplicação problemática. Crie uma classe chamada
**Main** com um método **main()**. Este método irá criar um objeto da classe **UnsafeTask** e iniciar 3
threads usando este objeto, suspender por dois segundos entre cada thread;
```java
public class Main {
    public static void main(String[] args) {
        UnsafeTask task = new UnsafeTask();
        for (int i=0; i<10; i++) {
            Thread thread = new Thread(task);
            thread.start();
            
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

 4. Na seguinte captura de tela, você pode ser os resultados das execução deste programa. Cada **Thread**
tem um tempo de inicialização diferente mas, quando elas terminam, todas tem o mesmo valor no seu atributo
**startDate**;

![saída do programa]()

 5. Como mencionado anteriormente, nós iremos usar o mecanismo de variáveis locais à thread para resolver
este problema;

 6. Crie uma classe chamada **SafeTask** e especifique que ela implementa a interface **Runnable**;
```java
public class SafeTask implements Runnable {
}
```

 7. Declare um objeto do tipo da classe **ThreadLocal<Date>**. Este método terá uma implementação implícita
que inclui o método **initialValue()**. Este métod irá retornar o valor atual;
```java
private static ThreadLocal<Date> startDate = new ThreadLocal<>() {
    protected Date initialValue() {
        return new Date();
    }
};
```

 8. Implemente o método **run()**. Ele tem a mesma funcionalidade que o método **run()** da classe 
**UnsafeTask**, mas ele alterar a forma de acesso ao atributo **startDate**;
```java
@Override
public void run() {
    startDate = new Date();
    System.out.printf("Starting Thread: %s : %s%n", Thread.currentThread().getId(), startDate.get());
    
    try {
        TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.printf("Thread Finished: %s : %s%n", Thread.currentThread().getId(), startDate.get());
}
```

 9. A classe **Main** deste exemplo é a mesma do exemplo da **UnsafeTask**, alterando o nome da classe
**Runnable**;

 10. Rode o exemplo e analise a diferença.

## Como ele funciona (How it works...)
Na captura de tela abaixo, você pode ver os resultados da execução do examplo **safe** (seguro). Agora, os
3 objetos **Thread** tem o seu próprio valor do atributo **startDate**. Segue a captura de tela:

![saída do programa]()

A variável local à thread armazena o valor do atributo para cada **Thread** que usa uma destas variáveis.
Você pode ler o valor usando o método **get()**, e alterar o valor usando o método **set()**. A primeira vez
que você acessa o valor da variável local à thread, se ele não tem um valor para o objeto **Thread** que 
está chamando, a variável local à thread invoca o método **initialValue()** para atribuir um valor para 
aquela **Thread** e retorna o valor inicial.

## Há mais (There is more...)
A classe classe **ThreadLocal** também fornece o método **remove()** que deleta o valor armazenado na 
variável local à thread para a thread que está o invocando.

A API de Concorrência Java inclui a classe **InheritableThreadLocal** que fornece herança de valores para 
threads criadas a partir desta thread. Se a thread **A** tem um valor na variável local à thread e ela cria
outra thread **B**, a thread **B** terá o mesmo valor que a thread **A** na variável local à thread. Você
pode sobrescrever o método **childValue()** que é chamado para inicializar o valor da thread filha na 
variável local à thread. Ela recebe o valor da thread parent (parente) na variável loca à thread como um
parâmetro.










