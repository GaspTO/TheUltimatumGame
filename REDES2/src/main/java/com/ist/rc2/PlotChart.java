package com.ist.rc2;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlotChart extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PlotChart(Component listOfferDistributionP) {
        JPanel panel = new JPanel();
        panel.add(listOfferDistributionP);
        add(panel);
        pack();
	}
}
