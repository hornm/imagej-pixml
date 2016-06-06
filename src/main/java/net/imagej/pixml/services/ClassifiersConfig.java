package net.imagej.pixml.services;

import javax.swing.JPanel;

import net.imagej.pixml.Classifier;

public interface ClassifiersConfig {
	
	JPanel getConfigPanel();
	
	Classifier getSelectedClassifier();

}
