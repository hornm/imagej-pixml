package net.imagej.pixml.features;

import java.util.List;

import org.scijava.plugin.Plugin;

import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;

@Plugin(type = FeatureSet.class)
public class TestFeatureSet implements FeatureSet {

	@Override
	public int getNumFeatures() {
		return 1;
	}

	@Override
	public String toString() {
		return "Test Feature Set";
	}

	@Override
	public <I extends RealType<I>, O extends RealType<O>> UnaryHybridCF<RandomAccessibleInterval<I>, List<RandomAccessibleInterval<O>>> calcOp() {
		// TODO Auto-generated method stub
		return null;
	}

}
