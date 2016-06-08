package net.imagej.pixml;

import java.awt.GridLayout;

import javax.swing.JFrame;

import org.scijava.command.Command;
import org.scijava.menu.MenuConstants;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.pixml.services.AnnotationService;
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
	
//	@Parameter
//	private AnnotationService annotationService; //or directly request an ImgLabeling to be filled by a respective PreProcessorPlugin
	
	@Parameter
	private PixMLService pixmlService;
	

	@Override
	public void run() {
		// 1. create dialog to configure features and the classifier
		//TODO: don't open a frame here but something else (JDialog?)
		JFrame frame = new JFrame("PixML");
		ClassifiersConfig cc = pixmlService.getClassifiersConfig();
		FeatureSetsConfig fc = pixmlService.getFeaturesConfig();
		frame.setLayout(new GridLayout(2, 1));
		frame.add(cc.getConfigPanel());
		frame.add(fc.getConfigPanel());
		frame.pack();
		frame.setVisible(true);
		
		// 2. get an annotated image (e.g. via the annotation service)
		// 3. calculate
		// 4. e.g. push the result via the DisplayService to a Display (a the
		// display that is for instance backed by the bigdataviewer)
	}

}
