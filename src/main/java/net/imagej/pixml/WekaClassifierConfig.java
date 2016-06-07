package net.imagej.pixml;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

@Plugin(type = ClassifierConfig.class, label = "Weka Classifier Configuration")
public class WekaClassifierConfig implements ClassifierConfig {
	
	@Parameter(label = "Weka Classifier")
	private Classifier classifier = new J48();
	
	@Parameter(label = "Sampling Rate")
	private double samplingRate = 1.0;
	
	@Parameter
	private String tmp = "test";
	
	@Override
	public void run() {
		//
	}
	
	Classifier getClassifier() {
		return classifier;
	}
	
	double getSamplingRate() {
		return samplingRate;
	}
	
	

}
