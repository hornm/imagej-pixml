package net.imagej.pixml.command;

import org.scijava.command.Command;
import org.scijava.object.ObjectService;
import org.scijava.plugin.Plugin;

/**
 * Potential command to just create a model that is than deployed and available
 * via the {@link ObjectService}, e.g. to be used by the {@link PixMLPredict}-command.
 * 
 * @author Martin Horn
 */
@Plugin(type = Command.class)
public class PixMLTrain implements Command {

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
