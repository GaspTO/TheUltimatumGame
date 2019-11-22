package com.ist.rc2;

import edu.uci.ics.jung.algorithms.generators.random.*;
import org.apache.commons.collections15.*;
import edu.uci.ics.jung.graph.*;

import java.util.HashSet;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;


public class Main 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        HashSet<Player> seedVertices = new HashSet<Player>();
        int initV = 5000;
        int addE = 7;
        int iter = 5000;

        BarabasiAlbertGenerator graph = new BarabasiAlbertGenerator<Player,Integer>(
        new gf(), new playerFactory('A'), new ef(),initV,addE,1023,seedVertices);


        Graph g = graph.create();
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

class playerFactory implements Factory<Player>{
    int count = 0;
    Random r = new Random(98822);
    char tactic = 'A'; //A, B or C
    public playerFactory(char _tactic){
    	tactic = _tactic;
    }

    public Player create(){
        double p_q = r.nextDouble();
       	if(tactic == 'A'){
        	return new Player(p_q,p_q,count++);
    	}
    	if(tactic == 'B'){
    		return new Player(p_q,1-p_q,count++);
    	}
    	return null;
    }

}

class ef implements Factory<Integer>{
    int count = 0;
    public Integer create(){
        return count++;
    }

}

class gf implements Factory<Graph<Player,Integer>>{

    public Graph<Player,Integer> create(){
        return new UndirectedSparseGraph<Player,Integer>();
    }

}

class Ultimatum{
    public Graph g;

    public Ultimatum(Graph _g){
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
        Random rand = new Random(2666);
        for( int i = 0; i<rounds; i++){ 
            if(((double)i/rounds * 100.0) % 5== 0){
                System.out.println("round:"+i );
            }
            for( Player p1:  (Collection<Player>) g.getVertices() ){
                p1.resetFitness();            
            }


            for( Player p1:  (Collection<Player>) g.getVertices() ){
                for( Player p2: (Collection<Player>) g.getNeighbors(p1) ){
                    Ultimatum.play(p1,p2);
                }
            }
        
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

}

class Player{
    public double p;
    public double q;
    public double fitness;
    public int id;
    public Player(double _p, double _q,int _id){
        p = _p;
        q = _q;
        id = _id;
        fitness = 0;
    }

    public void addFitness(double u){
        fitness = fitness + u;
    }
    
    public void resetFitness(){
        fitness = 0.0;
    }

    public void updatePQ(double _p, double _q){
        p = _p;
        q = _q;        
    }

    public String toString2(){
        String pp = String.format("%.2f", p);
        String qq = String.format("%.2f", q);
        String ffitness = String.format("%.2f", fitness);
        return "p1("+id+")"+ "["+pp+","+qq+"].fitness="+ffitness;
    }

    public String toString(){
        return ""+id;
    }
}
