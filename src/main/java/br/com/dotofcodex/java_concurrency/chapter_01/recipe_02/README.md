# Recipe 02 - Getting and setting thread information
A classe Thread salva alguns atributos de informação que podem nos ajudar a identificar a thread,
saber o seu status, or controlar a sua prioridade. Esses atributos são:
 - ID: Este atributo armazena um identificar único para cada *Thread*;
 - Name: Este atributo armazena o nome da *Thread*;
 - Priority: Este atributo armazena a prioridade do objeto *Thread*. Thread podem ter uma
 prioridade entre 1 e 10, onde 1 é a menor prioridade e 10 é a mais alta prioridade.
Não é recomendado alterar a prioridade das threads, mas é uma possíbilidade que você pode usar se você desejar.
 - Status: Este atributo armazena o status da Thread. Em Java uma Thread pode estar em um dos
seis estados: *new*, *runnable*, *blocked*, *waiting*, *time waiting*, ou *terminated*.

Nesta receita, nós iremos desenvolver um programa que estabelece o nome e prioridade para 10 threads e então
mostra informações sobre seus status até que elas terminem. As threads irão calcular a tabela de multiplicação
de um número.

## Como fazer (How to do it...)
Siga esses passos para implementar o exemplo.
 1. Crie uma classe chamada **Calculator** e especifique que ela implementa a interface Runnable;
 2. Declare um atributo **int private** chamado **number**, e implemente o construtor da classe
que inicializa este atributo;
 3. Implemente o método **run()**. Este método irá executar as instruções da thread que nós estamos criando,
então este método irá calcular e imprimir a tabela de multiplicação deste número;
 4. Agora, implemente a classe main deste exemplo. Crie uma classe chamada **Main** e implemente o
método **main()**;
 5. Crie um array de **10 Threads** e um array de **10 Thread.State** para armazenar as threads que vamos executar
e os seus status;
 6. Crie **10 objetos** da classe **Calculator**, cada um inicializa com um número diferente, e
10 threads para executá-las. Configure o valor da prioridade de 5 delas para o valor máximo e configure a prioridade
do resto para o valor mínimo;
 7. Crie um objeto **PrintWriter** para escrever para um arquivo a evolução do status das threads;
 8. Escreve para este arquivo o status das 10 threads. Agora, ele se torna **NEW**;
 9. Inicie a execução das **10 threads**;
 10. Até que as **10 threads** terminem, nós iremos checar o status delas. Se nós detectarmos uma alteração
no status de alguma thread, nós escrevemos ele para o arquivo;
 11. Implemente o método **writeThreadInfo()** que escrever o **ID**, **name**, **prioridade**
, **status antigo**, e o **novo status** da Thread;
 12. Execute o exemplo e abra o arquivo **log.txt** para ver a evolução das **10 threads**.

## Como ele funciona (How it works...)
A seguinte screenshot mostra as linhas do arquivo **log.txt** na execução deste programa.
Neste arquivo, nós podemos ver que as threads com mais alta prioridade terminar antes
daquelas com baixa prioridade. Nós podemos ver a evolução dos status de cada thread.

O programa mostrado no console é a tabela de multiplicação calculada pelas threads e a evolução
dos status de diferentes threads no arquivo **log.txt**. Deste forma, você consegue melhor visualizar
a evolução das threads.
A classe Thread tem atributos para armazenar todas as informações de uma thread. A JVM usa a prioridade
das threads para selecionar aquela que usa a CPU em cada momento e atualiza o status de cada thread 
de acordo com sua situação.
Se você não especifica o nome da thread, a JVM automaticamente atribui um com um formato, Thread-XX
onde XX é um número. Você não pode modificar o **ID** ou status de uma thread.
A classe Thread não implementa os métodos **setId()** e **setStatus()** para permitir a sua motificação.

## Há mais (There is more...)
In this recipe, you learned how to access the information attributes using a Thread object.
But you can also access these attributes from an implementation of the Runnable interface.
You can use the static method currentThread() of the Thread class to access the
Thread object that is running the Runnable object.
You have to take into account that the setPriority() method can throw an
IllegalArgumentException exception if you try to establish a priority that isn't
between one and 10.

Nesta receita, você aprendeu a como acessar os atributos de informação usando o objeto Thread.
Mas você também pode acessar esses atributos de uma implementação da interface Runnable.
Você pode usar o método estático **currentThread()** da classe Thread para acessar o objeto Thread
que está executando o objeto **Runnable**.
Você tem que levar em consideração que o método **setPriority()** pode lançar 
uma exceção **IllegarArgumentException** se você tentar estabelcer a priridade que não está nos intervalos
 de 1 e 10.