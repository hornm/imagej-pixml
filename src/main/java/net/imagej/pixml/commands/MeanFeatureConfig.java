package net.imagej.pixml.commands;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Command.class)
public class MeanFeatureConfig implements Command{

	@Parameter
	private int radius = 1;
	
	@Override
	public void run() {
		//
	}
	
	public int getRadius() {
		return radius;
	}
	
}