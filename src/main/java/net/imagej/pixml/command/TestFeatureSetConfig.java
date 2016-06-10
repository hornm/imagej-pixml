package net.imagej.pixml.command;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = Command.class)
public class TestFeatureSetConfig implements Command {
	
	@Parameter
	private double featureParam1;
	
	@Parameter
	private double featureParam2;

	@Override
	public void run() {

	}

}
