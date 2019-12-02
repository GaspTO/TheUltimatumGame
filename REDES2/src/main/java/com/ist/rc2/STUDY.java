package com.ist.rc2;

import java.util.HashSet;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import java.util.Collection;

public class STUDY {

    private Graph<Player,Integer> getG(int nodes){
        HashSet<Player> seedVertices = new HashSet<Player>();
        int initV = 3;
        int addE = 1;
        int iter = nodes-3;
        int totaln = initV + iter;
        BarabasiAlbertGenerator<Player, Integer> graph = new BarabasiAlbertGenerator<Player,Integer>(
        new GraphFactory(), new PlayerFactory('A'), new EdgeFactory(),initV,addE,seedVertices);

        Graph<Player, Integer> g = graph.create();
        graph.evolveGraph(iter);
        return  g;
    }
    
    public void TOLERANCE_AND_TAX_FOR_ROGUEZERO(){
        
        Boolean plot = false;
        
        double[] ineqs = {0,1,2,3,4,5,6,7,8,9,10,15,30,45,60,80,100,150,200,250,350,500,600,750,875,1000};
        for( double i: ineqs){
            double goodTax = -1;
            double inf = 0;
            double tax = 0.5;
            double sup = 1;
            int rogues = -1;
            int cooperators = -1;
            System.out.println("\n\n==========INEQUALITY(" + i + ")");
            while(sup - inf >= 0.05){
                DefectingTaxationUG ug = new DefectingTaxationUG(getG(100),tax,0,i,false);
                ug.runGame(100);
                rogues = ug.cooperatorANDrogueNumber()[1];
                cooperators = ug.cooperatorANDrogueNumber()[0];
                System.out.println("\t\t tax = " +tax + "  rogues= " + rogues + " cooperators= " + cooperators);
                if(rogues != 0){ //move up
                    inf = tax;
                    tax = tax + (sup - tax) / 2;
                }
                if(rogues == 0){
                    goodTax = tax;
                    sup = tax;
                    tax  = tax - (tax - inf)/2; 
                }
                
            }
            if(goodTax == -1){
                System.out.println("\tThe world is lost with inequality = " + i + "\n");   
            }
            else {
                System.out.println("\ttax= " + goodTax + " inequality = " + i + "\n");
            }
        }
        
        
    }

    public double TsimpleROUND(int rounds,double tax){
        
        //TaxationUG ug = new TaxationUG(getG(5000),tax);
        Ultimatum ug = new Ultimatum(getG(10000));

        System.out.println(ug);
        System.out.println("\t tax: " + tax);
        ug.runGame(rounds);
        System.out.println("\t\taverage P: " + ug.averageP());
        System.out.println("\t\taverage Q: " + ug.averageQ());
        System.out.println("\t\taverage fitness: " + ug.averageFitness());
        System.out.println("\t\taverage inequality: " + ug.averageInequality());
        System.out.println("\t\taverage degree: " + ug.averageDegree());

        /*
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            System.out.println("\n" + p1);
            for( Player p2:  (Collection<Player>) g.getNeighbors(p1) ){
                System.out.println("\t"+p2);
            }
        }
        */
        ug.plotP(10);
        ug.plotK();
        ug.plotFitness(100);
        
        /*
        System.out.println("\t\taverage cooperator fitness: " + ug.cooperatorFitness());
        System.out.println("\t\taverage rogue fitness: " + ug.rogueFitness());
        int[] k = ug.cooperatorANDrogueNumber();
        System.out.println("\t\tCOOPERATORS:" + k[0] + "    ROGUES:" + k[1]);
        System.out.println("\t\tTurned Rogue: " + ug.turnedRogueOfficial);
        System.out.println("\t\t\tTurned Cooperator: " + ug.turnedCooperatorOfficial);
        System.out.println("\n\n");
        */
        return ug.averageP();

    }



}