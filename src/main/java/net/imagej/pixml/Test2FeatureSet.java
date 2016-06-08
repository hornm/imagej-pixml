package net.imagej.pixml;

import org.scijava.plugin.Plugin;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;

@Plugin(type = FeatureSet.class)
public class Test2FeatureSet implements FeatureSet {


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
