# Thread Management - Gerenciamento de Threads

Neste capítulo, nós iremos cobrir:
 - Creating and running a thread - Criando e executando uma thread;
 - Getting and setting thread information - Obtendo e configurando informações da thread;
 - Interrupting a thread - Interrompendo uma thread;
 - Controlling the interruption of a thread - Controlando a interrupção de uma thread;
 - Sleeping and resuming a thread - Adormecendo e resumindo uma thread;
 - Waiting for the finalization of a thread - Aguardando a finalização de uma thread;
 - Creating and running a daemon thread - Criando e rodando uma thread daemon;
 - Processing uncontrolled exceptions in a thread - Processando exceções não controladas em uma thread;
 - Using local thread variables - Usando variáveis locais a thread;
 - Grouping threads into a group - Agrupando thread em um grupo;
 - Processing uncontrolled exceptions in a group of threads - Processando exceções não controladas em um grupo de threads;
 - Creating a thread through a thread factory - Criando uma thread através de uma fábrica de threads.

Introdução:
No mundo da computação, quando nós falamos sobre **concorrência**, nós falamos em uma série de tarefas
que rodam simultâneamente em um computador. Essa simultâneidade pode ser real se o computador tiver mais
do que um processador ou um processador multi-core, ou aparente se o computador tiver somente um núcleo
de processamento.

Todos os sistemas operacionais modernos permitem a execução concorrente de tarefas. Você pode ler os seus e-mails
enquanto você escuta uma música e lê as notícias em uma página da web. Nós podemos dizer que este tipo de concorrência
é uma concorrência a nível de processo. Mas dentro de um processo, nós também podemos ter várias outras tarefas
simultâneas. As tarefas concorrentes que rodam dentro de um processo são chamadas de **threads**.

Outro conceito relacionado a concorrência é o **paralelismo**. Há diferentes definições e relações com o conceito
de concorrência. Alguns autores falam sobre concorrência quando você executa sua aplicação com múltiplas threads
em um processador single-core, então simultâneamente você consegue ver quando a execução do seu programa é aparente.
Também, você pode falar sobre paralelismo quando você exeuta sua aplicação com mútliplas threads em um processador
multi-core ou em um computador com mais de um processador. Outros autores falam sobre concorrência quando as threads
da aplicação são executadas sem uma ordem predefinida, e falam sobre paralelismo quando você usa várias threads para
simplificar a solução de um problema, onde todas essas threads são executadas de forma ordenada.

Este capítulo apresenta várias receitas que mostram como executar operações básicas com threads usando a API Java 7.
Você irá ver como criar e executar threads em um programa Java, como controlar a execução delas, e como agrupar
algumas threads para manipulá-las como uma unidade.
