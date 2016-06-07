package net.imagej.pixml;

import org.scijava.plugin.SciJavaPlugin;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;

public interface FeatureSet<FC extends FeatureSetConfig> extends SciJavaPlugin {
	
	String getName();
	
	void configure(FC featureSetConfig);
	
	Class<FC> getFeatureSetConfigClass();
	
	<T extends RealType<T>> void calc(RandomAccessibleInterval<T> img, RandomAccessibleInterval<FloatType> out);

}
