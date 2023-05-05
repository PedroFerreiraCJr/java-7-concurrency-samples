# Recipe 12 - Creating threads through a factory
O **design pattern factory** é um dos padrões de projeto mais usados no mundo da programação orientada à 
objetos. Este é um padrão criacional e o seu objetivo é desenvolver um objeto que tem a missão de criar 
outros objetos de uma ou de várias classes. Então, quando nós desejamos criar um objeto de uma destas 
classes, nós usamos a fábrica ao invés de usar o operador **new**.

Com esta fábrica, nós centralizamos a criação de objetos com algumas vantagens:
 - É fácil alterar a classe do objetos criados ou a forma como nós criamos estes objetos;
 - É fácil limitar a criação de objetos para recursos limitados. Por exemplo, nós podemos somente ter *n*
objetos deste tipo;
 - É fácil gerar dados estatísticos sobre a criação dos objetos.

O Java fornece uma interface, a interface **ThreadFactory** para implementar uma fábrica de objetos
**Thread**. Alguns utilitários avançados da API de Concorrência Java usam fábricas de threads para 
para criar **threads**.

Nesta receita, nós iremos aprender a implementar a interface **ThreadFactory** para criar objetos **Thread**
com um nome personalizado enquanto nós salvamos estatísticas dos objetos **Thread** criados.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **MyThreadFactory** e especifique que ela implementa a interface
**ThreadFactory**;
```java
public class MyThreadFactory implements ThreadFactory {
    
}
```

 2. Declare três atributos: um número inteiro (**int**) chamado **counter**, que nós iremos usar para 
armazenar o número de objetos **Thread** criados, uma **String** chamada **name** com o nome base de 
todo objeto **Thread** criado, uma lista de objetos **String** chamada de **stats** para salvar dados 
estatísticos sobre os objetos **Thread** criados. Nós também implementamos um construtor da classe 
que inicializa estes atributos;
```java
private int counter;
private String name;
private List<String> stats;

public MyThreadFactory(String name) {
    super();
    this.counter = 0;
    this.name = name;
    this.stats = new ArrayList<String>();
}
```

 3. Implemente o método **newThread()**. Este método irá receber uma interface **Runnable** e retornar 
um objeto **Thread** para esta interface **Runnable**. Em nosso caso, nós geramos o nome do objeto 
**Thread**, criamos um novo objeto **Thread**, e salvamos as estatísticas;
```java
@Override
public Thread newThread(Runnable r) {
    Thread t = new Thread(r, this.name + "-Thread_" + this.counter);
    this.counter++;
    stats.add(String.format("Created thread %d with name %s on %s%n",
        t.getId(), t.getName(), new Date()));
    return t;
}
```

 4. Implemente o método **getStatistics()** que retorna um objeto **String** com os dados estatísticos de
todos os objetos **Thread** criados;
```java
public String getStatistics() {
    StringBuffer buffer = new StringBuffer();
    Iterator<String> it = this.stats.iterator();
    
    while (it.hasNext()) {
        buffer.append(it.next());
        buffer.append("\n");
    }
    
    return buffer.toString();
}
```

 5. Crie uma classe chamada **Task** e especifique que ela implementa a interface **Runnable**. Para este 
exemplo, estas tarefas não irão fazer nada mas do que apenas suspender por um segundo;
```java
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

 6. Crie a classe **main** do exemplo. Crie uma classe chamada **Main** e implemente o método **main()**;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 7. Crie um objeto **MyThreadFactory** e um objeto **Task**;
```java
MyThreadFactory factory = new MyThreadFactory("MyThreadFactory");
Task task = new Task();
```

 8. Crie 10 objetos **Thread** usando o objeto **MyThreadFactory** e os inicialize;
```java
Thread thread = null;
System.out.printf("Starting the Threads%n");

for (int i = 0; i < 10; i++) {
    thread = factory.newThread(task);
    thread.start();
}
```

 9. Escreva no console as estatísticas da fábrica de threads;
```java
System.out.printf("Factory stats:%n");
System.out.printf("%s%n",factory.getStats());
```

 10. Rode o exemplo e veja os resultados.

## Como ele funciona (How it works...)
A interface **ThreadFactory** tem um único método chamado **newThread()**. Ele recebe um objeto **Runnable**
como um parâmetro e retorna um objeto **Thread**. Quando você implementa a interface **ThreadFactory**, você
tem que sobrescrever este método.

O **ThreadFactory** mais básico tem apenas uma linha:
```java
return new Thread(r);
```

Você pode melhorar esta implementação adicionando algumas variantes:
 - Criando threads personalizadas, como neste exemplo, usando um formato especial de nome ou até mesmo
criando a sua própria classe thread que herda de **Thread**;
 - Salvar estatísticas de criação e threads, como mostrado no exemplo anterior;
 - Limitar o número de threads criadas;
 - Validar a criação das threads;
 - E o que você puder imaginar.

O uso do design pattern fábrica é uma boa prática de programação mas, se você implementar a interface
**ThreadFactory** para centralizar a criação de threads, você tem que revisar o código para garantir que 
todas as threads são criadas usando a fábrica.

## Veja também (See also)
- A receita **Implementing the ThreadFactory interface to generate custom threads**, no capítulo 7,
Customizando Classes de Concorrência.
- A receita, **Using our ThreadFactory in an Executor object**, no capítulo 7, Customizando Classes
de Concorrência.
