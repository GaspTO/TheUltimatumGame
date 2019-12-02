package com.ist.rc2;

import java.util.Collection;
import java.util.HashSet;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.graph.Graph;

public class Main 
{
    public static void main( String[] args )
    {

        
        System.out.println("Taxation");

        HashSet<Player> seedVertices = new HashSet<Player>();
        int initV = 20;
        int addE = 2;
        int iter = 10000;
        int totaln = initV + iter;
        BarabasiAlbertGenerator<Player, Integer> graph = new BarabasiAlbertGenerator<Player,Integer>(
        new GraphFactory(), new PlayerFactory('A'), new EdgeFactory(),initV,addE,seedVertices);

        Graph<Player, Integer> g = graph.create();
        graph.evolveGraph(iter);

        Ultimatum ug = new Ultimatum(g);
        
        System.out.println("\t\taverage P: " + ug.averageP());
        System.out.println("\t\taverage Q: " + ug.averageQ());
        System.out.println("\t\taverage fitness: " + ug.averageFitness());
        System.out.println("\t\taverage inequality: " + ug.averageInequality());
        System.out.println("\t\taverage degree: " + ug.averageDegree());

        //ug.plotK();
        //ug.plotFitness(100);
        ug.plotP(30);
        int step=500;
        int N = 20;
        for(int i=0;i<N;i++){
            ug.runGame(step);
            ug.plotP(20);           
        }
        System.out.println("last");
        //ug.runGame(10000);
        //ug.plotP(30);
        
    }

}


