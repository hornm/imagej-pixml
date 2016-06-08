package net.imagej.pixml;

import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import org.scijava.plugin.Plugin;

import net.imagej.pixml.services.FeatureSetsConfig;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;

@Plugin(type = FeatureSet.class)
public class TestFeatureSet implements FeatureSet<TestFeatureSetConfig> {
	
	private TestFeatureSetConfig featureSetConfig;

	@Override
	public void configure(TestFeatureSetConfig featureSetConfig) {
		this.featureSetConfig = featureSetConfig;
	}

	@Override
	public Optional<Class<TestFeatureSetConfig>> getFeatureSetConfigClass() {
		return Optional.of(TestFeatureSetConfig.class);
	}

	@Override
	public int getNumFeatures() {
		return 1;
	}

	@Override
	public <I extends RealType<I>, O extends RealType<O>> void calc(RandomAccessibleInterval<I> img,
			RandomAccessibleInterval<O>[] out) {
		
	}
	
	@Override
	public String toString() {
		return "Test Feature Set";
	}
	



}
