package com.ist.rc2;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import edu.uci.ics.jung.graph.Graph;
import java.util.Collection;    

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;
import java.lang.Math;


//PRAY FOR ALL THESE IMPORTS TO GO AWAY! YACK!

public class PlotChart extends JFrame {
    public double[] x;
    public double[] y;
    public String titulo;
    public String xTitulo;
    public String yTitulo;
    public String jTitulo;
    public PlotChart(double[] _x, double[] _y, String _titulo, String _yTitulo, String _xTitulo, String _jTitulo) {
        jTitulo = _jTitulo;
        yTitulo = _yTitulo;
        xTitulo = _xTitulo;
        titulo = _titulo;
        x = _x;
        y = _y;
        initUI();
    }

    private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle(jTitulo);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private XYDataset createDataset() {

        XYSeries series = new XYSeries("2016");
        for( int i=0; i<x.length; i++ ){
            series.add(x[i],y[i]);
        }
        

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
            titulo, 
            xTitulo, 
            yTitulo, 
            dataset, 
            PlotOrientation.VERTICAL,
            true, 
            true, 
            false 
    );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
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
        chart.setTitle(new TextTitle(titulo,
                         new Font("Serif", java.awt.Font.BOLD, 18)
                 )
         );

         return chart;

    }

    //this actually creates the plot
    public static void plot(double[] x, double[] y, String _titulo, String _yTitulo, String _xTitulo, String _jTitulo){
        SwingUtilities.invokeLater(() -> {
            PlotChart ex = new PlotChart(x,y,_titulo, _yTitulo, _xTitulo,_jTitulo);
            ex.setVisible(true);
        });
    }

    //P is continuos, decidebox decides which of N equal partitions from 0 to 1, number i gets in
    public static int decideBox(int N, double n){
        double step = 1.0/(N);
        int k = (int)Math.round(n/step);
        return k;
    }
}  