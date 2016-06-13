package net.imagej.pixml.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.scijava.command.Command;
import org.scijava.command.CommandService;
import org.scijava.menu.MenuConstants;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imagej.pixml.Classifier;
import net.imagej.pixml.FeatureSets;
import net.imagej.pixml.service.AnnotationManager;
import net.imagej.pixml.service.PixMLService;
import net.imglib2.Cursor;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.IntType;
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
	private OpService ops;

	@Parameter
	private Classifier classifier;

	@Parameter(label = "Features")
	private FeatureSets featureSets;

	@Parameter(label = "Source Image")
	private ImgPlus inputImg;

	@Parameter(label = "Label Image")
	private ImgPlus labelImg;

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
		// uiService.show(featImgs.get(0));
		RandomAccessibleInterval<RealComposite<F>> composite = composite(featImgs);

		// TODO: we need to get the ImgLabeling from somewhere else
		Object model = classifier.trainOp().compute2(composite, toImgLabeling(labelImg));
		// classifier.predictOp().compute2(inputImg, model);
		System.out.println("");

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

	private <T extends RealType<T>> ImgLabeling<Double, IntType> toImgLabeling(IterableInterval<T> img) {

		// FIXME: turning a image into a labeling by using views doesn't work
		// and result in a AIOOBE, don't understand why, yet
		// HashMap<Double, Integer> valueMap = new HashMap<>();
		// List<Double> valueList = new ArrayList<>();
		// int idx = 1;
		// for (T t : Views.iterable(img)) {
		// double val = t.getRealDouble();
		// if (!valueMap.containsKey(val)) {
		// valueMap.put(val, idx);
		// idx++;
		// valueList.add(val);
		// }
		// }
		//
		// RandomAccessibleInterval<IntType> indexImg = Converters.convert(img,
		// (a,b) -> {
		// b.set(valueMap.get(a.getRealDouble()));
		// }, new IntType());
		//
		// uiService.show(indexImg);
		//
		// final ArrayList<Set<Double>> labelSets = new
		// ArrayList<Set<Double>>();
		// labelSets.add(new HashSet<Double>());
		// for (int i = 1; i < valueList.size() + 1; ++i) {
		// final HashSet<Double> set = new HashSet<Double>();
		// set.add(valueList.get(i - 1));
		// labelSets.add(set);
		// }
		//
		// ImgLabeling<Double, IntType> res = new ImgLabeling<>(indexImg);
		// new SerialisationAccess(res.getMapping()) {
		// {
		// super.setLabelSets(labelSets);
		// }
		// };

		ImgLabeling<Double, IntType> imgLabeling = ops.create().imgLabeling(img, new IntType());
		Cursor<T> imgC = img.cursor();
		Cursor<LabelingType<Double>> labC = imgLabeling.cursor();
		while (imgC.hasNext()) {
			imgC.next();
			labC.next();
			labC.get().add(imgC.get().getRealDouble());
		}
		return imgLabeling;
	}

	private void onOpenAnnotationManager() {
		commandService.run(AnnotationManager.class, true);
	}

}
