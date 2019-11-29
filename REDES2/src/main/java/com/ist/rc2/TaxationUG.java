package com.ist.rc2;

import java.util.Collection;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;


class TaxationUG extends Ultimatum {
    public double taxation;
    public double inneficiency;

    public TaxationUG(Graph<Player, Integer> _g, double _taxation){
        super(_g);
        taxation = _taxation;
        inneficiency = 0;
    }

    public TaxationUG(Graph<Player, Integer> _g, double _taxation, double _inneficiency){
        super(_g);
        taxation = _taxation;
        inneficiency = _inneficiency;
    }
    
    public double taxFitness(Player p1){
        double taxed = p1.fitness*taxation;
        double initfitness = p1.fitness;
        p1.addFitness(-taxed);
        if(initfitness < p1.fitness || p1.fitness < 0 ){
            System.out.println("erro, update taxFitness/TaxationUG weird");
            System.out.println("init= "+initfitness + " and taxed= " + taxed);

        }
        return taxed*(1-inneficiency);
    }

    //play and tax the plays and redistribute
    public void develop(Random rand){
        //for each p1 play against all the p2 neighbors
        super.develop(rand);
        
        //taxation
        double total = 0.0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + taxFitness(p1);
        }
        
        double give = total/g.getVertexCount();

        //redistribution of taxes
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            p1.addFitness(give);
        }

    }
 

}