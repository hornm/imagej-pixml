package net.imagej.pixml;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import weka.classifiers.Classifier;

@Plugin(type = ClassifierConfig.class, label = "Weka Classifier Configuration")
public class WekaClassifierConfig implements ClassifierConfig {
	
	@Parameter
	private Classifier classifier;
	
	@Parameter
	private double samplingRate;

	@Override
	public void run() {
		
	}

}
