package com.ist.rc2;

import com.ist.rc2.Ultimatum;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.JFrame;
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
        /*
        int initV = 50;
        int addE = 3;
        int iter = 1000;
        int round = 20000;
        
        BarabasiAlbertGenerator<Player, Integer> graph = new BarabasiAlbertGenerator<Player,Integer>(
        new gf(), new playerFactory('B'), new ef(),initV,addE,0,seedVertices);
        
        
        Graph<Player, Integer> g = graph.create();
        graph.evolveGraph(iter);
        System.out.println(g);
        
        Ultimatum ug = new Ultimatum(g);
        
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            System.out.println(p1.toString2());
        }
        
        System.out.println("playGame");
        
        //play game
        ug.runGame(round);
        
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            System.out.println(p1.toString2());
        }
        
        
        System.out.println(g);
        
        System.out.println(ug.averageP());
        */
        //PlotChart pDistribution = new PlotChart(ug.ListOfferDistributionP(200));
        //pDistribution.setVisible(true);
        

    }

}


