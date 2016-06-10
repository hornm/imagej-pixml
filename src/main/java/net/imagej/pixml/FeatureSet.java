package net.imagej.pixml;

import java.util.List;

import org.scijava.plugin.SciJavaPlugin;

import net.imagej.ops.OpRef;
import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;

public interface FeatureSet extends SciJavaPlugin {

	/**
	 * @return the number of features provided by this features set
	 */
	int getNumFeatures();

	/**
	 * {@link OpRef} that calculates the features.
	 * 
	 */
	<I extends RealType<I>, O extends RealType<O>> UnaryHybridCF<RandomAccessibleInterval<I>, List<RandomAccessibleInterval<O>>> calcOp();

}
