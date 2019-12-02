package com.ist.rc2;

import java.util.Collection;
import java.util.Random;
import edu.uci.ics.jung.graph.Graph;

class Ultimatum extends Game {
   
    public boolean acceptEOffers = true;
    public Ultimatum(Graph<Player, Integer> _g){
        super(_g);
    }

    //what do the players do when they interact?
    public void playerInteraction(Player p1, Player p2){
        //System.out.println(p1.id + " oferece " + p1.p + " a " + p2.id + " este q=" + p2.q);
        if(p2.getQ() <= p1.getP()){
            //System.out.println("\tlogo aceita");
            p2.addFitness(p1.getP());
            p1.addFitness(1.0-p1.getP());
        }
    }

    //how is strategy of p1 updated? (with a probability)
    public void updateStrategy(Player p1, Player p2, Double prob){
        Random r = new Random();
       if(r.nextDouble() < prob){
            //p1.updateFuturePQ(p2.getP(),p2.getQ());
            p1.updatePQ(p2.getP(),p2.getQ());
       }
    }

    //based on p1 and p2 characteristics there will be a distribution to update strategy
    public double selectProbability(Player p1, Player p2){
        int max;
        if(p1.getNumNeighbors() > p1.getNumNeighbors()){
            max = p1.getNumNeighbors();
        }
        else{
            max = p2.getNumNeighbors();
        }
        if(p1.fitness < p2.fitness){
           return (p2.fitness - p1.fitness)/(2*max);
        }
        else{
            return 0.0;
        }
    }

    //fitness back to 0 and update p and q
    public void init( Random rand){
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            p1.resetFitness();
            //p1.swap();           
        }
    }

    
    public void develop(Random rand){
        //for each p1 play against all the p2 neighbors
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            for( Player p2: (Collection<Player>) g.getNeighbors(p1) ){
                playerInteraction(p1,p2);
            }
        }
    }

    //update strategies
    public void finalize(Random rand){
        rand = new Random();
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            if(g.getNeighborCount(p1) != 0){
                int l = rand.nextInt(g.getNeighborCount(p1));
                Player p2 = (Player) g.getNeighbors(p1).toArray()[l];
                updateStrategy(p1,p2,selectProbability(p1,p2));
            }
        }
    }   


    
    
}