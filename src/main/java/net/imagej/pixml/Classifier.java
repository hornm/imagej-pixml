package net.imagej.pixml;

import java.io.Serializable;
import java.util.List;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.SciJavaPlugin;

import net.imagej.ops.OpService;
import net.imagej.ops.special.function.BinaryFunctionOp;
import net.imagej.ops.special.hybrid.AbstractBinaryHybridCF;
import net.imagej.ops.special.hybrid.BinaryHybridCF;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.composite.RealComposite;

/**
 * 
 * TODO: if classifiers need configuration, they can just implement the
 * {@link Configurable} interface
 * 
 * @param M
 *            the (serializable) model/representation of the classifier
 * 
 * @author Martin Horn
 */
public interface Classifier<M extends Serializable> extends SciJavaPlugin {

	/**
	 * TODO
	 * 
	 * @return
	 */
	<T extends RealType<T>, L> BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, RandomAccessibleInterval<LabelingType<L>>, M> trainOp();

	/**
	 * TODO
	 * 
	 * By default this method returns an Op that uses the distribution to
	 * determine final classification result.
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
				// TODO - generics clash
				// predictDistrOp().compute2(input1, input2);
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

	boolean canHandle(Object model);

}
