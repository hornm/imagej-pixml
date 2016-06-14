package net.imagej.pixml;

import java.util.List;

import org.scijava.command.Command;

import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;

public interface FeatureSet extends Command {

	/**
	 * @return the number of features provided by this features set
	 */
	int getNumFeatures();

	/**
	 * {@link UnaryHybridCF}-op that calculates the features.
	 * 
	 */
	<I extends RealType<I>, O extends RealType<O>> UnaryHybridCF<RandomAccessibleInterval<I>, List<RandomAccessibleInterval<O>>> calcOp();
	
	@Override
	default void run() {
		// usually nothing to do here
	}

}
