package com.ist.rc2;

import java.util.Random;

import org.apache.commons.collections15.Factory;

class playerFactory implements Factory<Player>{
    int count = 0;
    Random r = new Random(98822);
    char tactic;
    public playerFactory(char _tactic){
    	tactic = _tactic;
    }

    public Player create(){
        double p_q = Math.round(r.nextDouble()*20.0)/20.0;
       	if(tactic == 'A'){
        	return new Player(p_q,p_q,count++);
    	}
    	if(tactic == 'B'){
    		return new Player(p_q,1-p_q,count++);
    	}
    	return null;
    }

}