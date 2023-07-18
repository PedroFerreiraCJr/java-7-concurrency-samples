# Recipe 02 - Arranging independent attributes in synchronized classes
Quando você usa a palavra-chave **synchronized** para proteger um bloco de código, você deve passar uma 
referência de objeto como um parâmetro. Normalmente, você irá usar a palavra-chave **this** para referenciar
o objeto que executa o método, mas você pode usar outra referência de objeto. Normalmente, estes objetos 
irão ser criados exclusivamente com este propósito. Por exemplo, se você tem dois atributos independentes 
em uma classe compartilhada entre mútiplas **threads**, você deve sincronizar o acesso a cada variável, mas 
não há problema se houver uma das **threads** acessando um dos atributos e outra **thread** acessando o 
outro ao mesmo tempo.

Nesta receita, você aprenderá como resolver esta situação de programação com um exemplo que simula um 
cinema com duas telas, e duas bilheterias. Quando uma bilheteira vende bilhetes, estes são para um dos 
dois cinemas, mas não para os dois, então o número de acentos livres em cada um cinema são atributos 
independentes.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **Cinema** e adicione a ela dois atributos do tipo **long** nomeados de, 
**vacanciesCinema1** e **vacanciesCinema2**;
```java
public class Cinema {
    private long vacanciesCinema1;
    private long vacanciesCinema2;}
```

 2. Adicione a classe **Cinema** dois atributos **Object** adicionais nomeados de **controleCinema1** e
**controlCinema2**;
```java
public class Cinema {
    // ...
    private final Object controlCinema1;
    private final Object controlCinema2;
    // ...
}
```

 3. Implemente o construtor da classe **Cinema** que inicializa todos os atributos da classe;
```java
public class Cinema {
    // ...
    public Cinema() {
        super();
        this.controlCinema1 = new Object();
        this.controlCinema2 = new Object();
        this.vacanciesCinema1 = 20;
        this.vacanciesCinema2 = 20;
    }
}
```

 4. Implemente o método **sellTickets1()** que é chamado quando um ticket para o primeiro cinema for vendido;
```java
public boolean sellTickets1(int number) {
    synchronized (controlCinema1) {
        if (number < vacanciesCinema1) {
            vacanciesCinema1 -= number;
            return true;
        }
        return false;
    }
}
```

 5. Implemente o método **sellTickets2()** que é chamado quando um ticket para o segundo cinema for 
vendido. Ele usa o objeto **controlCinema2** para controlar o acesso ao bloco de código sincronizado;
```java
public boolean sellTickets2(int number) {
    synchronized (controlCinema2) {
        if (number < vacanciesCinema2) {
            vacanciesCinema2 -= number;
            return true;
        }
        return false;
    }
}
```

 6. Implemente o método **returnTickets1()** que é chamado quando algum ticket para o primeiro cinema for 
retornado. Ele usa o objeto **controlCinema1** para controlar o acesso ao bloco de código sincronizado;
```java
public boolean returnTickets1(int number) {
    synchronized (controlCinema1) {
        vacanciesCinema1 += number;
        return true;
    }
}
```

 7. Implemente o método **returnTickets2()** que é chamado quando algum ticket para o segundo cinema for
retornado. Ele usa o objeto **controlCinema2** para controlar o acesso ao bloco de código sincronizado;
```java
public boolean returnTickets2(int number) {
    synchronized (controlCinema2) {
        vacanciesCinema2 += number;
        return true;
    }
}
```

 8. Implemente outros dois métodos que retorna o número de vagas em cada cinema;
```java
public long getVacanciesCinema1() {
    return this.vacanciesCinema1;
}

public long getVacanciesCinema2() {
    return this.vacanciesCinema2;
}
```

 9. Implemente a classe **TicketOffice1** e especifique que ela implementa a interface **Runnable**;
```java
public class TicketOffice1 implements Runnable {
    
}
```

 10. Declara um objeto **Cinema** e implemente o construtor da classe que inicializa este objeto;
