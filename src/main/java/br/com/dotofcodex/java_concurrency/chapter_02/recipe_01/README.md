# Recipe 01 - Synchronizing a method
Nesta receita, nós iremos aprender a usar um dos mais básicos métodos para sincrônização em Java, ou seja,
o uso da palavra reservada **synchronized** para controlar o acesso concorrente a um método. Somente uma 
thread de execução irá acessar um dos métodos de um objeto declarado com a palavra reservada **synchronized**.
Se outra **thread** tentar acessar qualquer método declarado com a **keyword** **synchronized** do mesmo 
objeto, ele irá ser suspenso até que a primeira **thread** termine a execução do método.

Em outras palavras, todo método declarado com a palavra reservada **synchronized** é uma seção crítica e o 
Java somente permite a execução de uma **seção crítica** de um objeto.

Método estáticos tem um comportamento diferente. Somente uma thread de execução irá acessar um dos métodos 
estáticos declarados com a palavra reservada **synchronized**, mas outra thread pode acessar outro método 
não-estático de um objeto da classe. Você tem que ser muito cuidadoso com este ponto, porque duas threads 
podem acessar dois métodos sincronizados diferentes se um for estático e o outro não for. Se ambos os métodos
alterarm os mesmos dados, vocÊ pode ter erros de inconsistência de dados.

Para aprender este conceito, nós iremos implementar um exemplo com duas threads acessando um objeto em comum.
Nós iremos ter uma conta bancária e duas **threads**; uma que transfere dinheiro para a conta e outra que 
saca dinheiro da mesma conta. Se método sincronizados, nós podemos ter resultados incorretos. O mecanismo de
sincronização assegura que o total final da conta estará correto.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **Account** ela irá modelar nossa conta bancária. Ela tem somente um atributo 
do tipo **double**, chamado de **balance**;
```java
public class Account {
    private double balance;
}
```

 2. Implemente os método **setBalance() ** e **getBalance()** para escrever e ler o valor do atributo;
```java
public void setBalance(double balance) {
    this.balance = balance;
}

public double getBalance() {
    return this.balance;
}
```

 3. Implemente um método chamado **addAmount()** que incrementa o valor do atributo **balance** em certo 
montante que é passado para o método. Somente uma **thread** deve poder alterar o valor de balance, então 
use a palavra reservada **synchronized** para converter este método em uma **seção crítica**;
```java
public synchronized void addAmount(double amount) {
    double temp = this.balance;
    try {
        Thread.sleep(10);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    temp += amount;
    balance = temp;
}
```

 5. Implemente um método chamado **subtractAmount()** que decrementa o valor de **balance** em certo montante 
passado para o método. Somente uma **thread** deve alterar o valor de **balance**, então use palavra 
reservada **synchronized** para converter este método em uma **seção crítica**;
```java
public synchronized void subtractAmount(double amount) {
    double temp = this.balance;
    try {
        Thread.sleep(10);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    temp -= amount;
    this.balance = temp;
}
```

 5. Implemente uma classe que simula um **ATM**. Ele irá usar o método **subtractAmount()** para decrementar 
o montante de uma conta. Esta classe deve implementar a interface **Runnable** para ser executado em uma 
thread;
```java
public class Bank implements Runnable {
    
}
```

 6. Adicione um objeto **Account** a esta classe. Implemente o construtor da classe que inicializa este 
objeto;
```java
private final Account account;

public Bank(Account account) {
    this.account = account;
}
```

 7. Implemente o método **run()**. Ele faz cem (100) invocações para o método **subtractAmount()** de uma 
conta para reduzir o total da conta;
```java
@Override
public void run() {
    for (int i = 0; i < 100; i++) {
        this.account.subtractAmount(1_000);
    }
}
```

 8. Implemente uma classe que simula uma companhia e usa o método **addAmount()** de uma classe **Account** 
para incrementar o valor da conta. Esta classe deve implementar a interface **Runnable** para ser executada 
por uma thread;
```java
public class Company implements Runnable {
    
}
```

 9. Adicione um objeto **Account** a esta classe. Implemente o construtor da classe que inicializa o objeto 
**Account**;
```java
private Account account;

public Company(Account account) {
    this.account = account;
}
```

 10. Implemente o método **run()**. Este faz cem (100) invocações para o método **addAmount()** de uma 
**account** para incrementar o valor;
```java
@Override
public void run() {
    for (int i = 0; i < 100; i++) {
        this.account.addAmount(1_000);
    }
}
```

 11. Implementa a classe **main** da aplicação criando uma classe chamada **Main** que contém o método 
