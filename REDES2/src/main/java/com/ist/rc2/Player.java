package com.ist.rc2;

class Player{
    public double p;
    public double q;
    //when updating, we put them first in the futures
    //and in the beg of the round, swap them with p and q
    public double futurep;
    public double futureq;
    public double fitness;
    public int id;
    public Player(double _p, double _q,int _id){
        p = _p;
        futurep = p;
        q = _q;
        futureq = q;
        id = _id;
        fitness = 0;
    }

    public void addFitness(double u){
        fitness = fitness + u;
    }

    public void swap(){
        if(futurep != -1.0){
            p = futurep;
        }
        if(futureq != -1.0){
            q = futureq;  
        }
        
        futurep = -1.0;
        futureq = -1.0;
    }
    
    public void resetFitness(){
        fitness = 0.0;
    }

    public void updatePQ(double _p, double _q){
        p = _p;
        q = _q;        
    }

    public void updateFuturePQ(double _p, double _q){
        futurep = _p;
        futureq = _q;
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