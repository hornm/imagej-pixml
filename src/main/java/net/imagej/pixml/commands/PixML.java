package net.imagej.pixml.commands;

import java.io.Serializable;

import org.scijava.command.Command;
import org.scijava.command.CommandService;
import org.scijava.menu.MenuConstants;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.widget.Button;

import net.imagej.pixml.Classifier;
import net.imagej.pixml.FeatureSets;
import net.imagej.pixml.service.AnnotationManager;
import net.imagej.pixml.service.PixMLService;

/**
 * Main entry point to the interactive PixML functionality in ImageJ.
 * 
 * @author Martin Horn
 */
@Plugin(type = Command.class, menu = {
		@Menu(label = MenuConstants.PLUGINS_LABEL, weight = MenuConstants.PLUGINS_WEIGHT, mnemonic = MenuConstants.PLUGINS_MNEMONIC),
		@Menu(label = "PixML ...", weight = 22) })
public class PixML<M extends Serializable> implements Command {

	// @Parameter
	// private AnnotationService annotationService; //or directly request an
	// ImgLabeling to be filled by a respective PreProcessorPlugin

	@Parameter
	private PixMLService pixmlService;

	@Parameter
	private CommandService commandService;

	@Parameter
	private Classifier<M> classifier;

	@Parameter(label = "Features")
	private FeatureSets featureSets;

	@Parameter(label = "Open Annotation Manager", callback = "onOpenAnnotationManager")
	private Button button;
	// TODO
	// @Parameter
	// private RichImg?? output;

	@Override
	public void run() {
		// 1. create dialog to configure features and the classifier (done by the framework)
		// 2. get an annotated image (e.g. via the annotation service)
		// 3. calculate
		// 4. e.g. push the result via the DisplayService to a Display (a the
		// display that is for instance backed by the bigdataviewer)
	}
	
	private void onOpenAnnotationManager() {
		commandService.run(AnnotationManager.class, true);
	}

}
