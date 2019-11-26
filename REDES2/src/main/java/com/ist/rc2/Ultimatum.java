package com.ist.rc2;

import java.util.Collection;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;

class Ultimatum extends Game {
    
    public Boolean imitation;
    public Ultimatum(Graph<Player, Integer> _g,Boolean _imitation){
        super(_g);
        imitation = _imitation;
    }

    //what do the players do when they interact?
    public static void playerInteraction(Player p1, Player p2){
        if(p2.q <= p1.p){
            p2.addFitness(p1.p);
            p1.addFitness(1.0-p1.p);
        }
    }

    //how is strategy of p1 updated? (with a probability)
    public static void updateStrategy(Player p1, Player p2, Double prob){
       Random r = new Random();
       if(r.nextDouble() < prob){
            p1.updateFuturePQ(p2.p,p2.q);
        }
    }

    //based on p1 and p2 characteristics there will be a distribution to update strategy
    public double selectProbability(Player p1, Player p2){
        int max = ((g.getNeighborCount(p1) > g.getNeighborCount(p2) ) ? g.getNeighborCount(p1) : g.getNeighborCount(p2));
        if(p1.fitness < p2.fitness){
           return (p2.fitness - p1.fitness)/(2*max);
        }
        else{
            return 0.0;
        }
    }

    public void init( Random rand){
        //for each player reset fitness
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            p1.resetFitness();
            p1.swap();           
        }
    }

    public void develop(Random rand){
        //for each p1 play against all the p2 neighbors
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            for( Player p2: (Collection<Player>) g.getNeighbors(p1) ){
                Ultimatum.playerInteraction(p1,p2);
            }
        }
    }

    public void finalize(Random rand){
        if(imitation == true){
        //for each p1, choose a random neightboard and update strategy
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