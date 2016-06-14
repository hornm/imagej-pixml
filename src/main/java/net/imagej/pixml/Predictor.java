package net.imagej.pixml;

import java.io.Serializable;
import java.util.List;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;

import net.imagej.ops.OpService;
import net.imagej.ops.special.computer.AbstractUnaryComputerOp;
import net.imagej.ops.special.computer.UnaryComputerOp;
import net.imagej.ops.special.hybrid.AbstractBinaryHybridCF;
import net.imagej.ops.special.hybrid.BinaryHybridCF;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.StackView.StackAccessMode;
import net.imglib2.view.Views;

public interface Predictor<M extends Serializable> extends Command {

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
	default <T extends RealType<T>, L> BinaryHybridCF<RandomAccessibleInterval<T>, M, RandomAccessibleInterval<LabelingType<L>>> predictOp() {
		return new AbstractBinaryHybridCF<RandomAccessibleInterval<T>, M, RandomAccessibleInterval<LabelingType<L>>>() {

			@Parameter
			private OpService ops;

			@Override
			public void compute2(RandomAccessibleInterval<T> input1, M input2,
					RandomAccessibleInterval<LabelingType<L>> output) {
				BinaryHybridCF<RandomAccessibleInterval<T>, M, List<RandomAccessibleInterval<FloatType>>> op = predictDistrOp();
				List<RandomAccessibleInterval<FloatType>> distr = op.compute2(input1, input2);
				RandomAccessibleInterval<FloatType> stack = Views.stack(StackAccessMode.DEFAULT, distr);
				UnaryComputerOp<Iterable<FloatType>, LabelingType<L>> maxProject = new AbstractUnaryComputerOp<Iterable<FloatType>, LabelingType<L>>() {
					@Override
					public void compute1(Iterable<FloatType> input, LabelingType<L> output) {
						int i = 0;
						Integer classIdx = 0;
						float max = -Float.MAX_VALUE;
						for (FloatType t : input) {
							if(t.getRealFloat() > max) {
								max = t.getRealFloat();
								classIdx = i;
							}
							i++;
						}
						output.add((L) classIdx);
					}
				};
				ops.transform().<FloatType, LabelingType<L>> project(
						Views.iterable(output), stack, maxProject,
						stack.numDimensions() - 1);
			}

			@Override
			public RandomAccessibleInterval<LabelingType<L>> createOutput(RandomAccessibleInterval<T> input1,
					M input2) {
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
	<T extends RealType<T>> BinaryHybridCF<RandomAccessibleInterval<T>, M, List<RandomAccessibleInterval<FloatType>>> predictDistrOp();
	
	@Override
	default void run() {
		// nothing to do here, usually
		
	}
	
}
