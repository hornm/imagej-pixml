package net.imagej.pixml.features;

import java.util.Arrays;
import java.util.List;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.OpService;
import net.imagej.ops.special.hybrid.AbstractUnaryHybridCF;
import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.outofbounds.OutOfBoundsBorderFactory;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.Views;

@Plugin(type = FeatureSet.class)
public class GaussianFeature implements FeatureSet {

	@Parameter
	private OpService opService;

	@Parameter
	private double sigma = 1;

	@Override
	public int getNumFeatures() {
		return 1;
	}

	@Override
	public <I extends RealType<I>, O extends RealType<O>> UnaryHybridCF<RandomAccessibleInterval<I>, List<RandomAccessibleInterval<O>>> calcOp() {
		return new AbstractUnaryHybridCF<RandomAccessibleInterval<I>, List<RandomAccessibleInterval<O>>>() {

			@Override
			public void compute1(RandomAccessibleInterval<I> input, List<RandomAccessibleInterval<O>> output) {
				opService.run("filter.gauss", Views.iterable(output.get(0)), input,
						sigma, new OutOfBoundsBorderFactory<>());
			}

			@Override
			public List<RandomAccessibleInterval<O>> createOutput(RandomAccessibleInterval<I> input) {
				return Arrays.asList((RandomAccessibleInterval<O>) opService.create().img(input));
			}
		};
	}

	@Override
	public String toString() {
		return "Gaussian";
	}
}
