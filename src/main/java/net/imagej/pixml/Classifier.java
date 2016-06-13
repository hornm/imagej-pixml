package net.imagej.pixml;

import java.io.Serializable;

import org.scijava.plugin.SciJavaPlugin;

import net.imagej.ops.special.function.BinaryFunctionOp;
import net.imagej.ops.special.hybrid.BinaryHybridCF;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.numeric.RealType;
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
	 * @return
	 */
	<T extends RealType<T>, L> BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, ImgLabeling<L, ? extends RealType<?>>, M> trainOp();

	<T extends RealType<T>> BinaryHybridCF<RandomAccessibleInterval<T>, M, RandomAccessibleInterval<?>> predictOp();
	
	boolean canHandle(Object model);

}
