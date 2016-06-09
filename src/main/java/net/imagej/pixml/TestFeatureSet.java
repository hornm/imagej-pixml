package net.imagej.pixml;

import org.scijava.plugin.Plugin;

import net.imagej.ops.OpRef;
import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imagej.pixml.commands.TestFeatureSetConfig;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;

@Plugin(type = FeatureSet.class)
public class TestFeatureSet implements FeatureSet, Configurable<TestFeatureSetConfig> {
	
	private TestFeatureSetConfig featureSetConfig;

	@Override
	public Class<TestFeatureSetConfig> getConfigCommandClass() {
		return TestFeatureSetConfig.class;
	}
	
	@Override
	public void configure(TestFeatureSetConfig featureSetConfig) {
		this.featureSetConfig = featureSetConfig;
	}
	

	@Override
	public int getNumFeatures() {
		return 1;
	}

	
	@Override
	public String toString() {
		return "Test Feature Set";
	}

	@Override
	public <I extends RealType<I>, O extends RealType<O>> UnaryHybridCF<RandomAccessibleInterval<I>, RandomAccessibleInterval<O>[]> calcOp() {
		// TODO Auto-generated method stub
		return null;
	}


}
