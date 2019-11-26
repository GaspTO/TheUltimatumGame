package com.ist.rc2;

import edu.uci.ics.jung.graph.Graph;
import java.util.Collection;
import java.util.Random;

abstract class Game {
    public Graph<Player, Integer> g;

    public Game(Graph<Player, Integer> _g){
        g = _g;
    }

    //The game calls (Init then develop then finalize) * number of rounds
    public abstract void init(Random rand);
    public abstract void develop(Random rand);
    public abstract void finalize(Random rand);

    //runs the games, loops over all vertexes and does stuff
    public void runGame(int rounds){
        Random rand = new Random();
        for( int i = 0; i<rounds; i++){ //over rounds
            if(((double)i/rounds * 100.0) % 5== 0){
                System.out.println("===========round=========:"+i );
            }
            init(rand);
            develop(rand);
            finalize(rand);
        }
    }
    
    //CREATES THE PLOT by calling LineChartEx.plot
    public void plot(int N, int vNumber){ 
        double[] A = new double[N+1];
        double[] B = new double[N+1];
        for(int i = 0; i< N+1; i++){
            double ii = (double) i;
            A[i] = ii/N;
            B[i] = 0.0;

        }
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            int box = PlotChart.decideBox(N,p1.p);
            B[box] = B[box]+1;
        }
        for(int i=0; i<N+1; i++){
            B[i] = B[i]/vNumber*100;
        }

        PlotChart.plot(A,B);
    }

    //getter
    public double averageFitness(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + p1.fitness;
            count ++;      
        }
        return total/count;
    }  

    //getter
    public double averageP(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + p1.p;
            count ++;     
        }
        return total/count;
    }

}