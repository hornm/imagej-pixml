package net.imagej.pixml;

import java.util.List;

import org.scijava.plugin.Plugin;

import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.composite.RealComposite;

@Plugin(type = ClassifierFactory.class)
public class OtherClassifier implements ClassifierFactory {

	@Override
	public Classifier createClassifier() {
		return new Classifier() {
			

			@Override
			public <T extends RealType<T>, L> void build(RandomAccessibleInterval<RealComposite<T>> feaetures,
					RandomAccessibleInterval<LabelingType<L>> labels) {
				// TODO Auto-generated method stub

			}

			@Override
			public <T extends RealType<T>> UnaryHybridCF<IterableInterval<RealComposite<T>>, List<IterableInterval<FloatType>>> predictDistrOp() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Override
	public String toString() {
		return "Other Classifier";
	}

}
