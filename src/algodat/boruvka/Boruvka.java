/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algodat.boruvka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Boruvka {

    int numVertices;
    int numArestas;

    int numArvores = 0;
    int MSTCusto = 0;
    int numAresta;
    int CustoMGM = 0;

    public String getMSTCusto() {
        return "O custo da árvore geradora mínima é: " + MSTCusto;
    }
    Aresta Aresta[];
    subArvores subArvores[];
    int ConjuntoCusto[];

    class Aresta {

        int inicio;
        int destino;
        int Custo;
    }

    class subArvores {

        int pai;
        int rank;
    }

    /**
     * Construtor do grafo
     *
     * @param v Nó
     * @param e Aresta
     */
    Boruvka(int v, int e) {
        numVertices = v;
        numAresta = e;
        numArvores = v;
        Aresta = new Aresta[e];
        subArvores = new subArvores[v];
        ConjuntoCusto = new int[v];

        for (int i = 0; i < e; ++i) {
            Aresta[i] = new Aresta();
        }
        for (int i = 0; i < v; ++i) {
            subArvores[i] = new subArvores();
            subArvores[i].pai = i;
            subArvores[i].rank = 0;
            ConjuntoCusto[i] = -1;
        }
    }

    /**
     *
     * @param verticex Nó
     * @return gefundenen Nó
     */
    int Encontrar(int verticex) {
        subArvores tempSet = subArvores[verticex];
        while (subArvores[verticex].pai != verticex) {
            int paiVertex = subArvores[verticex].pai;
            verticex = subArvores[paiVertex].pai;
        }
        return tempSet.pai;
    }

    /**
     * @param inicio Ursprung
     * @param destino Ziel
     */
    //Função para combinasr duas árvores
    void uniao(int inicio, int destino) {

        int raizX = Encontrar(inicio);
        int raizY = Encontrar(destino);

        //Arvore conectada na raiz que possui o maior rank
        if (subArvores[raizX].rank < subArvores[raizY].rank) {
            subArvores[raizX].pai = raizY;
        } else if (subArvores[raizX].rank > subArvores[raizY].rank) {
            subArvores[raizY].pai = raizX;
        } /// Se  as raizes tiverem o mesmo rank, incrementa o rank do vertice1 e vertice2 vira filho de vertice1
        else {
            subArvores[raizY].pai = raizX;
            subArvores[raizX].rank++;
        }
    }

    //Funcão que calcula a arvore geradora mínima
    String boruvkaMST() {
        String AGM = "";//String que armanzena os resultados da arvore geradora mínima
        //Laço para agrupar as subAvores
        while (numArvores > 1) {
            //Liguar todas as arestas e atualizar o custo de cada componente
            for (int i = 0; i < numAresta; i++) {
                //Encontrar o vertice inicial e final de uma aresta
                int Vertice1 = Encontrar(Aresta[i].inicio);
                int Vertice2 = Encontrar(Aresta[i].destino);

                if (Vertice1 == Vertice2) {
                    continue;
                } else {
                    if (ConjuntoCusto[Vertice1] == -1
                            || Aresta[ConjuntoCusto[Vertice1]].Custo > Aresta[i].Custo) {
                        ConjuntoCusto[Vertice1] = i;
                    }

                    if (ConjuntoCusto[Vertice2] == -1
                            || Aresta[ConjuntoCusto[Vertice2]].Custo > Aresta[i].Custo) {
                        ConjuntoCusto[Vertice2] = i;
                    }
                }
            }

            //Adicionando melhor aresta a AGM
            for (int i = 0; i < numVertices; i++) {
                if (ConjuntoCusto[i] != -1) {
                    int Vertice1 = Encontrar(Aresta[ConjuntoCusto[i]].inicio);
                    int Vertice2 = Encontrar(Aresta[ConjuntoCusto[i]].destino);

                    if (Vertice1 == Vertice2) {
                        continue;
                    }
                    MSTCusto += Aresta[ConjuntoCusto[i]].Custo;
                    System.out.printf("Aresta:(%d-%d) Custo: %d\n",
                            Aresta[ConjuntoCusto[i]].inicio, Aresta[ConjuntoCusto[i]].destino,
                            Aresta[ConjuntoCusto[i]].Custo);
                    CustoMGM += Aresta[ConjuntoCusto[i]].Custo;
                    AGM += String.valueOf(Aresta[ConjuntoCusto[i]].inicio)
                            + " - " + String.valueOf(Aresta[ConjuntoCusto[i]].destino)
                            + "  Custo: " + String.valueOf(Aresta[ConjuntoCusto[i]].Custo) + "\n";

                    //Unir duas subArvores
                    uniao(Vertice1, Vertice2);
                    numArvores--;
                }
            }
            for (int i = 0; i < numVertices; i++) {
                ConjuntoCusto[i] = -1;

            }
        }
        AGM = "Custo total :" + CustoMGM + "\n" + AGM;
        return AGM;
    }

    public static void main(String[] args) throws IOException {
        //Criar grafos e salvar em txt 
        GeraGrafo acesso = new GeraGrafo();
        acesso.GeraGrafo();
        //----------------- Adicionar arestas
        String path = "entrada.txt";
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        Boruvka graph = null;//Declarando variavel de grafo
        String linha = "";
        String ArestaTxt[] = new String[3];
        int vertice = 0;
        int aresta = 0;
        int numAresta = 0;//variavel que conta o numero de arestas 
        int count = 0;
        while (true) {
            linha = buffRead.readLine();
            if (linha != null) {
                if (linha.equals("")) {
                    break;
                }
                ArestaTxt = linha.split(" ");//Pegar os valores de vertice, aresta, custo, quantidade de vertices e arestas e coloca em um vetor
                //for(int i=0; i<ArestaTxt.length;i++)System.out.print(" "+ArestaTxt[i]+" ");
                //System.out.println("");
                if (count == 0) {
                    vertice = Integer.valueOf(ArestaTxt[0]);//Ler quantidade de vértice do txt
                }
                if (count == 1) {
                    aresta = Integer.valueOf(ArestaTxt[0]);;//Ler quantidade de aresta do txt
                }
                if (count == 2) {
                    graph = new Boruvka(vertice, aresta);//cria novo grafo 
                    graph.Aresta[numAresta].inicio = Integer.valueOf(ArestaTxt[0]);
                    graph.Aresta[numAresta].destino = Integer.valueOf(ArestaTxt[1]);
                    graph.Aresta[numAresta].Custo = Integer.valueOf(ArestaTxt[2]);
                    numAresta++;
                }
                if (count > 2 && numAresta <= aresta) {

                    graph.Aresta[numAresta].inicio = Integer.valueOf(ArestaTxt[0]);
                    graph.Aresta[numAresta].destino = Integer.valueOf(ArestaTxt[1]);
                    graph.Aresta[numAresta].Custo = Integer.valueOf(ArestaTxt[2]);
                    numAresta++;
                    count++;
                }
                count++;
            } else {
                break;
            }

        }
        System.out.println("Vertice " + vertice + " Aresta " + aresta);
        //-------------Medir tempo de execução do algoritmo-----------------
        long tempoInicial = System.currentTimeMillis();
        //--------------------------------------------------------------
        String AGM = graph.boruvkaMST();
        System.out.println(graph.getMSTCusto());
        long tempofinal = System.currentTimeMillis();
        tempofinal = tempofinal - tempoInicial;
        float TempoTotal = (float) ((tempofinal / (1000)));
        System.out.println("o metodo executou em " + TempoTotal + " Segundo(s).");
        NewJFrame ShowInterface = new NewJFrame(vertice, aresta, AGM, String.valueOf(TempoTotal));
        ShowInterface.setVisible(true);

    }
}