**main()**;
```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 12. Crie um objeto **Account** e inicialize o valor de **balance** para 1000;
```java
Account account = new Account();
account.setBalance(1_000);
```

 13. Crie um objeto **Company** e uma **Thread** para rodá-lo;
```java
Company company = new Company(account);
Thread companyThread = new Thread(company);
```

 14. Crie um objeto **Bank** e uma **Thread** para rodá-lo;
```java
Bank bank = new Bank(account);
Thread bankThread = new Thread(bank);
```

 15. Escreva o valor inicial da conta no console;
```java
System.out.printf("Account : Initial Balance: %f%n", account.getBalance());
companyThread.start();
bankThread.start();
```

 16. Aguarde pela finalização dos dois objetos **thread** usando o método **join()** e imprima no console 
o valor final da conta;
```java
try {
    companyThread.join();
    bankThread.join();
    System.out.printf("Account : Final Balance: %f%n", account.getBalance());
} catch(InterruptedException e) {
    e.printStackTrace();
}
```

## Como ele funciona (How it works...)
Nesta receita, você desenvolveu uma aplicação que incrementa e decrementa o valor total de uma classe que 
simula uma conta bancária. O programa faz 100 invocações para o método **addAmount()** que incrementa o valor
da conta em 1000 cada invocação e 100 invocações para o método **subtractAmount()** que decrementa o valor
o valor total em 1000 cada invocação. Você deve esperar que no final e inicial sejam iguais.

Você tentou forçar uma situação de erro usando uma variável chamada **temp** para armazenar o valor total da
conta, então você lê o valor de **balance**, você incrementa o valor da variável temporária, e então você
estabelece o valor da conta novamente. Adicionalmente, você foi introduziu um pequeno delay usando o 
método **sleep()** da classe **Thread** para por a thread que está executando o método em suspensão por 10 
milissegundos, então se outra thread executa este método, ela pode modificar o valor total da conta 
provocando um erro. É o mecanismo da palavra reservada **synchronized** que evita estes erros.

Se você deseja ver os problemas de acesso concorrente a dados compartilhados, delete a palavra reservada 
**synchronized** dos método **addAmount()** e **substractAmount()** e rode o programa. Sem a palavra 
reservada **synchronized**, enquanto uma thread está suspensa após ler o valor da variável **balance**, 
outro método irá ler o valor da variável **balance**, então ambos os método irão alterar o mesmo valor e 
uma das operações não será refletida no resultado final.

Como você pode ver na seguinte captura de tela, você pode obter resultados incosistentes:

![saída do programa](https://raw.githubusercontent.com/PedroFerreiraCJr/traducao-java-7-concurrency/master/images/recipe_01_01_segundo_capitulo.png)

Se você rodar o programa frequentemente, você irá obter resultados diferentes. A order da execução das threads 
não é garantida pela JVM. Então toda vez que vocÊ executa, as threads irão ler e modificar o valor total da 
conta em uma ordem diferente, então o resultado final será diferente.

Agora, adicione a palavra reservada **synchronized** como você aprendeu antes e rode o programa novamente. 
Como você pode ver na seguinte captura de tela, agora voê obter o resultado esperado. Se você rodar o programa
frequentemente, você irá obter os mesmos resultados. Me refiro a seguinte captura de tela:

![saída do programa](https://raw.githubusercontent.com/PedroFerreiraCJr/traducao-java-7-concurrency/master/images/recipe_01_02_segundo_capitulo.png)

Usando a palavra reservada **synchronized**, nós garantimos o acesso correto ao dado compartilhado em
aplicações concorrentes.

Como nós mencionamos na introdução desta receita, somente uma thread pode acessar os métodos de um objeto que 
usa a palavra reservada **synchronized** em sua declaração. Se uma thread (**A**) está executando um método 
sincronizado e outra thread (**B**) deseja executar outro método sincronizado do mesmo objeto, ela será 
bloqueada até que a thread **A** termine. Mas se a thread **B** tem acesso a objetos diferentes da mesma 
classe, nenhuma delas será bloqueada.

## Há mais (There is more...)
A palavra reservada **synchronized** penaliza a performance da aplicação, então você deve somente usá-la em 
métodos que modificam objetos compartilhados em ambiente concorrente. Se você tem múltiplas **threads** 
chamando um método sincronizado, somente uma irá executá-lo de cada vez enquanto as outras estaram 
aguardando. Se uma operação não usa a palavra reservada **synchronized**, todas as thread podem executar a 
operação ao mesmo tempo, reduzindo o tempo total de execução. Se você sabe que um método não irá ser chamado 
por mais de uma thread, não use a palavra reservada **synchronized**.

Você pode usar chamadas recursivas para métodos sincronizados. Como a **thread** tem acesso aos métodos 
sincronizados de um objeto, você pode chamar outros método sincronizados do mesmo objeto, incluindo o método 
que está executando. A thread não terá que obter acesso ao método sincronizado novamente.

Nós podemos usar a palavra reservada **synchronized** para proteger o acesso a um bloco de código ao invés de 
um método por completo. Nós devemos usar a palavra reservada **synchronized** desta forma para proteger o 
o acesso a um dado compartilhado, deixando o restante das operações fora do bloco, obtendo uma melhor
performance para a aplicação. O objetivo é ter a **seção crítica** (o bloco de código que pode ser acessado 
somente por uma thread de cada vez) o mais curta possível. Nós usamos a palavra reservada **synchronized** para 
proteger o acesso as instruções que atualizam o número de pessoas em uma construção, deixando de fora longas 
operações que não usam os dados compartilhados. Quando você usa a palavra reservada **synchronized** desta 
forma você deve passar uma referência de objeto como parâmetro. Somente uma thread pode acessar o bloco 
sincronizado (blocos ou métodos) de um objeto. Normalmente, você irá usar a palavra reservada **this** para 
referenciar o objeto que está tendo o método executado.

```java
synchronized (this) {
    // Código Java
}
```
