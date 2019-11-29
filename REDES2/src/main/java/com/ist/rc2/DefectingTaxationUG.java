package com.ist.rc2;

import java.util.Collection;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

class DefectingTaxationUG extends TaxationUG {
    double u; //average for normal distribution to turn a node rogue

    public DefectingTaxationUG(Graph<Player, Integer> _g, double _taxation, double _u){
        super(_g,_taxation,0);
        u = _u;
    }

    public DefectingTaxationUG(Graph<Player, Integer> _g, double _taxation,double _inneficiency, double _u){
        super(_g,_taxation,_inneficiency);
        u = _u;        
    }
    //NOTA BEGIN BY SWAP STATE
    public void updateStrategy(Player p1, Player p2, Double prob){
        Random r = new Random();
        if(p2.getCivilState() == Player.civilStates.COOPERATOR){
            if(r.nextDouble() < prob){
                System.out.println("I'ma immitate A GOOD GUY");
                p1.updateFuturePQ(p2.p,p2.q);
            }
        }
        else if(p2.getCivilState() == Player.civilStates.ROGUE){
            if(r.nextDouble() < prob){
                System.out.println("I'ma immitate A BAD GUY");
                p1.nextTurnRogue();
            }
        }
     }

    
    public void finalize(Random rand){
       
        


        for( Player p1:  (Collection<Player>) g.getVertices() ){     
            if(p1.getCivilState()==Player.civilStates.COOPERATOR){


                if(isRogueGaussian(p1,u)){
                    System.out.println("IM TURN ROGUE NEXT");
                    //p1.nextTurnRogue();
                }
                else if(g.getNeighborCount(p1) != 0){
                    int l = rand.nextInt(g.getNeighborCount(p1));
                    Player p2 = (Player) g.getNeighbors(p1).toArray()[l];
                    updateStrategy(p1,p2,selectProbability(p1,p2));
                }
            }
            else if(p1.getCivilState() == Player.civilStates.ROGUE){
                System.out.println("I'M A THIEF"); 
            }
        }      
    }
    


        


                
   

    public Boolean isRogueGaussian(Player p1, double _u){
        StandardDeviation sd = new StandardDeviation();
        double avNeighborFitness = 0.0;
        
        double[] db = new double[g.getNeighborCount(p1)];
        int i = 0;
        for( Player p2: (Collection<Player>) g.getNeighbors(p1)){
            avNeighborFitness = avNeighborFitness + p2.getFitness();
            db[i] = p2.getFitness();
            i = i + 1;
        }
        avNeighborFitness = avNeighborFitness/ g.getNeighborCount(p1);

        double o = sd.evaluate(db);//WHEN THERE'S NO DISTRIBUTION WE DON'T FEEL ALONE
        //SHOULD WE INCLUDE OUR DATA?
        if(o == 0){ return false;}
        NormalDistribution normal = new NormalDistribution(_u,o);
        Random r = new Random();
        double prob = normal.cumulativeProbability(avNeighborFitness/p1.getFitness());
        System.out.println("\t avNeighFitness="+ avNeighborFitness + " and mine=" + p1.getFitness() + " rate=" + avNeighborFitness/p1.getFitness() + "  prob=" + prob);   
        if(p1.fitness < avNeighborFitness){
            if(r.nextDouble() < prob){  
                return true;
            }
           return false;
        }
        else{
            return false;
        }
    }


}               //TODO: IMPLEMENT THE REST OF THE SIM