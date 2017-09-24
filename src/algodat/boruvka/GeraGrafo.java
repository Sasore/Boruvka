/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algodat.boruvka;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.JOptionPane;

public class GeraGrafo {

    void GeraGrafo() throws FileNotFoundException {

        // Ler argumentos da linha de comandos
        int QtdeVertice = 0;//nos
        long QtdeArestas = 0;//arestas
        int CustoMaximo = 0;//custo
        // Novo gerador de numeros (pseudo) aleatorios
        Random GeraAleatorio = new Random();
        //----------------------------------------------
        GeraGrafo acesso = new GeraGrafo();//instancia uma chave para acesso as funções desta classe
        //----------------------------------------------
        //Usuario entra com o numero de vertices
        while (QtdeVertice < 3) {
            QtdeVertice = Integer.valueOf(JOptionPane.showInputDialog("Digite o número de vertices(>3)", null));
        }
        //-------Abre uma tela de aviso-----------------------------------------
        MostraMensagem mensagem = new MostraMensagem();
        mensagem.setVisible(true);        //Abre uma tela com a mensagem "Criando arestas"
        //-----------Gerar número de arestas-----------------------------------
        QtdeArestas = QtdeVertice - 1;
        QtdeArestas = acesso.Somatorio(QtdeArestas);//Calcula o numero máximo de arestas
        QtdeArestas = GeraAleatorio.nextInt((int) QtdeArestas);//Gera o numero aleatorio de arestas <qtde max. aresta
        //Se o numero de arestas gerados for menor que o numero de vertice
        if (QtdeArestas < QtdeVertice) {
            QtdeArestas = QtdeVertice - 1;
        }
        //----------Gerar custo máximo aleatoriamente--------------------------
        CustoMaximo = GeraAleatorio.nextInt((int) (QtdeVertice + QtdeArestas));//Gera o custo máximo aleatoriamente

        // Criar lista de adjacencias (usando conjuntos)
        ArrayList<Set<Integer>> adj = new ArrayList<Set<Integer>>();
        for (int i = 0; i <= QtdeVertice; i++) {
            adj.add(new HashSet<Integer>());
        }

        // Criar spanning tree para garantir conectividade
        boolean s[] = new boolean[QtdeVertice + 1];
        s[0] = true;
        int a, b, QtdeArestaCriada = 0;
        for (int i = 1; i < QtdeVertice; i++) {
            do {
                a = GeraAleatorio.nextInt(QtdeVertice);
            } while (s[a]);//pego um vertice que ainda não foi escolhido
            do {
                b = GeraAleatorio.nextInt(QtdeVertice);
            } while (!s[b]);//Ligar o vertice B a um vertice que já é conhecido (menor do que ele)
            s[a] = true;
            adj.get(a).add(b);
            adj.get(b).add(a);
            QtdeArestaCriada++;
        }

        // Criar restantes arestas
        while (QtdeArestaCriada <= QtdeArestas) {
            do {
                a = GeraAleatorio.nextInt(QtdeVertice);
                b = GeraAleatorio.nextInt(QtdeVertice);
            } while (a == b || adj.get(a).contains(b));
            adj.get(a).add(b);
            adj.get(b).add(a);
            QtdeArestaCriada++;
        }

        // Imprimir grafo
        System.out.println(QtdeVertice);
        System.out.println(QtdeArestaCriada);//quantidade de arestas geradas
        //--Gerar entrada.txt
        String nomeDoArquivo = "entrada.txt";
        PrintWriter gravarArq = new PrintWriter(nomeDoArquivo);
        gravarArq.println(String.valueOf(QtdeVertice));
        gravarArq.println(String.valueOf(QtdeArestaCriada));
        for (int i = 0; i < QtdeVertice; i++) {
            for (int j : adj.get(i)) // Imprimir uma vez apenas cada aresta
            {
                if (i < j) {
                    mensagem.AtualizaStatus(i + " " + j + " " + (GeraAleatorio.nextInt(CustoMaximo) + 1));
                    System.out.println(i + " " + j + " " + (GeraAleatorio.nextInt(CustoMaximo) + 1));
                    gravarArq.println(String.valueOf(i + " " + j + " " + (GeraAleatorio.nextInt(CustoMaximo) + 1)));
                }
            }
        }
        mensagem.setVisible(false);        //Fecha tela 'criando arestas'
        gravarArq.close();
    }

    public long Somatorio(long numero) {
        long sum = numero;
        while (numero > 2) {
            sum = sum + (numero - 1);
            numero--;
        }
        return sum;
    }
}
