# Recipe 05 - Synchronizing data access with read/write locks
Uma das mais significantes melhorias oferecidas pelas travas (locks), e a interface **ReadWriteLock**
e a classe **ReentrantReadWriteLock**, a única que a implementa. Esta classe tem duas travas (locks), 
uma para operações de leitura, e outra para operações de escrita. Pode haver mais que uma thread usando 
operações de leitura simultaneamente, mas somente uma thread pode estar usando operações de escrita. 
Quando uma thread está fazendo uma operação de escrita, não pode haver nenhuma thread fazendo operações 
de leitura.

Nesta receita, você irá aprender como usar a interface **ReadWriteLock** implementando um programa que 
usa ela para controlar o acesso a um objeto que armazena o preço de dois produtos.

## Se preparando (Getting ready)
Você deve ler a receita **Synchronizing a block of code with a Lock** para um melhor entendimento desta 
receita.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **PricesInfo** que armazena informações sobre o preço de dois produtos;
```java
public class PricesInfo {
    
}
```

 2. Declare dois atributos **double** chamados **price1** e **price2**;
```java
public class PricesInfo {
    private double price1;
    private double price2;
}
```

 3. Declare um objeto **ReadWriteLock** chamado **lock**;
```java
public class PricesInfo {
    private ReadWriteLock lock; 
}
```

 4. Implemente o construtor da classe para iniciailizar os três atributos. Para o atributo **lock**, nós
criamos um novo objeto **ReentrantReadWriteLock**;
```java
public PricesInfo() {
    this.prices1 = 1.0;
    this.prices2 = 2.0;
    this.lock = new ReentrantReadWriteLock();
}
```

 5. Implemente o método **getPrice1()** que retorna o valor do atributo **price1**. Ele usa a trava de 
leitura para controlar o acesso ao valor deste atributo;
```java
public double getPrice1() {
    lock.readLock().lock();
    double value = price1;
    lock.readLock().unlock();
    return value;
}
```

 6. Implemente o método **getPrice2()** que retorna o valor do atributo **price2**. Ele usa a trava de
leitura para controlar o acesso ao valor deste atributo;
```java
public double getPrice2() {
    lock.readLock().lock();
    double value = price2;
    lock.readLock().unlock();
    return value;
}
```

 7. Implemente o método **setPrices()** que estabelece os valores para os dois atributos. Ele usa a trava 
de escrita para controlar o acesso a eles;
```java
public void setPrices(double price1, double price2) {
    lock.writeLock().lock();
    this.price1 = price1;
    this.price2 = price2;
    lock.writeLock().unlock();
}
```

 8. Crie uma classe chamada **Reader** e especifique que ela implementa a interface **Runnable**. Esta 
classe implementa um leitor de valores dos atributos da classe **PricesInfo**;
```java
public class Reader implements Runnable {
    
}
```

 9. Declare um objeto **PricesInfo** e implemente o construtor da classe que inicializa este objeto;
```java
private PricesInfo pricesInfo;

public Reader(PricesInfo pricesInfo) {
    this.pricesInfo = pricesInfo;
}
```

 10. Implemente o método **run()** desta classe. Ele lê 10 vezes o valor dos dois preços;
```java
@Override
public void run() {
    for (int i=0; i<10; i++) {
        System.out.printf("%s: Price 1: %f%n", Thread.currentThread().getName(), pricesInfo.getPrice1());
        System.out.printf("%s: Price 2: %f\n", Thread.currentThread().getName(), pricesInfo.getPrice2());
    }
}
```

 11. Crie uma classe chamada **Writer** e especifique que ela implementa a interface **Runnable**. Esta 
classe implementa um modificador de valores dos atributos da classe **PricesInfo**;
```java
public class Writer implements Runnable {
    
}
```

 12. Declare um objeto **PricesInfo** e implemente o construtor da classe que inicializa este objeto;
```java
private PricesInfo priceInfo;

public Write(PricesInfo priceInfo) {
    this.pricesInfo = pricesInfo;
}
```

 13. Implemente o método **run()**. Este modifica três vezes o valor dos dois preços que adormecem dois 
segundos durante as modificações;
```java
@Override
public void run() {
    for (int i=0; i<3; i++) {
        System.out.printf("Writer: Attempt to modify the prices.%n");
        pricesInfo.setPrices(Math.random() * 10, Math.random() * 8);
        System.out.printf("Writer: Prices have been modified.%n");
        
        try {
            Thread.sleep(2);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

 14. Implemente a classe principal do exemplo criando uma classe chamada **Main** e adicione o método 
**main()** a ela;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 15. Crie um objeto **PricesInfo**;
```java
PricesInfo pricesInfo = new PricesInfo();
```

 16. Crie cinco objetos **Reader** e cinco **Threads** para executá-los;
```java
Reader[] readers = new Reader[5];
Thread[] threadsReader = new Thread[5];

for (int i=0; i<5; i++){
    readers[i] = new Reader(pricesInfo);
    threadsReader[i] = new Thread(readers[i]);
}
```

16. Crie um objeto escritor e uma **Thread** para executá-lo;
```java
Write writer = new Writer(pricesInfo);
Thread threadWriter = new Thread(writer);
```

16. Rode as threads;
```java
for (int i=0; i<5; i++){
    threadsReader[i].start();
}

threadWriter.start();
```

## Como ele funciona (How it works...)
Na seguinte captura de tela, você pode ver uma parte da saída da execução deste exemplo:

![saída do programa](https://raw.githubusercontent.com/PedroFerreiraCJr/traducao-java-7-concurrency/master/images/recipe_05_segundo_capitulo.png)

Como mencionado anteriormente, a classe **ReentrantReadWriteLock** tem duas travas (locks), um para 
operações de leitura e uma para operações de escrita. O lock usado em operações de leitura é obtido com
o método **readLock()** declarado na interface **ReadWriteLock**.  Este lock é um objeto que implementa a 
interaface **Lock**, então nós podemos usar os métodos: **lock()**, **unlock()**, e **tryLock()**. O lock
usado em operações de escrita é obtido através do método **writeLock()** declarado na interace 
**ReadWriteLock**. Este **lock** é um objeto que implementa a interface **Lock**, então nós podemos usar
 os métodos: **lock()**, **unlock()**, e **tryLock()**. É responsabilidade do programador assegurar o uso 
correto deste locks, usando ele com o mesmo propósito para o qual eles foram projetas. Quando você obtem 
a trava de leitura da interface **Lock** você não pode modificar o valor da variável. Caso contrário, 
você provavelmente iria ter erros de dados inconsistentes.

## Veja também (See also)
 - A receita, **Synchronizing a block of code with a Lock**, no capítulo 2, Sincronização básica de
threads;
 - A receita, **Monitorin a Lock interface**, no capítulo 8, Testando aplicações concorrentes;
