# Recipe 04 - Controlling the interruption of a thread
Na receita anterior, você aprendeu como você pode interromper a execução de uma thread e o quê você
tem que fazer para controlar esta interrupção no objeto **Thread**. O mecanismo mostrado no exemplo anterior
pode ser usado se a thread que pode ser interrompida for simples. Mas se a thread implementa um algoritmo
complexo dividido em alguns métodos, ou ela tenha métodos com chamadas recursivas, nós podemos usar um
mecanismo melhor para controlar a interrupção da thread. O Java fornece a exceção **InterruptedException**
para esse propósito. Você pode lançar esta exceção quando você detectar a interrupção da thread e 
capturá-la no método **run()**.

Nesta receita, nós iremos implementar uma **Thread** que procura por arquivos com um determinado nome em uma
pasta e em todas as suas subpastas para mostrar como usar a exceção **InterruptedException** para controlar
a interrupção de uma thread.

## Se preparando (Getting ready)
O exemplo desta receita foi implementado usando a IDE Eclipse. Se você usa o Eclipse ou outra IDE como
o NetBeans, abra e crie um novo projeto Java.

## Como fazer (How to do it...)
Siga estes passos para implementar o exemplo:
 1. Crie uma classe chamada **FileSearch** e especifique que ela implementa a interface **Runnable**;
```java
public class FileSearch implements Runnable {}
```

 2. Declare dois atributos **private**, um para o nome do arquivo que nós iremos pesquisar e um para
a pasta inicial. Implemente o construtor da classe, que inicializa estes atributos;
```java
private String initPath;
private String fileName;
public FileSearch(String initPath, String fileName) {
    this.initPath = initPath;
    this.fileName = fileName;
}
```

 3. Implemente o método **run()** da classe **FileSearch**. Ele checa se o atributo **fileName** é
um diretório e, se ele for, chama o método **processDirectory()**. Este método pode lançar a exceção
**InterruptedException**, então nós temos que capturá-la;
 ```java
@Override
public void run() {
    File file = new File(initPath);
    if (file.isDirectory()) {
        try {
            directoryProcess(file);
        } catch(InterruptedException e) {
            System.out.printf("%s: The search has been interrupted",
                Thread.currentThread().getName());
        }
    }
}
```

 4. Implemente o método **directoryProcess()**. Este método irá obter os arquivos e subpastas em uma
pasta e processá-los. Para cada diretório, o método irá fazer uma chamada recursiva passando um diretório
como parâmetro. Para cada arquivo, o método irá chamar o método **fileProcess()**. Após processar todos
os arquivos e pastas, o método checa se a Thread foi interrompida e, neste caso, lança uma exceção
**InterruptedException**;
 ```java
private void directoryProcess(File file) throws InterruptedException {
    File[] list = file.listFiles();
    if (list != null) {
        for (int i=0; i<list.length; i++) {
            if (list[i].isDirectory()) {
                directoryProcess(list[i]);
            }
            else {
                fileProcess(list[i]);
            }
        }
    }
    if (Thread.interrupted()) {
        throw new InterruptedException();
    }
}
```

 5. Implemente o método **fileProcess()**. Este método irá comparar o nome do arquivo que está sendo
processado com o nome que nós estamos procurando. Se os nomes são iguais, nós então escrevemos uma mensagem
no console. Após esta comparação, a Thread irá checar se ela foi interrompida e, neste caso, ela lança
a exceção **InterruptedException**;
 ```java
private void fileProcess(File file) throws InterruptedException {
    if (file.getName().equals(fileName)) {
        System.out.printf("%s : %s%n", Thread.currentThread()
            .getName(), file.getAbsolutePath());
    }
    if (Thread.interrupted()) {
        throw new InterruptedException();
    }
}
```

 6. Agora, vamos implementar a classe **Main** do exemplo. Implemente uma classe **Main** que contenha 
o método **main()**;
 ```java
public class Main {
    public static void main(String[] args) {
        
    }
}
```

 7. Crie e inicialize um objeto da classe **FileSearch** e uma Thread para executar sua tarefa. Então,
inicie a execução da Thread;
 ```java
FileSearch searcher = new FileSearch("C:\\", "autoexec.bat");
Thread thread = new Thread(searcher);
thread.start();
```

 8. Aguarde por 10 segundos e interrompa a Thread;
 ```java
try {
    TimeUnit.SECONDS.sleep(10);
} catch(InterruptedException e) {
    e.printStackTrace();
}

thread.interrupt();
```

 9. Rode o exemplo e veja os resultados.

## Como ele funciona (How it works...)
A seguinte screenshot mostra o resultado da execução deste programa. Você pode ver como o objeto da classe
**FileSearch** termina a sua execução quando ele detecta que ele foi interrompido.

![saída do programa](https://raw.githubusercontent.com/PedroFerreiraCJr/traducao-java-7-concurrency/master/images/recipe_04.png)

Neste exemplo, nós usamos as exceções do Java para controlar a interrupção da Thread. Quando você roda este
exemplo, o programa começa a ir através das pastas checando se elas tem o arquivo ou não. Por exemplo, se 
você fornecer a pasta "b\c\d", o programa irá ter três chamadas recursivas para o método 
**directoryProcess**. Quando ela detecta que ela foi interrompida, ela lança uma exceção 
**InterruptedException** e continua a execução no método **run()**, não importando quantas invocações
recursivas foram feitas.

## Há mais (There is more...)
A exceção **InterruptedException** é lançada por alguns métodos Java relacionados com a API de concorrência
como o método **sleep()**.

## Veja também (See also)
- Interrompendo uma thread, no capítulo 1, Gerenciamento de Threads.
