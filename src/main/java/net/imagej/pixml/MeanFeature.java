package net.imagej.pixml;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.OpService;
import net.imagej.ops.Ops.Filter.Mean;
import net.imagej.ops.special.hybrid.AbstractUnaryHybridCF;
import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imagej.pixml.commands.MeanFeatureConfig;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.neighborhood.RectangleShape;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.Views;

@Plugin(type = FeatureSet.class)
public class MeanFeature implements FeatureSet, Configurable<MeanFeatureConfig> {

	@Parameter
	private OpService opService;
	private MeanFeatureConfig config;

	@Override
	public int getNumFeatures() {
		return 1;
	}

	@Override
	public <I extends RealType<I>, O extends RealType<O>> UnaryHybridCF<RandomAccessibleInterval<I>, RandomAccessibleInterval<O>[]> calcOp() {
		return new AbstractUnaryHybridCF<RandomAccessibleInterval<I>, RandomAccessibleInterval<O>[]>() {

			@Override
			public void compute1(RandomAccessibleInterval<I> input, RandomAccessibleInterval<O>[] output) {
				opService.op(Mean.class, Views.iterable(output[0]), input, new RectangleShape(config.getRadius(), false)).run();
			}

			@Override
			public RandomAccessibleInterval<O>[] createOutput(RandomAccessibleInterval<I> input) {
				return new RandomAccessibleInterval[] { opService.create().img(input) };
			}
		};
	}

	@Override
	public String toString() {
		return "Mean";
	}

	@Override
	public Class<MeanFeatureConfig> getConfigCommandClass() {
		return MeanFeatureConfig.class;
	}

	@Override
	public void configure(MeanFeatureConfig config) {
		this.config = config;
	}


	
}
