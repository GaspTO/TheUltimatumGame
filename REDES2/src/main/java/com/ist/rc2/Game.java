package com.ist.rc2;

import edu.uci.ics.jung.graph.Graph;
import java.util.Collection;
import java.util.Random;

/* RUN THE GAME AND THEN PLOT IT*/


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
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            p1.setNumNeighbors(g.getNeighborCount(p1));
        }

        Random rand = new Random();
        for( int i = 0; i<rounds; i++){ //over rounds
            //System.out.println("RAND = " + i);
            if(((double)i/rounds * 100.0) % 5== 0){
                System.out.println("===========round=========:"+i );
            }
            init(rand);
            develop(rand);
            if(i!=rounds-1){ //don't do the last one.
                finalize(rand);
            }
        }
    }
    
    //CREATES THE PLOT by calling plotchart
    //N: number of x's or "boxes" in graph
    public void plotP(int N){ 
        int vNumber = g.getVertices().size();
        double[] A = new double[N+1];
        double[] B = new double[N+1];
        for(int i = 0; i< N+1; i++){
            double ii = (double) i;
            A[i] = ii/N*100;
            B[i] = 0.0;

        }
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            int box = PlotChart.decideBox(N,p1.p);
            B[box] = B[box]+1;
        }
        for(int i=0; i<N+1; i++){
            B[i] = B[i]/vNumber*100;
        }

        PlotChart.plot(A,B,"offer-p distribution","D(p)(%)","p(%)","Offer-p Chart");
    }

    //CREATES THE PLOT by calling plotchart
    public void plotK(){
        int N = 0;
        int vNumber = g.getVertices().size();
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            if(N < g.getNeighborCount(p1)){
                N = g.getNeighborCount(p1);
            }
        }
        N = N + 1;
        
        System.out.println(N);
        double[] A = new double[N+1];
        double[] B = new double[N+1];
        for(int i = 0; i< N+1; i++){
            double ii = (double) i;
            A[i] = ii*100;
            B[i] = 0.0;

        }
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            int box = g.getNeighborCount(p1);
            B[box] = B[box]+1;
        }
        for(int i=0; i<N+1; i++){
            B[i] = B[i]/vNumber*100;
        }

        PlotChart.plot(A,B,"degree distribution","D(k)(%)","k(%)", "Degree Chart");
    }

    //CREATES THE PLOT by calling plotchart
    //N: number of x's or "boxes" in graph
    public void plotFitness(int N){ 
        int vNumber = g.getVertices().size();
        double[] A = new double[N+1];
        double[] B = new double[N+1];
        for(int i = 0; i< N+1; i++){
            double ii = (double) i;
            A[i] = ii/N;
            B[i] = 0.0;

        }
        double maxFitness = 0.0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            if(maxFitness < p1.getFitness()){
                maxFitness = p1.getFitness();
            }
        }

        for( Player p1:  (Collection<Player>) g.getVertices() ){
            int box = PlotChart.decideBox(N,p1.getFitness()/maxFitness);
            B[box] = B[box]+1;
        }
        for(int i=0; i<N+1; i++){
            B[i] = B[i]/vNumber*100;
        }

        PlotChart.plot(A,B,"fitness distribution","D(fitness)(%)","fitness(%)", "Fitness Chart");
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


    //getter
    public double averageQ(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + p1.q;
            count ++;     
        }
        return total/count;
    }
}