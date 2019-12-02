package com.ist.rc2;

class Player{
    enum civilStates {
        COOPERATOR,
        ROGUE
    }

	private double p;
    private double q;
    //when updating, we put them first in the futures
    //and in the beg of the round, swap them with p and q
    private double futurep;
    private double futureq;
    public double fitness;
    public int id;
    private int numNeighbors;
    private civilStates state = civilStates.COOPERATOR;
    private civilStates nextstate = civilStates.COOPERATOR;
    public Player(double _p, double _q,int _id){
        p = _p;
        futurep = p;
        q = _q;
        futureq = q;
        id = _id;
        fitness = 0;
    }
    
    public int getNumNeighbors(){
        return numNeighbors;
    }

    public void setNumNeighbors(int n){
        numNeighbors = n;
    }

    public double getP() {
		return p;
	}

	public void setP(double p) {
        this.p = p;
	}

	public double getQ() {
		return q;
	}

	public void setQ(double q) {
        this.q = q;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public void nextTurnRogue(){
        nextstate = civilStates.ROGUE;
    }

    public void nextTurnCooperator(){
        nextstate = civilStates.COOPERATOR;
    }

    public civilStates getCivilState(){
        return state;
    }

    public civilStates getNextCivilState(){
        return nextstate;
    }

    public void advanceToNextTurnState(){
        System.out.println("PLayer: advance to next turn state");
        state = nextstate;
        //the nextstate remains the same until opposite order
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

        state = nextstate;
        

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