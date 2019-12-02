package com.ist.rc2;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

class DefectingTaxationUG extends TaxationUG {
    double u; //average for normal distribution to turn a node rogue
    private int turnedRogue = 0;
    public int turnedRogueOfficial = 0;
    private int turnedCooperator = 0;
    public int turnedCooperatorOfficial = 0;
    private double std = (double)1/(double)3; //the std of the stolen normal
    private Boolean rogueImitation;
    private double oddRobbed = 0.0022;
    
    
    public DefectingTaxationUG(Graph<Player, Integer> _g, double _taxation, double _u, Boolean _rogueImitaton){
        super(_g,_taxation,0);
        u = _u;
        rogueImitation = _rogueImitaton;
    }

    public DefectingTaxationUG(Graph<Player, Integer> _g, double _taxation, double _inneficiency, double _u, Boolean _rogueImitation){
        super(_g,_taxation,_inneficiency);
        u = _u;        
        rogueImitation = _rogueImitation;
    }

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
            if(p1.getCivilState() == Player.civilStates.COOPERATOR){
                int box = PlotChart.decideBox(N,p1.getP());
                B[box] = B[box]+1;
            }
        }
        for(int i=0; i<N+1; i++){
            B[i] = B[i]/vNumber*100;
        }

        PlotChart.plot(A,B,"offer-p distribution","D(p)(%)","p(%)","Offer-p Chart");
    }

    //getter
    public int[] cooperatorANDrogueNumber(){
        int[] num = new int[2];
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            if(p1.getCivilState() == Player.civilStates.COOPERATOR){
                num[0] = num[0] + 1;
            }
            else{
                num[1] = num[1] + 1;
            }  
        }
        return num;
    }

    public double rogueFitness(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            if(p1.getCivilState() == Player.civilStates.ROGUE){
                //System.out.println(p1.fitness);
                total = total + p1.fitness;
                count ++;
            }
        }
        return total/count;  
    }

    public double cooperatorFitness(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            if(p1.getCivilState() == Player.civilStates.COOPERATOR){
                total = total + p1.fitness;
                count ++;
            }
        }
        
        return total/count;  
    }
    
    //getter
    public double averageP(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            if(p1.getCivilState() == Player.civilStates.COOPERATOR){
                total = total + p1.getP();
                count ++;
            } 
        }
        return total/count;
    }

    //getter
    public double averageQ(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            if(p1.getCivilState() == Player.civilStates.COOPERATOR){
                total = total + p1.getQ();
                count ++;
            } 
        }
        return total/count;
    }


    //NOTA BEGIN BY SWAP STATE
    public void updateStrategy(Player p1, Player p2, Double prob){
        Random r = new Random();
        if(p1.getCivilState() == Player.civilStates.COOPERATOR){
            if(p2.getCivilState() == Player.civilStates.COOPERATOR){
                if(r.nextDouble() < prob){
                    p1.updateFuturePQ(p2.getP(),p2.getQ());
                }
            }
            
            else if( rogueImitation == true && p2.getCivilState() == Player.civilStates.ROGUE){
                if(r.nextDouble() < prob){
                    p1.nextTurnRogue();
                    turnedRogue = turnedRogue + 1;
                }
            }
        }
        
        else if(p1.getCivilState() == Player.civilStates.ROGUE){
            if(p2.getCivilState() == Player.civilStates.COOPERATOR){
                if(r.nextDouble() < prob){
                    p1.updateFuturePQ(p2.getP(),p2.getQ());
                    p1.nextTurnCooperator();
                    turnedCooperator = turnedCooperator + 1;
                }
            }
        }
     }

    
     //ATENTION, THIS SHOULD BE USED AFTER EVERYONE LOOKED AT EVERYONE ELSE'S FITNESS
    public void steal(Player p1, Player p2, double percentage){
        if(percentage<=0){
            return;
        }
        if(percentage>1){
            percentage = 1;
        }
        double stolen = p2.getFitness()*percentage;
        p2.addFitness(-stolen);
        p1.addFitness(stolen);
    }

    public void init(Random rand){
        turnedRogue = 0;
        turnedCooperator = 0;
        super.init(rand);
    }

    public void develop(Random rand){
        //for each p1 play against all the p2 neighbors
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            if(p1.getCivilState() == Player.civilStates.COOPERATOR){
                for( Player p2: (Collection<Player>) g.getNeighbors(p1) ){
                    if(p2.getCivilState() == Player.civilStates.COOPERATOR){
                        playerInteraction(p1,p2);
                    }
                }
            }
        }
        
        //taxation
        double total = 0.0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            if(p1.getCivilState() == Player.civilStates.ROGUE){
                if(p1.getFitness() != 0){
                    System.out.println("not suppose to happen");
                }
            }
            total = total + taxFitness(p1);
        }
        
        double give = total/g.getVertexCount();


        for( Player p1:  (Collection<Player>) g.getVertices() ){
            p1.addFitness(give);
        }
 

       
        //We only steal after taxe. ---MENTION THIS IN REPORT---
        for( Player p1:  (Collection<Player>) g.getVertices() ){ 
            
            if(p1.getCivilState() == Player.civilStates.ROGUE){
                ArrayList<Player> neigh = new ArrayList<Player>( (Collection<Player>) g.getNeighbors(p1));
                //steal(p1,p2,rand.nextGaussian()*std);
                steal(p1,neigh.get(rand.nextInt(neigh.size())),rand.nextDouble());                
            }
        }        
     }


    public void finalize(Random rand){
        for( Player p1:  (Collection<Player>) g.getVertices() ){     
            if(p1.getCivilState()==Player.civilStates.COOPERATOR){
                if(isRogueGaussian(p1,u)){ //MAYBE ILL BECOME ROGUE
                    p1.nextTurnRogue();
                    turnedRogue = turnedRogue + 1;

                }
                else if(p1.getNumNeighbors() != 0){ //IF I DON'T ILL IMITATE SOMEONE
                    int l = rand.nextInt(p1.getNumNeighbors());
                    Player p2 = (Player) g.getNeighbors(p1).toArray()[l];
                    updateStrategy(p1,p2,selectProbability(p1,p2));
                }
            }

            else if(p1.getCivilState() == Player.civilStates.ROGUE){ 
                if(p1.getNumNeighbors() != 0){ //I'LL IMITATE SOMEONE
                    int l = rand.nextInt(p1.getNumNeighbors());
                    Player p2 = (Player) g.getNeighbors(p1).toArray()[l];
                    updateStrategy(p1,p2,selectProbability(p1,p2));
                } 
            }
  
        }
        //this is needed because finalize is not run in the last round
        turnedRogueOfficial = turnedRogue;
        turnedCooperatorOfficial = turnedCooperator;
    }
    


        


                
   

    public Boolean isRogueGaussian(Player p1, double _u){
        if(p1.getNumNeighbors()==0){ return false;} //alone nodes don't turn rogue. 
        StandardDeviation sd = new StandardDeviation();
        double avNeighborFitness = 0.0;
        
        double[] db = new double[p1.getNumNeighbors()];
        int i = 0;
        for( Player p2: (Collection<Player>) g.getNeighbors(p1)){
            avNeighborFitness = avNeighborFitness + p2.getFitness();
            db[i] = p2.getFitness();
            i = i + 1;
        }
        avNeighborFitness = avNeighborFitness/ p1.getNumNeighbors();

        double o = sd.evaluate(db);//WHEN THERE'S NO DISTRIBUTION WE DON'T FEEL ALONE
        //SHOULD WE INCLUDE OUR DATA?
        if(o == 0){ return false;}
        NormalDistribution normal = new NormalDistribution(_u,o);
        Random r = new Random();
        double prob = normal.cumulativeProbability(avNeighborFitness/p1.getFitness());
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


// AVERAGE OF CURVE IS 2. AND THE X = PROPORTION BETWEEN AVERAGE AND YOUR FITNESS
// YOU'LL PASS 50% IF THE AVERAGE IS TWICE YOUR FITNESS

// THE BIGGER THE STD, THE HARDER IT IS FOR YOU TO TURN
// MAKES SENSE SINCE THAT MEANS YOU'RE NOT IN THE BOTTOM OF THE HIERARCHY