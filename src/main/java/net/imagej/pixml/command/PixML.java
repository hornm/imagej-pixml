package net.imagej.pixml.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.scijava.command.Command;
import org.scijava.command.CommandService;
import org.scijava.menu.MenuConstants;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;
import org.scijava.widget.Button;

import net.imagej.ImgPlus;
import net.imagej.overlay.Overlay;
import net.imagej.pixml.Classifier;
import net.imagej.pixml.FeatureSets;
import net.imagej.pixml.service.AnnotationManager;
import net.imagej.pixml.service.PixMLService;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.Views;
import net.imglib2.view.composite.RealComposite;

/**
 * Main entry point to the interactive PixML functionality in ImageJ.
 * 
 * @author Martin Horn
 */
@Plugin(type = Command.class, menu = {
		@Menu(label = MenuConstants.PLUGINS_LABEL, weight = MenuConstants.PLUGINS_WEIGHT, mnemonic = MenuConstants.PLUGINS_MNEMONIC),
		@Menu(label = "PixML ...", weight = 22) })
public class PixML<F extends RealType<F>> implements Command {

	// @Parameter
	// private AnnotationService annotationService; //or directly request an
	// ImgLabeling to be filled by a respective PreProcessorPlugin

	@Parameter
	private PixMLService pixmlService;

	@Parameter
	private CommandService commandService;
	
	@Parameter
	private UIService uiService;

	@Parameter
	private Classifier classifier;

	@Parameter(label = "Features")
	private FeatureSets featureSets;

	@Parameter
	private ImgPlus inputImg;
	
	@Parameter
	private Overlay overlay;

	// @Parameter
	// private ImgLabeling labels;

	@Parameter(label = "Open Annotation Manager", callback = "onOpenAnnotationManager")
	private Button button;

	// TODO
	// @Parameter
	// private RichImg?? output;

	@Override
	public void run() {
		/*
		 * 1. create dialog to configure features and the classifier (done by
		 * the framework)
		 */
		/* 2. get an annotated image (e.g. via the annotation service) */
		/* 3. calculate features, train model and perform prediction */
		List<RandomAccessibleInterval<F>> featImgs = calcFeatImgs();
		uiService.show(featImgs.get(0));
		RandomAccessibleInterval<RealComposite<F>> composite = composite(featImgs);
		
		//TODO: we need to get the ImgLabeling from somewhere
		// Object model = classifier.trainOp().compute2(composite, null);
		// classifier.predictOp().compute2(inputImg, model);
		
		
		/*
		 * 4. e.g. push the result via the DisplayService to a Display (a the
		 * display that is for instance backed by the bigdataviewer)
		 */
	}

	private List<RandomAccessibleInterval<F>> calcFeatImgs() {
		List<RandomAccessibleInterval<F>> featImgs = new ArrayList<>();
		featureSets.forEach(fs -> featImgs
				.addAll((Collection<? extends RandomAccessibleInterval<F>>) fs.calcOp().compute1(inputImg)));
		return featImgs;
	}
	
	private RandomAccessibleInterval<RealComposite<F>> composite(List<RandomAccessibleInterval<F>> featImgs) {
		return Views.collapseReal(Views.stack(featImgs));
	}

	private void onOpenAnnotationManager() {
		commandService.run(AnnotationManager.class, true);
	}

}
