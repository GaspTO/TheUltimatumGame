package com.ist.rc2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.uci.ics.jung.graph.Graph;

class Ultimatum{
    public Graph<Player, Integer> g;

    public Ultimatum(Graph<Player, Integer> _g){
        g = _g;
    }

    public static void play(Player p1, Player p2){
        //p1 offers to p2
        if(p2.q <= p1.p){
            p2.addFitness(p1.p);
            p1.addFitness(1.0-p1.p);
        }
    }

    public static void updateStrategy(Player p1, Player p2, Double prob){
        Random r = new Random(0);
        double d = r.nextDouble();
        if(r.nextDouble() < prob){
            p1.updatePQ(p2.p,p2.q);
        }
    }

    public double selectProbability(Player p1, Player p2){
        int max = ((g.getNeighborCount(p1) > g.getNeighborCount(p2) ) ? g.getNeighborCount(p1) : g.getNeighborCount(p2));
        if(p1.fitness < p2.fitness){
           return (p2.fitness - p1.fitness)/(2*max);
        }
        else{
            return 0.0;
        }
    }

    public void runGame(int rounds){
        Random rand = new Random(26663);
        for( int i = 0; i<rounds; i++){ 
            if(((double)i/rounds * 100.0) % 5== 0){
                System.out.println("round:"+i );
            }
            for( Player p1:  (Collection<Player>) g.getVertices() ){
                p1.resetFitness();            
            }


            for( Player p1:  (Collection<Player>) g.getVertices() ){
                for( Player p2: (Collection<Player>) g.getNeighbors(p1) ){
                    Ultimatum.play(p1,p2);
                }
            }
        
            for( Player p1:  (Collection<Player>) g.getVertices() ){
                //getsCollections.TransformsToArray[ Picks a random neighbor ]
                if(g.getNeighborCount(p1) != 0){
                    int l = rand.nextInt(g.getNeighborCount(p1));
                    Player p2 = (Player) g.getNeighbors(p1).toArray()[l];
                    Ultimatum.updateStrategy(p1,p2,selectProbability(p1,p2));
                }
            }      
        }

    }


   public double averageFitness(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + p1.fitness;
            count ++;       
        }
        return total/count;
    }  

   public double averageP(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + p1.p;
            count ++;     
        }
        return total/count;
    }

    public double averageK(){
        double total = 0.0;
        int count = 0;
        for( Player p1:  (Collection<Player>) g.getVertices() ){
            total = total + g.getNeighborCount(p1);
            count ++;     
        }
        return total/count;
    }  

    
    public Component ListOfferDistributionP(int round){
    	XYDataset dataset = createDatasetP(round, g.getVertices().stream().map(p -> p.getP()).collect(Collectors.toList()));
        JFreeChart chart = createChart(dataset, round);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        return chartPanel;
    }
    

	private XYDataset createDatasetP(int round, List<Double> list) {
			XYSeries series = new XYSeries("");
			Map<Double, MutableInt> pDistribution = pDistribution(list);
			for (Entry<Double, MutableInt> degree : pDistribution.entrySet()) {
				series.add(degree.getKey().doubleValue(), degree.getValue().value);
			}
	        XYSeriesCollection dataset = new XYSeriesCollection();
	        dataset.addSeries(series);
	        return dataset;
	}
    
    
    private Map<Double, MutableInt> pDistribution(List<Double> list) {
    	Map<Double, MutableInt> map = new HashMap<Double, MutableInt>();
		for (Double p: list) {
			if(map.containsKey(p)) 
				map.get(p).increment();
			 else
				 map.put(p, new MutableInt());
		}
		return map;
	}

	private JFreeChart createChart(XYDataset dataset, int round) {
    	 JFreeChart chart = ChartFactory.createXYLineChart(
                 "pDistribution", 
                 "p", 
                 "D(p)", 
                 dataset, 
                 PlotOrientation.VERTICAL,
                 true, 
                 true, 
                 false 
         );

         XYPlot plot = chart.getXYPlot();

         XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
         renderer.setBaseShapesVisible(true);
         renderer.setSeriesPaint(0, Color.RED);
         renderer.setSeriesStroke(0, new BasicStroke(2.0f));

         plot.setRenderer(renderer);
         plot.setBackgroundPaint(Color.white);

         plot.setRangeGridlinesVisible(true);
         plot.setRangeGridlinePaint(Color.BLACK);

         plot.setDomainGridlinesVisible(true);
         plot.setDomainGridlinePaint(Color.BLACK);

         chart.getLegend().setFrame(BlockBorder.NONE);

         chart.setTitle(new TextTitle("pDistribution",
                         new Font("Serif", java.awt.Font.BOLD, 18)
                 )
         );

         return chart;
	}

	public Component ListOfferDistributionQ(){
    	g.getVertices().stream().map(p -> p.getQ()).collect(Collectors.toList());
    	return null;
    } 
    
    
}