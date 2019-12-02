package com.ist.rc2;

import edu.uci.ics.jung.graph.Graph;
import java.util.Collection;
import java.util.Random;

/* RUN THE GAME AND THEN PLOT IT*/


abstract class Game {
    public Graph<Player, Integer> g;
    private Boolean shutup = true;
    private int pplot = 0;
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

        Random rand = null;
        for( int i = 0; i<rounds; i++){ //over rounds
            //System.out.println("RAND = " + i);
            if( shutup == false && ((double)i/rounds * 100.0) % 5== 0){
                System.out.println("===========round=========:"+i );
            }
            init(rand);
            develop(rand);
            finalize(rand);
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
            int box = PlotChart.decideBox(N,p1.getP());
            B[box] = B[box]+1;
        }
        for(int i=0; i<N+1; i++){
            B[i] = B[i]/vNumber*100;
        }

        PlotChart.plot(A,B,"offer-p distribution","D(p)(%)","p(%)","Offer-p Chart " + pplot);
        pplot ++;
    }

        //CREATES THE PLOT by calling plotchart
    //N: number of x's or "boxes" in graph
    public void plotQ(int N){ 
        int vNumber = g.getVertices().size();
        double[] A = new double[N+1];
        double[] B = new double[N+1];
        for(int i = 0; i< N+1; i++){
            double ii = (double) i;
            A[i] = ii/N*100;
            B[i] = 0.0;

        }
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            int box = PlotChart.decideBox(N,p1.getQ());
            B[box] = B[box]+1;
        }
        for(int i=0; i<N+1; i++){
            B[i] = B[i]/vNumber*100;
        }

        PlotChart.plot(A,B,"offer-p distribution","D(p)(%)","q(%)","Offer-p Chart");
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
            total = total + p1.getP();
            count ++;     
        }
        return total/count;
    }


    //getter
    public double averageQ(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + p1.getQ();
            count ++;     
        }
        return total/count;
    }

    //getter
    public double averageDegree(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + g.getNeighborCount(p1);
            count ++;     
        }
        return total/count;
    }

    public double averageInequality(){
        double total = 0.0;
        int count = 0;
        double neighAVFitness;
        for( Player p1:  (Collection<Player>) g.getVertices() ){ 
            double neighborFitness = 0.0;
            if(p1.getNumNeighbors() == 0){
                continue;
            }
            for( Player p2: (Collection<Player>) g.getNeighbors(p1)){
                neighborFitness = neighborFitness + p2.getFitness();
            }
            neighAVFitness = neighborFitness/p1.getNumNeighbors();
            total = total + neighAVFitness/p1.getFitness();
            count = count + 1;
        }
        return total/count;  
    }
}