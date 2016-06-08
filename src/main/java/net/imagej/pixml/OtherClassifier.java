package net.imagej.pixml;

import java.io.Serializable;

import org.scijava.plugin.Plugin;

import net.imagej.pixml.OtherClassifier.MyModel;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.composite.RealComposite;

@Plugin(type = Classifier.class)
public class OtherClassifier implements Classifier<MyModel> {

	public static class MyModel implements Serializable {

	}

	@Override
	public <T extends RealType<T>, L> MyModel train(RandomAccessibleInterval<RealComposite<T>> img,
			ImgLabeling<L, ? extends RealType<?>> annotation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends RealType<T>> void predict(RandomAccessibleInterval<T> img, RandomAccessibleInterval<?> res,
			MyModel model) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "Other Classifier";
	}

}