```java
private Cinema cinema;

public TicketOffice1(Cinema cinema) {
    super();
    this.cinema = cinema;
}
```

 11. Implemente o método **run()** que simula algumas operações em dois cinemas;
```java
@Override
public void run() {
    cinema.sellTickets1(3);
    cinema.sellTickets1(2);
    cinema.sellTickets2(2);
    cinema.returnTickets1(3);
    cinema.sellTickets1(5);
    cinema.sellTickets2(2);
    cinema.sellTickets2(2);
    cinema.sellTickets2(2);
}
```

 12. Implemente a classe **TicketOffice2** e especifique que ela implementa a interface **Runnable**;
```java
public class TicketOffice2 implements Runnable {
    
}
```

 13. Declare um objeto **Cinema** e implemente o construtor da classe que inicializa este objeto;
```java
private Cinema cinema;

public TicketOffice2(Cinema cinema) {
    super();
    this.cinema = cinema;
}
```

 14. Implemente o método **run()** que simula algumas operações nos dois cinemas;
```java
@Override
public void run() {
    cinema.sellTickets2(2);
    cinema.sellTickets2(4);
    cinema.sellTickets1(2);
    cinema.sellTickets1(1);
    cinema.returnTickets2(2);
    cinema.sellTickets1(3);
    cinema.sellTickets2(2);
    cinema.sellTickets1(2);
}
```

 15. Implemente a classe main deste exemplo criando uma classe chamada **Main** e adicione o método **main**;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 16. Declara e crie um objeto **Cinema**;
```java
Cinema cinema = new Cinema();
```

 17. Crie um objeto **TicketOffice1** e um objeto **Thread** para rodá-lo;
```java
Cinema cinema = new Cinema();

TicketOffice1 ticketOffice1 = new TicketOffice1(cinema);
Thread thread1 = new Thread(ticketOffice1, "TicketOffice1");
```

 18. Crie um objeto **TicketOffice2** e um objeto **Thread** para rodá-lo;
```java
Cinema cinema = new Cinema();

TicketOffice2 ticketOffice2 = new TicketOffice2(cinema);
Thread thread2 = new Thread(ticketOffice2, "TicketOffice2");
```

 19. Inicie ambas as **Threads**;
```java
thread1.start();
thread2.start();
```

 20. Aguarde o término das **threads**;
```java
try {
    thread1.join();
    thread2.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}
```

 21. Escreva no console as vagas dos dois cinemas;
```java
System.out.printf("Room 1 Vacancies: %d%n", cinema.getVacanciesCinema1());
System.out.printf("Room 2 Vacancies: %d%n", cinema.getVacanciesCinema2());
```

## Como ele funciona (How it works...)
Quando você usa a palavra-chave **synchronized** para proteger um bloco de código, você usa um objeto como
parâmetro. A JVM garante que somente uma thread pode ter acesso a todos os blocos de código protegidos com 
determinado objeto (note que sempre falamos sobre objetos e não classes).

## Nota (Note)
Neste exemplo, nós temos um objeto que controla o acesso ao atributo **vacanciesCinema1**, então
somente uma **thread** pode modificar este atributo de cada vez, e outro objeto controla o acesso ao 
atributo **vacanciesCinema2**, então somente uma **thread** pode modificar este atributo de cada vez. Mas, 
pode haver duas **threads** rodando simultaneamente, uma modificando o atributo **vacanciesCinema1** e a 
outra modificando o atributo **vacanciesCinema2**.

Quando você roda este exemplo, você pode ver como o resultado final é sempre o número esperado de vagas para 
cada cinema. Na seguinte captura de tela, você pode ver os resultados de uma execução da aplicação.

![saída do programa](https://raw.githubusercontent.com/PedroFerreiraCJr/traducao-java-7-concurrency/master/images/recipe_02_segundo_capitulo.png)

## Há mais (There is more...)
Há outros usos importantes da palavra-chave **synchronized**. Veja a seção **ver também**, para outras 
receitas que explicam o uso da palavra-chave **synchronized**. 

## Veja também (See also)
- Usando condições em código sincronizado, no capítulo 2, Sincronização básica de threads.


