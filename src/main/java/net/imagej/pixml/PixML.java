package net.imagej.pixml;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.scijava.command.Command;
import org.scijava.command.CommandService;
import org.scijava.menu.MenuConstants;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.pixml.services.AnnotationManager;
import net.imagej.pixml.services.ClassifiersConfig;
import net.imagej.pixml.services.FeatureSetsConfig;
import net.imagej.pixml.services.PixMLService;

/**
 * Main entry point to the interactive PixML functionality in ImageJ.
 * 
 * @author Martin Horn
 */
@Plugin(type = Command.class, menu = {
		@Menu(label = MenuConstants.PLUGINS_LABEL, weight = MenuConstants.PLUGINS_WEIGHT, mnemonic = MenuConstants.PLUGINS_MNEMONIC),
		@Menu(label = "PixML ...", weight = 22) })
public class PixML implements Command {

	// @Parameter
	// private AnnotationService annotationService; //or directly request an
	// ImgLabeling to be filled by a respective PreProcessorPlugin

	@Parameter
	private PixMLService pixmlService;

	@Parameter
	private CommandService commandService;

	@Override
	public void run() {
		// 1. create dialog to configure features and the classifier
		// TODO: don't open a frame here but something else (JDialog?)
		JFrame frame = new JFrame("PixML");
		ClassifiersConfig cc = pixmlService.getClassifiersConfig();
		FeatureSetsConfig fc = pixmlService.getFeaturesConfig();
		frame.setLayout(new GridLayout(5, 1));
		frame.add(cc.getConfigPanel());
		frame.add(fc.getConfigPanel());
		JButton annotationManager = new JButton("Annotation Manager");
		annotationManager.addActionListener(l -> commandService.run(AnnotationManager.class, true));
		frame.add(annotationManager);
		frame.add(new JButton("Train"));
		frame.add(new JButton("Predict"));
		frame.pack();
		frame.setVisible(true);

		// 2. get an annotated image (e.g. via the annotation service)
		// 3. calculate
		// 4. e.g. push the result via the DisplayService to a Display (a the
		// display that is for instance backed by the bigdataviewer)
	}

}
