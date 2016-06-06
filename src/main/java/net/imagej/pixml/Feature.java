package net.imagej.pixml;

import org.scijava.plugin.SciJavaPlugin;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;

public interface Feature<FC extends FeatureConfig> extends SciJavaPlugin {
	
	String getName();

	Class<FC> getFeatureConfigClass();
	
	void configure(FC featureConfig);

	<T extends RealType<T>> void calc(RandomAccessibleInterval<T> img, RandomAccessibleInterval<FloatType> out);

}
