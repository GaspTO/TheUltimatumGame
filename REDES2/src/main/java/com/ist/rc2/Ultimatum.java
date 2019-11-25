package com.ist.rc2;

import java.util.Collection;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;

class Ultimatum implements Game {
    public Graph<Player, Integer> g;

    public Ultimatum(Graph<Player, Integer> _g){
        g = _g;
    }

    public static void play(Player p1, Player p2){
        //p1 offers to p2
        if(p2.q <= p1.p){
            p2.addFitness(p1.p);
            p1.addFitness(1.0-p1.p);
        }
    }

    public static void updateStrategy(Player p1, Player p2, Double prob){
        Random r = new Random(92777);
        if(r.nextDouble() < prob){
            p1.updatePQ(p2.p,p2.q);
        }
    }

    public double selectProbability(Player p1, Player p2){
        int max = ((g.getNeighborCount(p1) > g.getNeighborCount(p2) ) ? g.getNeighborCount(p1) : g.getNeighborCount(p2));
        if(p1.fitness < p2.fitness){
           return (p2.fitness - p1.fitness)/(2*max);
        }
        else{
            return 0.0;
        }
    }

    public void runGame(int rounds){
        Random rand = new Random(26663);
        for( int i = 0; i<rounds; i++){ //over rounds
            if(((double)i/rounds * 100.0) % 5== 0){
                System.out.println("round:"+i );
            }

            //for each player reset fitness
            for( Player p1:  (Collection<Player>) g.getVertices() ){
                p1.resetFitness();            
            }

            //for each p1 play against all the p2 neighbors
            for( Player p1:  (Collection<Player>) g.getVertices() ){
                for( Player p2: (Collection<Player>) g.getNeighbors(p1) ){
                    Ultimatum.play(p1,p2);
                }
            }
        
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


   public double averageFitness(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + p1.fitness;
            count ++;       
        }
        return total/count;
    }  

   public double averageP(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + p1.p;
            count ++;     
        }
        return total/count;
    }

    public double averageK(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + g.getNeighborCount(p1);
            count ++;     
        }
        return total/count;
    }  


    
    // TODO: YU
    public void findOfferDistribution(){

    }    
}