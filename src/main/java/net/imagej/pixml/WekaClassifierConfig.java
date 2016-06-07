package net.imagej.pixml;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = ClassifierConfig.class, label = "Weka Classifier Configuration")
public class WekaClassifierConfig implements ClassifierConfig {
	
	@Parameter
	private double samplingRate;

	@Override
	public void run() {
		// either open a dialog here (e.g. to select the weka classifier) or do
		// it via pluginpreprocessors and try to put the weka-classifier
		// selection as widget there
	}

}
