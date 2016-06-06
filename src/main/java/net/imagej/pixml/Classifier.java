package net.imagej.pixml;

import java.io.Serializable;

import org.scijava.plugin.SciJavaPlugin;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.composite.RealComposite;

/**
 * 
 * @param M
 *            the (serializable) model
 * 
 * @author Martin Horn
 */
public interface Classifier<M extends Serializable, CC extends ClassifierConfig> extends SciJavaPlugin {
	
	String getName();

	Class<CC> getClassifierConfigClass();
	
	void configure(CC classifierConfig);

	<T extends RealType<T>, L> M train(RandomAccessibleInterval<RealComposite<T>> img,
			ImgLabeling<L, ? extends RealType<?>> annotation);

	<T extends RealType<T>> void predict(RandomAccessibleInterval<T> img, RandomAccessibleInterval<?> res,
			M model);

}
