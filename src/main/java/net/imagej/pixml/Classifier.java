package net.imagej.pixml;

import java.io.Serializable;
import java.util.Optional;

import org.scijava.plugin.SciJavaPlugin;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.composite.RealComposite;

/**
 * 
 * TODO: if classifiers need configuration, they can just implement the {@link Configurable} interface
 * 
 * @param M
 *            the (serializable) model
 * 
 * @author Martin Horn
 */
public interface Classifier<M extends Serializable> extends SciJavaPlugin {
	
	/**
	 * 
	 * @param img
	 * @param annotation
	 * @return
	 */
	<T extends RealType<T>, L> M train(RandomAccessibleInterval<RealComposite<T>> img,
			ImgLabeling<L, ? extends RealType<?>> annotation);

	<T extends RealType<T>> void predict(RandomAccessibleInterval<T> img, RandomAccessibleInterval<?> res,
			M model);

}
