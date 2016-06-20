package net.imagej.pixml.classifiers;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;

import net.imagej.pixml.ui.swing.ClassifierFactoryWidget;

/**
 * Allows one to create image classifiers. If used as a parameter in a
 * {@link Command}, a configuration dialog will pop up (see
 * {@link ClassifierFactoryWidget}). For a convenient configuration of the
 * ClassifierFactory itself, just annotate the respective fields with
 * {@link Parameter} and transfer (copy) their values to the {@link Classifier}
 * to be created in the {@link #createClassifier()}-method.
 * 
 * 
 * @author Martin Horn
 *
 */
public interface ClassifierFactory extends Command {

	/**
	 * Creates a new classifier depending on the configuration of this factory.
	 * 
	 * Important: implementers must make sure that there are no
	 * interdependencies within different created classifier-instances (i.e.
	 * same object-reference used in two classifier-instances)
	 * 
	 * @return
	 */
	Classifier createClassifier();

	@Override
	default void run() {
		// usually nothing to do here
	}

}
