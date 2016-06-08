package net.imagej.pixml;

import java.util.Optional;

import org.scijava.plugin.SciJavaPlugin;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;

public interface FeatureSet<FC extends FeatureSetConfig> extends SciJavaPlugin {
	
	/**
	 * Initializes/configures the feature set. Always called before
	 * {@link #getNumFeatures()} and
	 * {@link #calc(RandomAccessibleInterval, RandomAccessibleInterval[])}.
	 * 
	 * @param featureSetConfig
	 */
	void configure(FC featureSetConfig);

	/**
	 * @return the class used to configure the feature set
	 */
	Optional<Class<FC>> getFeatureSetConfigClass();

	/**
	 * @return the number of features provided by this features set
	 */
	int getNumFeatures();

	/**
	 * Calculates the features.
	 * 
	 * TODO: find the best method signature, e.g. also considering lazy
	 * calculation etc. (should we return an op instead?)
	 * 
	 * @param img
	 * @param out
	 *            on {@link RandomAccessibleInterval} for each feature, i.e.
	 *            <code>out.length == getNumFeatures()</code>
	 */
	<I extends RealType<I>, O extends RealType<O>> void calc(RandomAccessibleInterval<I> img,
			RandomAccessibleInterval<O>[] out);

}
