package com.ist.rc2;

import java.util.Collection;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;


class TaxationUG extends Ultimatum {
    public double taxation;

    public TaxationUG(Graph<Player, Integer> _g, Boolean _imitation, double _taxation){
        super(_g,_imitation);
        taxation = _taxation;
    }
    /*
    public double taxFitness(Player p1){
        double taxed = p1.fitness*taxation;
        double initfitness = p1.fitness;
        p1.addFitness(-taxed);
        if(initfitness < p1.fitness || p1.fitness < 0 ){
            System.out.println("erro, update taxFitness/TaxationUG weird");
            System.out.println("init= "+initfitness + " and taxed= " + taxed);

        }
        return taxed;
    }

    public void runGame(int rounds){
        Random rand = new Random(91772);
        for( int i = 0; i<rounds; i++){ //over rounds
            if(((double)i/rounds * 100.0) % 5== 0){
                System.out.println("round:"+i );
            }

            //for each player reset fitness
            for( Player p1:  (Collection<Player>) g.getVertices() ){
                p1.resetFitness();
                p1.swap();            
            }

            //for each p1 play against all the p2 neighbors
            for( Player p1:  (Collection<Player>) g.getVertices() ){
                for( Player p2: (Collection<Player>) g.getNeighbors(p1) ){
                    Ultimatum.playInteraction(p1,p2);
                }
            }

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
        
            //for each p1, choose a random neightboard and update strategy
            if(imitation == true){
                for( Player p1:  (Collection<Player>) g.getVertices() ){
                    //getsCollections.TransformsToArray[ Picks a random neighbor ]
                    if(g.getNeighborCount(p1) != 0){
                        int l = rand.nextInt(g.getNeighborCount(p1));
                        Player p2 = (Player) g.getNeighbors(p1).toArray()[l];
                        Ultimatum.updateStrategy(p1,p2,selectProbability(p1,p2));
                    }
                }
            }
        }
        


    }
*/

}