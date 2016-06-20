package net.imagej.pixml.features;

import java.util.List;

import org.scijava.plugin.Plugin;

import net.imagej.ops.OpRef;
import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;

@Plugin(type = FeatureSet.class)
public class Test2FeatureSet implements FeatureSet {


	@Override
	public int getNumFeatures() {
		return 2;
	}

	
	@Override
	public String toString() {
		return "Test2 Feature Set";
	}


	@Override
	public <I extends RealType<I>, O extends RealType<O>> UnaryHybridCF<RandomAccessibleInterval<I>, List<RandomAccessibleInterval<O>>> calcOp() {
		// TODO Auto-generated method stub
		return null;
	}





}
