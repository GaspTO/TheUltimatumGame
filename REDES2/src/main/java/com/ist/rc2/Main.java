package com.ist.rc2;

import java.util.Collection;
import java.util.HashSet;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.graph.Graph;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Main 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        HashSet<Player> seedVertices = new HashSet<Player>();
        int initV = 100;
        int addE = 10;
        int iter = 5000;
        int totaln = initV + iter;
        BarabasiAlbertGenerator<Player, Integer> graph = new BarabasiAlbertGenerator<Player,Integer>(
        new GraphFactory(), new PlayerFactory('A'), new EdgeFactory(),initV,addE,1023,seedVertices);

        Graph<Player, Integer> g = graph.create();
        graph.evolveGraph(iter);
        Ultimatum ug = new Ultimatum(g,true);
        ug.runGame(200);
        ug.plot(10,totaln);
        System.out.println("average P: " + ug.averageP());
        System.out.println("average fitness: " + ug.averageFitness());
 
    }



}

