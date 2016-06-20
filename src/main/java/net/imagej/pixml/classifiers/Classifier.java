package net.imagej.pixml.classifiers;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.scijava.plugin.Parameter;

import net.imagej.ops.Op;
import net.imagej.ops.OpService;
import net.imagej.ops.special.computer.AbstractUnaryComputerOp;
import net.imagej.ops.special.computer.UnaryComputerOp;
import net.imagej.ops.special.hybrid.AbstractUnaryHybridCF;
import net.imagej.ops.special.hybrid.BinaryHybridCF;
import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.interpolation.randomaccess.NearestNeighborInterpolatorFactory;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.StackView.StackAccessMode;
import net.imglib2.view.Views;
import net.imglib2.view.composite.RealComposite;

/**
 * Represents a general classifier for images. After the classifier has been
 * build with {@link #build(RandomAccessibleInterval, RandomAccessibleInterval)}
 * , the predictions can be determined by calling {@link #predictOp()} or
 * {@link #predictDistrOp()}.
 * 
 * @author Martin Horn
 *
 */
public interface Classifier extends Serializable {

	/**
	 * Builds the classifier model. Needs to be called before calling any the
	 * {@link #predictOp()} or {@link #predictDistrOp()} methods.
	 * 
	 * The provided label- and feature images must represent the same intervals!
	 * Otherwise an {@link IllegalArgumentException} will be thrown.
	 * 
	 * @param features
	 *            the image containing the features
	 * @param labels
	 *            the image containing the labels
	 */
	<T extends RealType<T>, L> void build(RandomAccessibleInterval<RealComposite<T>> features,
			RandomAccessibleInterval<LabelingType<L>> labels);

	/**
	 * Returns an {@link UnaryHybridCF} that can be used to perform the actual
	 * prediction. The result will be returned as a labeled image (i.e. each
	 * pixel-position receives the predicted class label).
	 * 
	 * By default this method returns an Op that uses the op provided by
	 * {@link #predictDistrOp()} to determine the final classification result.
	 * It takes the maximum over the distribution and assigns the class-idx as
	 * class-label.
	 * 
	 * @return a {@link BinaryHybridCF} that produces the p of all predicted
	 *         classes.
	 * 
	 */
	default <T extends RealType<T>, L> UnaryHybridCF<IterableInterval<RealComposite<T>>, IterableInterval<LabelingType<L>>> predictOp() {
		return new AbstractUnaryHybridCF<IterableInterval<RealComposite<T>>, IterableInterval<LabelingType<L>>>() {

			@Parameter
			private OpService ops;

			@Override
			public void compute1(IterableInterval<RealComposite<T>> input, IterableInterval<LabelingType<L>> output) {
				UnaryHybridCF<IterableInterval<RealComposite<T>>, List<IterableInterval<FloatType>>> op = predictDistrOp();
				// TODO convert II to RAI
				List<RandomAccessibleInterval<FloatType>> distr = null;
				// = op.compute1(input).stream().map(ii -> {
				// return Views.raster(Views.interpolate(ii, new
				// NearestNeighborInterpolatorFactory<FloatType>()));
				// }).collect(Collectors.toList());
				RandomAccessibleInterval<FloatType> stack = Views.stack(StackAccessMode.DEFAULT, distr);
				UnaryComputerOp<Iterable<FloatType>, LabelingType<L>> maxProject = new AbstractUnaryComputerOp<Iterable<FloatType>, LabelingType<L>>() {
					@Override
					public void compute1(Iterable<FloatType> input, LabelingType<L> output) {
						int i = 0;
						Integer classIdx = 0;
						float max = -Float.MAX_VALUE;
						for (FloatType t : input) {
							if (t.getRealFloat() > max) {
								max = t.getRealFloat();
								classIdx = i;
							}
							i++;
						}
						output.add((L) classIdx);
					}
				};
				ops.transform().<FloatType, LabelingType<L>> project(output, stack, maxProject,
						stack.numDimensions() - 1);
			}

			@Override
			public IterableInterval<LabelingType<L>> createOutput(IterableInterval<RealComposite<T>> input1) {
				return ops.create().imgLabeling(input1);
			}
		};
	}

	/**
	 * Returns an {@link UnaryHybridCF} that can be used to perform the actual
	 * prediction. The result will be returned as a list of images, each
	 * containing the probabilities for a class. That is, the size of the list
	 * will equal the number of classes to be predicted.
	 * 
	 * @return a {@link BinaryHybridCF} that produces the distribution over all
	 *         classes
	 */
	<T extends RealType<T>> UnaryHybridCF<IterableInterval<RealComposite<T>>, List<IterableInterval<FloatType>>> predictDistrOp();

}
