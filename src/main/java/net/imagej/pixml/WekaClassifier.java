package net.imagej.pixml;

import org.scijava.plugin.Plugin;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.composite.RealComposite;
import weka.classifiers.AbstractClassifier;

@Plugin(type = Classifier.class)
public class WekaClassifier implements Classifier<AbstractClassifier, WekaClassifierConfig> {
	
	private WekaClassifierConfig classifierConfig;

	@Override
	public String toString() {
		return "Weka Classifier";
	}

	@Override
	public Class<WekaClassifierConfig> getClassifierConfigClass() {
		return WekaClassifierConfig.class;
	}

	@Override
	public void configure(WekaClassifierConfig classifierConfig) {
		this.classifierConfig = classifierConfig;
	}

	@Override
	public <T extends RealType<T>, L> AbstractClassifier train(RandomAccessibleInterval<RealComposite<T>> img,
			ImgLabeling<L, ? extends RealType<?>> annotation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends RealType<T>> void predict(RandomAccessibleInterval<T> img, RandomAccessibleInterval<?> res,
			AbstractClassifier model) {
		// TODO Auto-generated method stub

	}

}
