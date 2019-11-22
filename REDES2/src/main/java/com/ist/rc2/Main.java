package com.ist.rc2;

import java.util.Collection;
import java.util.HashSet;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.graph.Graph;


public class Main 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        HashSet<Player> seedVertices = new HashSet<Player>();
        int initV = 5000;
        int addE = 7;
        int iter = 5000;

        BarabasiAlbertGenerator<Player, Integer> graph = new BarabasiAlbertGenerator<Player,Integer>(
        new gf(), new playerFactory('A'), new ef(),initV,addE,1023,seedVertices);


        Graph<Player, Integer> g = graph.create();
        graph.evolveGraph(iter);
        System.out.println(g);

        Ultimatum ug = new Ultimatum(g);

        for( Player p1:  (Collection<Player>) g.getVertices() ){
            System.out.println(p1.toString2());
        }

       System.out.println("playGame");

        //play game
        ug.runGame(10000);

        for( Player p1:  (Collection<Player>) g.getVertices() ){
            System.out.println(p1.toString2());
        }


        System.out.println(g);

        System.out.println(ug.averageP());

     
    }



}

