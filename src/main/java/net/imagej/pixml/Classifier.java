package net.imagej.pixml;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.scijava.plugin.Parameter;

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
 * TODO
 * 
 * @author Martin Horn
 *
 */
public interface Classifier extends Serializable {

	/**
	 * TODO
	 * 
	 * @return
	 */
	<T extends RealType<T>, L> void build(RandomAccessibleInterval<RealComposite<T>> feaetures,
			RandomAccessibleInterval<LabelingType<L>> labels);

	/**
	 * TODO
	 * 
	 * By default this method returns an Op that uses the distribution to
	 * determine the final classification result.
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
	 * TODO
	 * 
	 * @return a {@link BinaryHybridCF} that produces the distribution over all
	 *         classes
	 */
	<T extends RealType<T>> UnaryHybridCF<IterableInterval<RealComposite<T>>, List<IterableInterval<FloatType>>> predictDistrOp();

}
