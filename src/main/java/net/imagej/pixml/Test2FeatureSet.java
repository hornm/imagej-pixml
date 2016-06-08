package net.imagej.pixml;

import java.util.Optional;

import org.scijava.plugin.Plugin;

import net.imagej.pixml.FeatureSetConfig.EmptyFeatureSetConfig;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;

@Plugin(type = FeatureSet.class)
public class Test2FeatureSet implements FeatureSet<EmptyFeatureSetConfig> {

	@Override
	public void configure(EmptyFeatureSetConfig featureSetConfig) {
		// nothing to do here
	}

	@Override
	public Optional<Class<EmptyFeatureSetConfig>> getFeatureSetConfigClass() {
		return Optional.empty();
	}

	@Override
	public int getNumFeatures() {
		return 2;
	}

	@Override
	public <I extends RealType<I>, O extends RealType<O>> void calc(RandomAccessibleInterval<I> img,
			RandomAccessibleInterval<O>[] out) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "Test2 Feature Set";
	}


}
