package net.imagej.pixml;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.widget.InputHarvester;

import net.imagej.pixml.services.PixMLService;

/**
 * A {@link Command} that is used to configure a {@link Classifier}. After
 * instantiating, populating its {@link Parameter}s (potentially by opening a
 * configure dialog, see {@link InputHarvester}) and running it, the object is
 * passed to the {@link Classifier#configure(ClassifierConfig)}-method. All this
 * takes place in {@link PixMLService#getClassifiersConfig()}.
 * 
 * @author Martin Horn
 */
public interface ClassifierConfig extends Command {

	public static final class EmptyClassifierConfig implements ClassifierConfig {

		@Override
		public void run() {
			//
		}
	}

}
