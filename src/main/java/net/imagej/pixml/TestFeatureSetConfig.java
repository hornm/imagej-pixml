package net.imagej.pixml;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = FeatureSetConfig.class)
public class TestFeatureSetConfig implements FeatureSetConfig {
	
	@Parameter
	private double featureParam1;
	
	@Parameter
	private double featureParam2;

	@Override
	public void run() {

	}

}
