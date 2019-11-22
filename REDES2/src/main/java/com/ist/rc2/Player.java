package com.ist.rc2;

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