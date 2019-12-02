package com.ist.rc2;

import java.util.Random;

import org.apache.commons.collections15.Factory;

class PlayerFactory implements Factory<Player>{
    int count = 0;
    Random r = new Random(177);
    char tactic = 'A'; //A, B or C
    public PlayerFactory(char _tactic){
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
        if(tactic == 'C'){
            return new Player(p_q, r.nextDouble(),count++);
        }
    	return null;
    }

}