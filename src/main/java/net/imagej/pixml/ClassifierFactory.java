package net.imagej.pixml;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;

/**
 * TODO
 * 
 * {@link Parameter} annotations for convenient configuration.
 * 
 * @author Martin Horn
 *
 */
public interface ClassifierFactory extends Command {
	
	/**
	 * TODO
	 * 
	 * Important: must return independent instances
	 * 
	 * @return
	 */
	Classifier createClassifier();

	@Override
	default void run() {
		// usually nothing to do here
	}

}
