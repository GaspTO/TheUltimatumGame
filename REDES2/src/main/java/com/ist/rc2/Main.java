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
import org.apache.commons.math3.distribution.NormalDistribution;

public class Main 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        NormalDistribution normal = new NormalDistribution(0,0.5);
        System.out.println(normal.cumulativeProbability(0.5*0.5));


        HashSet<Player> seedVertices = new HashSet<Player>();
        int initV = 100;
        int addE = 7;
        int iter = 5000;
        int totaln = initV + iter;
        BarabasiAlbertGenerator<Player, Integer> graph = new BarabasiAlbertGenerator<Player,Integer>(
        new GraphFactory(), new PlayerFactory('A'), new EdgeFactory(),initV,addE,1023,seedVertices);

        Graph<Player, Integer> g = graph.create();
        graph.evolveGraph(iter);
        //Ultimatum ug = new Ultimatum(g,true);
        //TaxationUG ug = new TaxationUG(g, 0);
        DefectingTaxationUG ug = new DefectingTaxationUG(g,0,0,10);
        ug.runGame(1000);
        ug.plotP(10);
        ug.plotK();
        ug.plotFitness(100);
        
        System.out.println("average P: " + ug.averageP());
        System.out.println("average fitness: " + ug.averageFitness());
        System.out.println("average cooperator fitness: " + ug.cooperatorFitness());
        System.out.println("average rogue fitness: " + ug.rogueFitness());
        int[] k = ug.cooperatorANDrogueNumber();
        System.out.println("COOPERATORS:" + k[0] + "    ROGUES:" + k[1]);
        System.out.println("Turned Rogue: " + ug.turnedRogueOfficial);
        System.out.println("Turned Cooperator: " + ug.turnedCooperatorOfficial);
        
    }

}


